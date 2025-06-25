package com.x1.frans.supplier.query.service;

import com.x1.frans.supplier.query.dto.HqDeliveryInfoDetailsDTO;
import com.x1.frans.supplier.query.dto.HqRequestedDeliveryInfoDTO;
import com.x1.frans.supplier.query.dto.HqSupplierDeliveryInfoDTO;
import com.x1.frans.supplier.query.repository.HqSupplierDeliveryInfoMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HqServiceDeliveryInfoQueryServiceImpl implements HqSupplierDeliveryInfoQueryService {

    private final HqSupplierDeliveryInfoMapper hqSupplierDeliveryInfoMapper;

    @Autowired
    public HqServiceDeliveryInfoQueryServiceImpl(HqSupplierDeliveryInfoMapper hqSupplierDeliveryInfoMapper) {
        this.hqSupplierDeliveryInfoMapper = hqSupplierDeliveryInfoMapper;
    }

    @Override
    public List<HqSupplierDeliveryInfoDTO> findDeliveryInfos(Long userId,
                                                             Integer year,
                                                             Integer month,
                                                             Integer day,
                                                             Long supplierId) {
        // 매퍼 쿼리 호출
        return hqSupplierDeliveryInfoMapper.findDeliveryInfos(userId, year, month, day, supplierId);
    }

    @Override
    public List<HqRequestedDeliveryInfoDTO> getRequestedPurchaseOrders(Long userId,
                                                                       String type,
                                                                       String startYearMonth,
                                                                       String endYearMonth,
                                                                       Long supplierId) {
        return hqSupplierDeliveryInfoMapper.getRequestedPurchaseOrders(userId,
                                                                       type,
                                                                       startYearMonth,
                                                                       endYearMonth,
                                                                       supplierId);
    }

    @Override
    public HqDeliveryInfoDetailsDTO getPurchaseOrderDetail(Long userId, Long purchaseOrderId) {
        return hqSupplierDeliveryInfoMapper.getPurchaseOrderDetail(userId, purchaseOrderId);
    }
}
