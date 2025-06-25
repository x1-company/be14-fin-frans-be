package com.x1.frans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FranchiseGenerator {
    static final LinkedHashMap<String, String> guCodeMap = new LinkedHashMap<>() {{
        put("종로구", "JRO"); put("중구", "JGU"); put("용산구", "YSN");
        put("성동구", "SDG"); put("광진구", "GJN"); put("동대문구", "DDM");
        put("중랑구", "JRG"); put("성북구", "SBK"); put("강북구", "GBK");
        put("도봉구", "DBG"); put("노원구", "NWW"); put("은평구", "EPG");
        put("서대문구", "SDM"); put("마포구", "MAP"); put("양천구", "YCG");
        put("강서구", "GSR"); put("구로구", "GUO"); put("금천구", "GCN");
        put("영등포구", "YDP"); put("동작구", "DJK"); put("관악구", "GAN");
        put("서초구", "SCP"); put("강남구", "KAN"); put("송파구", "SPA");
        put("강동구", "GDG");
    }};

    static final Map<String, String[]> guStoreNames = new HashMap<>() {{
        put("종로구", new String[]{"광화문", "삼청"}); put("중구", new String[]{"명동", "을지로"});
        put("용산구", new String[]{"이태원", "한남"}); put("성동구", new String[]{"성수", "금호"});
        put("광진구", new String[]{"구의", "자양"}); put("동대문구", new String[]{"청량리", "용두"});
        put("중랑구", new String[]{"상봉", "망우"}); put("성북구", new String[]{"길음", "돈암"});
        put("강북구", new String[]{"미아", "반동"}); put("도봉구", new String[]{"방학", "쌍문"});
        put("노원구", new String[]{"중계", "상계"}); put("은평구", new String[]{"응암", "불광"});
        put("서대문구", new String[]{"홍제", "연희"}); put("마포구", new String[]{"상수", "망원"});
        put("양천구", new String[]{"목동", "신월"}); put("강서구", new String[]{"가양", "등춘"});
        put("구로구", new String[]{"개봉", "고창"}); put("금천구", new String[]{"독산", "가산"});
        put("영등포구", new String[]{"여이도", "당산"}); put("동작구", new String[]{"노량진", "사당"});
        put("관악구", new String[]{"신림", "봉천"}); put("서초구", new String[]{"반포", "장원"});
        put("강남구", new String[]{"역삼", "논현"}); put("송파구", new String[]{"장실", "문정"});
        put("강동구", new String[]{"천호", "둘촉"});
    }};

    static final Map<Integer, Integer> userDeptMap = new HashMap<>();
    static final List<Integer> managerIds = new ArrayList<>();
    static final Random random = new Random();

    static {
        // ✅ 정확한 부서 매핑
        for (int i = 1; i <= 100; i++) {
            int dept = (i <= 33) ? 4 : (i <= 66 ? 5 : 6);
            userDeptMap.put(i, dept);
        }

        // ✅ 각 유저가 5개씩 담당
        for (int i = 1; i <= 100; i++) {
            for (int j = 0; j < 5; j++) {
                managerIds.add(i);
            }
        }

        Collections.shuffle(managerIds); // 가맹점에 랜덤하게 배정
    }

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        String ym = now.format(DateTimeFormatter.ofPattern("yyyyMM"));
        Map<String, Integer> nameCountMap = new HashMap<>();

        System.out.println("INSERT INTO franchise\n(" +
                "    id, code, name, address, address_detail,\n" +
                "    zipcode, business_number, phone, signed_at,\n" +
                "    is_active, manager_id, owner_id, department_id\n) VALUES");

        for (int i = 1; i <= 500; i++) {
            List<String> guList = new ArrayList<>(guCodeMap.keySet());
            String gu = guList.get(random.nextInt(guList.size()));
            String code = guCodeMap.get(gu) + ym + String.format("%04d", i);
            String[] stores = guStoreNames.getOrDefault(gu, new String[]{"본"});
            String baseName = stores[random.nextInt(stores.length)];
            String fullName = "꼭끼오 " + baseName + "점";

            int count = nameCountMap.getOrDefault(fullName, 0) + 1;
            nameCountMap.put(fullName, count);
            if (count > 1) {
                fullName = "꼭끼오 " + baseName + count + "호점";
            }

            String address = "서울특별시 " + gu + " " + (random.nextInt(100) + 1) + "길 " + (random.nextInt(50) + 1);
            String addressDetail = "NULL";
            String zipcode = String.format("%05d", random.nextInt(99999));
            String businessNumber = String.format("%03d-%02d-%05d", random.nextInt(1000), random.nextInt(100), random.nextInt(100000));
            String phone = String.format("02-%03d-%04d", random.nextInt(1000), random.nextInt(10000));
            String signedAt = now.toString();
            int isActive = 1;
            int managerId = managerIds.get(i - 1); // user_id
            int ownerId = 26 + (i - 1) % 75;
            int departmentId = userDeptMap.get(managerId); // 💡 정확하게 매핑된 부서

            System.out.printf("(%d, '%s', '%s', '%s', %s,\n '%s', '%s', '%s', '%s',\n %d, %d, %d, %d)%s\n",
                    i, code, fullName, address, addressDetail,
                    zipcode, businessNumber, phone, signedAt,
                    isActive, managerId, ownerId, departmentId,
                    i < 500 ? "," : ";");
        }
    }
}
