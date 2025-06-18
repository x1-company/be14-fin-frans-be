package com.x1.frans.statistics.command.application.service;

import com.x1.frans.exception.NoDeletableMessagesException;
import com.x1.frans.exception.NotFoundStatisticsException;
import com.x1.frans.statistics.command.application.dto.FranchiseReturnProductStatDTO;
import com.x1.frans.statistics.command.application.dto.FranchiseReturnProductStatDeleteDTO;
import com.x1.frans.statistics.command.application.dto.FranchiseReturnProductStatModifyDTO;
import com.x1.frans.statistics.command.domain.aggregate.FranchiseReturnProductStat;
import com.x1.frans.statistics.command.domain.repository.FranchiseReturnProductStatRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FranchiseReturnProductStatManualServiceImpl implements FranchiseReturnProductStatManualService {

    private final FranchiseReturnProductStatRepository franchiseReturnProductStatRepository;

    @Autowired
    public FranchiseReturnProductStatManualServiceImpl(
            FranchiseReturnProductStatRepository franchiseReturnProductStatRepository) {
        this.franchiseReturnProductStatRepository = franchiseReturnProductStatRepository;
    }

    public void addStat(FranchiseReturnProductStatDTO dto) {
        FranchiseReturnProductStat stat = FranchiseReturnProductStat.builder()
                .year(dto.getYear())
                .month(dto.getMonth())
                .returnQuantity(dto.getReturnQuantity())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .productId(dto.getProductId())
                .franchiseId(dto.getFranchiseId())
                .isDeleted(false)
                .build();

        franchiseReturnProductStatRepository.save(stat);

    }

    public void modifyStat(FranchiseReturnProductStatModifyDTO dto) {
        FranchiseReturnProductStat stat = franchiseReturnProductStatRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundStatisticsException("통계 데이터가 존재하지 않습니다. id=" + dto.getId()));

        stat.setReturnQuantity(dto.getReturnQuantity());
        stat.setYear(dto.getYear());
        stat.setMonth(dto.getMonth());
        stat.setFranchiseId(dto.getFranchiseId());
        stat.setProductId(dto.getProductId());
        stat.setUpdatedAt(LocalDateTime.now());

        franchiseReturnProductStatRepository.save(stat);

    }

    public void deleteStat(FranchiseReturnProductStatDeleteDTO franchiseReturnProductStatDeleteDTO) {
        if (franchiseReturnProductStatDeleteDTO.getDeletedReason() == null || franchiseReturnProductStatDeleteDTO
                                                                                .getDeletedReason().trim().isEmpty()) {
            throw new NoDeletableMessagesException("삭제 사유는 반드시 입력해야 합니다.");
        }

        FranchiseReturnProductStat stat
                = franchiseReturnProductStatRepository.findById(franchiseReturnProductStatDeleteDTO.getId())
                .orElseThrow(() -> new NotFoundStatisticsException("통계 데이터가 없습니다. id="
                                                                     + franchiseReturnProductStatDeleteDTO.getId()));

        stat.setIsDeleted(true);
        stat.setDeletedReason(franchiseReturnProductStatDeleteDTO.getDeletedReason());
        stat.setDeletedAt(LocalDateTime.now());

        franchiseReturnProductStatRepository.save(stat);

    }
}
