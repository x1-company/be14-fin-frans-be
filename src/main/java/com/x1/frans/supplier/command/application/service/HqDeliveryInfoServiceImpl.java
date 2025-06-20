package com.x1.frans.supplier.command.application.service;

import com.x1.frans.exception.CannotModifyDeliveryInfoException;
import com.x1.frans.exception.NotFoundDeliveryInfoException;
import com.x1.frans.supplier.command.domain.repository.HqDeliveryInfoRepository;
import com.x1.frans.supplier.command.domain.aggregate.SupplierDeliveryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HqDeliveryInfoServiceImpl implements HqDeliveryInfoService {

    private final HqDeliveryInfoRepository hqDeliveryInfoRepository;

    @Autowired
    public HqDeliveryInfoServiceImpl(HqDeliveryInfoRepository hqDeliveryInfoRepository) {
        this.hqDeliveryInfoRepository = hqDeliveryInfoRepository;
    }

    @Transactional
    public void confirmDeliveryDate(Long deliveryInfoId, Integer year, Integer month, Integer day) {
        SupplierDeliveryInfo deliveryInfo = hqDeliveryInfoRepository.findById(deliveryInfoId)
                .orElseThrow(() -> new NotFoundDeliveryInfoException("납품서를 찾을 수 없습니다."));

        if (deliveryInfo.getYear() != null || deliveryInfo.getMonth() != null || deliveryInfo.getDay() != null) {
            throw new CannotModifyDeliveryInfoException("이미 납품일이 기입된 납품서는 수정할 수 없습니다.");
        }

        deliveryInfo.setYear(year);
        deliveryInfo.setMonth(month);
        deliveryInfo.setDay(day);
    }


}
