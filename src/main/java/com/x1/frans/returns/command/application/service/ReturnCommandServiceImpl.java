package com.x1.frans.returns.command.application.service;

import com.x1.frans.exception.*;
import com.x1.frans.franchise.command.domain.aggregate.FranchiseEntity;
import com.x1.frans.franchise.command.domain.repository.FranchiseCommandRepository;
import com.x1.frans.product.command.domain.aggregate.ProductEntity;
import com.x1.frans.product.command.domain.repository.ProductRepository;
import com.x1.frans.returns.command.domain.aggregate.ReturnDetailEntity;
import com.x1.frans.returns.command.domain.aggregate.ReturnEntity;
import com.x1.frans.returns.command.domain.aggregate.ReturnFileEntity;
import com.x1.frans.returns.command.domain.repository.ReturnCommandRepository;
import com.x1.frans.returns.command.domain.repository.ReturnDetailCommandRepository;
import com.x1.frans.returns.command.domain.repository.ReturnFileCommandRepository;
import com.x1.frans.returns.command.domain.vo.ReturnCreateRequestVO;
import com.x1.frans.returns.command.domain.vo.ReturnDetailRequestVO;
import com.x1.frans.returns.command.domain.vo.ReturnFileRequestVO;
import com.x1.frans.returns.enums.ProductReturnStatus;
import com.x1.frans.returns.enums.ReturnStatus;
import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.command.repository.UserCommandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReturnCommandServiceImpl implements ReturnCommandService {

    private final ReturnCommandRepository returnRepository;
    private final UserCommandRepository userRepository;
    private final ProductRepository productRepository;
    private final FranchiseCommandRepository franchiseRepository;
    private final ReturnDetailCommandRepository returnDetailRepository;
    private final ReturnFileCommandRepository returnFileRepository;

    public ReturnCommandServiceImpl(ReturnCommandRepository returnRepository,
                                    UserCommandRepository userCommandRepository,
                                    ProductRepository productRepository,
                                    FranchiseCommandRepository franchiseRepository,
                                    ReturnDetailCommandRepository returnDetailRepository,
                                    ReturnFileCommandRepository returnFileRepository) {
        this.returnRepository = returnRepository;
        this.userRepository = userCommandRepository;
        this.productRepository = productRepository;
        this.franchiseRepository = franchiseRepository;
        this.returnDetailRepository = returnDetailRepository;
        this.returnFileRepository = returnFileRepository;

    }

    @Override
    @Transactional
    public void registReturn(Long franchiseId, ReturnCreateRequestVO vo, Long userId) {

        // 사용자 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("가맹점주의 정보를 찾을 수 없습니다."));

        FranchiseEntity franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new FranchiseNotFoundException("가맹점의 정보를 찾을 수 없습니다"));

        // 반품 코드 생성
        String returnCode = generateReturnCode(franchiseId);

        // 유효성 검사 수행
        validateReturnRequest(vo);

        // 반품 총 금액 계산
        BigDecimal totalAmount = calculateTotalAmount(vo.getDetails());

        // 반품 엔티티 생성
        ReturnEntity returnEntity = returnRepository.save(
                ReturnEntity.builder()
                        .description(vo.getDescription())
                        .franchiseId(franchise.getId())
                        .totalAmount(totalAmount)
                        .userId(user.getId())
                        .code(returnCode)
                        .status(ReturnStatus.WAITING_FOR_RECEIPT)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        // 반품 상세 내역 생성
        List<ReturnDetailEntity> returnDetails = createReturnDetails(vo.getDetails(), returnEntity);
        returnDetailRepository.saveAll(returnDetails);

        // 반품 첨부파일 정보 생성
        if (vo.getFiles() != null && !vo.getFiles().isEmpty()) {
            List<ReturnFileEntity> returnFiles = createReturnFiles(vo.getFiles(), returnEntity);
            returnFileRepository.saveAll(returnFiles);
        }
    }

    private BigDecimal calculateTotalAmount(List<ReturnDetailRequestVO> details) {
        return details.stream()
                .map(this::getReturnItemPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getReturnItemPrice(ReturnDetailRequestVO detail) {
        ProductEntity product = productRepository.findById(detail.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("해당하는 자재가 없습니다"));
        return product.getSalePrice().multiply(BigDecimal.valueOf(detail.getQuantity()));
    }

    private void validateReturnRequest(ReturnCreateRequestVO vo) {

        // 반품 사유 누락 확인
        if (vo.getDescription() == null || vo.getDescription().trim().isEmpty()) {
            throw new InvalidReturnArgumentException("반품 사유는 필수 입력값입니다");
        }

        // 반품 첨부파일 누락 확인
        if (vo.getFiles() == null || vo.getFiles().isEmpty()) {
            throw new InvalidReturnArgumentException("반품 사진은 필수 입력값입니다");
        }

    }

    private String generateReturnCode(Long franchiseId) {
        // 가맹점 정보 조회
        FranchiseEntity franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new FranchiseNotFoundException("가맹점 정보를 찾을 수 없습니다."));

        // 현재 날짜 (YYYYMMDD 형식)
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String franchisePrefix = franchise.getCode();
        int franchiseReturnCount = returnRepository.countByReturnCodePrefix(franchisePrefix);

        // 일련번호 생성 (0001부터 시작)
        String sequenceNumber = String.format("%04d", franchiseReturnCount + 1);

        // 최종 반품 코드 생성: [가맹점코드][날짜][일련번호]
        // 예) FR001202412150001
        String returnCode = franchisePrefix + currentDate + sequenceNumber;

        return returnCode;
    }

    private List<ReturnDetailEntity> createReturnDetails(List<ReturnDetailRequestVO> details, ReturnEntity returnEntity) {
        List<ReturnDetailEntity> returnDetails = new ArrayList<>();

        for (ReturnDetailRequestVO detail : details) {

            // 반품 상세 엔티티 생성
            ReturnDetailEntity returnDetail = new ReturnDetailEntity();
            returnDetail.setReturnEntity(returnEntity);
            returnDetail.setProductId(detail.getProductId());
            returnDetail.setQuantity(detail.getQuantity());
            returnDetail.setOrderId(detail.getOrderId());
            returnDetail.setStatus(ProductReturnStatus.WAITING);
            returnDetail.setReturnType(detail.getReturnType());

            returnDetails.add(returnDetail);
        }

        return returnDetails;
    }

    private List<ReturnFileEntity> createReturnFiles(List<ReturnFileRequestVO> files,
                                                     ReturnEntity returnEntity) {

        List<ReturnFileEntity> returnFiles = new ArrayList<>();

        for (ReturnFileRequestVO file : files) {

            // 반품 첨부파일 엔티티 생성
            ReturnFileEntity returnFile = new ReturnFileEntity();
            returnFile.setReturnId(returnEntity);
            returnFile.setName(file.getName());
            returnFile.setUrl(file.getUrl());

            returnFiles.add(returnFile);
        }

        return returnFiles;
    }

}
