package com.x1.frans.supplier.query.repository;

import com.x1.frans.supplier.query.dto.DeliveryInfoDetailsDTO;
import com.x1.frans.supplier.query.dto.RequestedDeliveryInfoDTO;
import com.x1.frans.supplier.query.dto.SupplierDeliveryInfoDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface SupplierDeliveryInfoMapper {

    List<SupplierDeliveryInfoDTO> findDeliveryInfos(@Param("supplierId") Long supplierId,
                                                    @Param("year") Integer year,
                                                    @Param("month") Integer month,
                                                    @Param("day") Integer day);

    List<RequestedDeliveryInfoDTO> getRequestedPurchaseOrders(@Param("supplierId") Long supplierId,
                                                              @Param("type") String type,
                                                              @Param("startYearMonth") String startYearMonth,
                                                              @Param("endYearMonth") String endYearMonth);

    DeliveryInfoDetailsDTO getPurchaseOrderDetail(@Param("purchaseOrderId") Long purchaseOrderId,
                                                  @Param("supplierId") Long supplierId);
}

