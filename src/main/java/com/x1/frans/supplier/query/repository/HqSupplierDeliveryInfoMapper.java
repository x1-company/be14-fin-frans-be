package com.x1.frans.supplier.query.repository;

import com.x1.frans.supplier.query.dto.HqDeliveryInfoDetailsDTO;
import com.x1.frans.supplier.query.dto.HqRequestedDeliveryInfoDTO;
import com.x1.frans.supplier.query.dto.HqSupplierDeliveryInfoDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface HqSupplierDeliveryInfoMapper {

    List<HqSupplierDeliveryInfoDTO> findDeliveryInfos(@Param("userId") Long userId,
                                                      @Param("year") Integer year,
                                                      @Param("month") Integer month,
                                                      @Param("day") Integer day,
                                                      @Param("supplierId") Long supplierId);

    List<HqRequestedDeliveryInfoDTO> getRequestedPurchaseOrders(@Param("userId") Long userId,
                                                                @Param("type") String type,
                                                                @Param("startYearMonth") String startYearMonth,
                                                                @Param("endYearMonth") String endYearMonth,
                                                                @Param("supplierId") Long supplierId);

    HqDeliveryInfoDetailsDTO getPurchaseOrderDetail(@Param("purchaseOrderId") Long purchaseOrderId,
                                                    @Param("userId") Long userId);
}
