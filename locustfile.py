from locust import FastHttpUser, task, between, tag
from urllib.parse import urljoin
import os
import json

# ======== 사용자 맞춤 설정 ========
BASE_PATH = "/api/hq/orders"
FRANCHISE_ID = int(os.getenv("FRANCHISE_ID", "1"))
PAGE = int(os.getenv("PAGE", "1"))
SIZE = int(os.getenv("SIZE", "10"))  # 비교 테스트 시 동일 값 유지!

# Bearer 토큰 (환경변수 LOCUST_BEARER_TOKEN가 있으면 그걸 우선 사용)
BEARER_TOKEN = os.getenv("LOCUST_BEARER_TOKEN") or (
    "Bearer "
)

# 다중 조건 조회 파라미터
FILTER_PARAMS = {
    "franchiseId": FRANCHISE_ID,
    "product": "치킨",
    "startDate": "2025-06-09",
    "endDate": "2025-09-21",
    "statusList": ["REVIEW_COMPLETED", "DELIVERING"],  # statusList=..&statusList=.. 로 인코딩
    "page": PAGE,
    "size": SIZE,
}
# ==================================


def extract_first_order_id(payload):
    """
    응답 JSON에서 주문 ID를 유연하게 찾아 반환.
    우선순위: 'content' → 'data' → 'items' → 루트의 임의 리스트
    항목 내 키 후보: 'orderId', 'id', 'order_id'
    """
    if payload is None:
        return None

    candidates = []
    if isinstance(payload, dict):
        for key in ("content", "data", "items", "list", "results"):
            if key in payload and isinstance(payload[key], list):
                candidates = payload[key]
                break
        if not candidates:
            for v in payload.values():
                if isinstance(v, list):
                    candidates = v
                    break
    elif isinstance(payload, list):
        candidates = payload

    for item in candidates:
        if isinstance(item, dict):
            for id_key in ("orderId", "id", "order_id"):
                if id_key in item:
                    return item[id_key]
    return None


class HqOrderUser(FastHttpUser):
    """
    - FastHttpUser는 세션 전역 headers 없음 → 요청마다 headers 전달
    - @tag로 시나리오 분리 (all / by_franchise / filtered / detail)
    - SIZE 고정(비교 테스트 일관성)
    """
    host = os.getenv("LOCUST_HOST", "http://localhost:8080")
    wait_time = between(0.1, 0.3)

    def on_start(self):
        token = (BEARER_TOKEN or "").strip()
        if token and not token.lower().startswith("bearer "):
            token = "Bearer " + token
        # 공통 헤더를 인스턴스 변수로 보관
        self.common_headers = {
            "Authorization": token,
            "Accept": "application/json",
        }

    # ---------- 유틸: 공통 응답 검사 ----------
    def _check(self, resp, fail_msg_prefix):
        if resp.status_code == 401:
            resp.failure("Unauthorized (token expired/invalid)")
            return False
        if resp.status_code >= 400:
            resp.failure(f"{fail_msg_prefix}: {resp.status_code}")
            return False
        return True

    @tag("all")
    @task(3)
    def list_all(self):
        with self.client.get(
                BASE_PATH,
                params={"page": PAGE, "size": SIZE},
                headers=self.common_headers,
                name="GET /api/hq/orders [all]",
                catch_response=True,
        ) as resp:
            self._check(resp, "All orders failed")

    @tag("by_franchise")
    @task(3)
    def list_by_franchise(self):
        with self.client.get(
                BASE_PATH,
                params={"franchiseId": FRANCHISE_ID, "page": PAGE, "size": SIZE},
                headers=self.common_headers,
                name="GET /api/hq/orders [by franchiseId]",
                catch_response=True,
        ) as resp:
            self._check(resp, "Franchise orders failed")

    @tag("filtered")
    @task(4)
    def list_filtered(self):
        with self.client.get(
                BASE_PATH,
                params=FILTER_PARAMS,
                headers=self.common_headers,
                name="GET /api/hq/orders [filtered]",
                catch_response=True,
        ) as resp:
            self._check(resp, "Filtered orders failed")

    @tag("detail")
    @task(2)
    def get_detail_from_previous(self):
        """
        상세 조회만 분리 측정하고 싶을 때 태그로 골라서 실행 가능.
        먼저 리스트를 호출해 id를 하나 찾은 후 상세 조회.
        """
        order_id = None
        for params in (
                FILTER_PARAMS,
                {"franchiseId": FRANCHISE_ID, "page": PAGE, "size": SIZE},
                {"page": PAGE, "size": SIZE},
        ):
            with self.client.get(
                    BASE_PATH,
                    params=params,
                    headers=self.common_headers,
                    name="GET /api/hq/orders [probe]",
                    catch_response=True,
            ) as r:
                if not self._check(r, "Probe failed"):
                    return
                try:
                    js = r.json()
                except Exception:
                    js = None
                if r.status_code < 400:
                    order_id = extract_first_order_id(js) or order_id
            if order_id:
                break

        if not order_id:
            # 상세 조회 실패를 명시적으로 기록
            self.environment.events.request.fire(
                request_type="GET",
                name="GET /api/hq/orders/:id [detail]",
                response_time=0,
                response_length=0,
                exception=RuntimeError("No orderId found for detail"),
                context={},
            )
            return

        with self.client.get(
                f"{BASE_PATH}/{order_id}",
                headers=self.common_headers,
                name="GET /api/hq/orders/:id [detail]",
                catch_response=True,
        ) as resp_detail:
            self._check(resp_detail, "Order detail failed")
