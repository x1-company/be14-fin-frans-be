package com.x1.frans.statistics.command.application.service;

import com.x1.frans.exception.NoDeletableMessagesException;
import com.x1.frans.exception.NotFoundStatisticsException;
import com.x1.frans.statistics.command.application.dto.FranchiseOrderAmountStatDTO;
import com.x1.frans.statistics.command.application.dto.FranchiseOrderAmountStatDeleteDTO;
import com.x1.frans.statistics.command.application.dto.FranchiseOrderAmountStatModifyDTO;
import com.x1.frans.statistics.command.domain.aggregate.FranchiseOrderAmountStat;
import com.x1.frans.statistics.command.domain.repository.FranchiseOrderAmountStatRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FranchiseOrderAmountStatManualServiceImpl implements FranchiseOrderAmountStatManualService {

    private final FranchiseOrderAmountStatRepository franchiseOrderAmountStatRepository;

    @Autowired
    public FranchiseOrderAmountStatManualServiceImpl(
            FranchiseOrderAmountStatRepository franchiseOrderAmountStatRepository) {
        this.franchiseOrderAmountStatRepository = franchiseOrderAmountStatRepository;
    }

    public void addStat(FranchiseOrderAmountStatDTO dto) {
        FranchiseOrderAmountStat stat = FranchiseOrderAmountStat.builder()
                .year(dto.getYear())
                .month(dto.getMonth())
                .orderAmount(dto.getOrderAmount())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .franchiseId(dto.getFranchiseId())
                .isDeleted(false)
                .build();

        franchiseOrderAmountStatRepository.save(stat);
    }

    public void modifyStat(FranchiseOrderAmountStatModifyDTO dto) {
        FranchiseOrderAmountStat stat = franchiseOrderAmountStatRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundStatisticsException("통계 데이터가 존재하지 않습니다. id=" + dto.getId()));

        stat.setOrderAmount(dto.getOrderAmount());
        stat.setYear(dto.getYear());
        stat.setMonth(dto.getMonth());
        stat.setFranchiseId(dto.getFranchiseId());
        stat.setUpdatedAt(LocalDateTime.now());

        franchiseOrderAmountStatRepository.save(stat);
    }



    public void deleteStat(FranchiseOrderAmountStatDeleteDTO franchiseOrderAmountStatDeleteDTO) {
        if (franchiseOrderAmountStatDeleteDTO.getDeletedReason() == null || franchiseOrderAmountStatDeleteDTO
                                                                                .getDeletedReason().trim().isEmpty()) {
            throw new NoDeletableMessagesException("삭제 사유는 반드시 입력해야 합니다.");
        }

        FranchiseOrderAmountStat stat
                = franchiseOrderAmountStatRepository.findById(franchiseOrderAmountStatDeleteDTO.getId())
                .orElseThrow(()
                        -> new NotFoundStatisticsException("통계 데이터가 없습니다. id="
                                                        + franchiseOrderAmountStatDeleteDTO.getId()));

        stat.setIsDeleted(true);
        stat.setDeletedReason(franchiseOrderAmountStatDeleteDTO.getDeletedReason());
        stat.setDeletedAt(LocalDateTime.now());

        franchiseOrderAmountStatRepository.save(stat);
    }

}
