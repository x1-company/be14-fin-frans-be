package com.x1.frans.outbound.command.application.service;

import com.x1.frans.exception.OutboundNotFoundException;
import com.x1.frans.outbound.command.application.vo.RegisterOutboundInfoVO;
import com.x1.frans.outbound.command.domain.aggregate.OrderOutboundEntity;
import com.x1.frans.outbound.command.domain.aggregate.OutboundEntity;
import com.x1.frans.outbound.command.domain.repository.OrderOutboundCommandRepository;
import com.x1.frans.outbound.command.domain.repository.OutBoundCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OutboundCommandServiceImpl implements OutboundCommandService {

    private final OutBoundCommandRepository outboundCommandRepository;
    private final OrderOutboundCommandRepository orderOutboundCommandRepository;

    @Autowired
    public OutboundCommandServiceImpl(OutBoundCommandRepository outboundCommandRepository,
                                      OrderOutboundCommandRepository orderOutboundCommandRepository) {
        this.outboundCommandRepository = outboundCommandRepository;
        this.orderOutboundCommandRepository = orderOutboundCommandRepository;
    }

    /**
     * 주문 결재 완료 시 호출되는 출고 지시 로직
     * 
     * @param orderIds 주문 id들
     */
    @Transactional
    @Override
    public void generateOutbound(List<Long> orderIds) {

        // 1. 출고 코드 자동 생성(충돌 방지)
        String newCode = generateOutboundCode();

        // 2. 출고 데이터 생성(출고 코드, 출고일, 담당자 id, 배송 id는 NULL임)
        OutboundEntity outbound = new OutboundEntity();
        outbound.setCode(newCode);
        outbound.setStatus("IN_PROGRESS");

        OutboundEntity savedOutbound = outboundCommandRepository.save(outbound);
        
        // 3. 주문 별 출고 지시 데이터 생성
        List<OrderOutboundEntity> orderOutBoundEntityList = orderIds.stream()
                .map(orderId -> {
                    OrderOutboundEntity orderOutbound = new OrderOutboundEntity();
                    orderOutbound.setOrdersId(orderId);
                    orderOutbound.setOutbound(savedOutbound);
                    return orderOutbound;
                })
                .toList();

        orderOutboundCommandRepository.saveAll(orderOutBoundEntityList);
    }

    private String generateOutboundCode() {

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefix = "OB" + today;

        String lastCode = outboundCommandRepository.findLastCodeForToday(prefix + "%");

        int nextNumber = (lastCode != null) ? Integer.parseInt(lastCode.substring(prefix.length())) + 1 : 1;

        return String.format("%s%03d", prefix, nextNumber);
    }


    /**
     * 지시된 출고에 출고 정보 등록
     * 
     * @param vo 출고 등록에 필요한 정보
     */
    @Override
    public void registerOutboundInfo(RegisterOutboundInfoVO vo, Long userId) {

        OutboundEntity outbound = outboundCommandRepository.findById(vo.getOutboundId())
                .orElseThrow(() -> new OutboundNotFoundException("해당 출고는 존재하지 않습니다."));

        outbound.setShippedAt(vo.getShippedAt());
        outbound.setStatus("COMPLETED");
        outbound.setUserId(userId);
        outbound.setDeliveryId(vo.getDeliveryId());

        outboundCommandRepository.save(outbound);

        // TODO: 자재 연동 추가 필요
    }
}
