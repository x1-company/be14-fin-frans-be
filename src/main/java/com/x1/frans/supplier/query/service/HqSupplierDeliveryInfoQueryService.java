package com.x1.frans.supplier.query.service;

import com.x1.frans.supplier.query.dto.HqDeliveryInfoDetailsDTO;
import com.x1.frans.supplier.query.dto.HqRequestedDeliveryInfoDTO;
import com.x1.frans.supplier.query.dto.HqSupplierDeliveryInfoDTO;
import java.util.List;

public interface HqSupplierDeliveryInfoQueryService {
    List<HqSupplierDeliveryInfoDTO> findDeliveryInfos(Long userId,
                                                      Integer year,
                                                      Integer month,
                                                      Integer day,
                                                      Long supplierId);

    List<HqRequestedDeliveryInfoDTO> getRequestedPurchaseOrders(Long userId,
                                                                String type,
                                                                String startYearMonth,
                                                                String endYearMonth,
                                                                Long supplierId);

    HqDeliveryInfoDetailsDTO getPurchaseOrderDetail(Long userId, Long purchaseOrderId);

}
