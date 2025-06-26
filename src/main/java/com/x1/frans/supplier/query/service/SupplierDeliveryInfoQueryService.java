package com.x1.frans.supplier.query.service;

import com.x1.frans.supplier.query.dto.DeliveryInfoDetailsDTO;
import com.x1.frans.supplier.query.dto.RequestedDeliveryInfoDTO;
import com.x1.frans.supplier.query.dto.SupplierDeliveryInfoDTO;
import java.util.List;

public interface SupplierDeliveryInfoQueryService {

    List<SupplierDeliveryInfoDTO> findDeliveryInfos(Long supplierId, Integer year, Integer month, Integer day);

    List<RequestedDeliveryInfoDTO> getRequestedPurchaseOrders(Long supplierId,
                                                              String type,
                                                              String startYearMonth,
                                                              String endYearMonth);

    DeliveryInfoDetailsDTO getPurchaseOrderDetail(Long purchaseOrderId, Long supplierId);
}
