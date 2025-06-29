package com.x1.frans.purchase.query.repository;

import com.x1.frans.purchase.query.dto.PurchaseRequestDetailDto;
import com.x1.frans.purchase.query.dto.PurchaseRequestProductSimpleDto;
import com.x1.frans.purchase.query.dto.PurchaseRequestSimpleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchaseRequestQueryMapper {

    List<PurchaseRequestSimpleDto> selectByStatus(@Param("status") String status,
                                                  @Param("limit") int limit,
                                                  @Param("offset") int offset);
    int countByStatus(@Param("status") String status);

    List<PurchaseRequestSimpleDto> selectByTitle(@Param("title") String title,
                                                 @Param("limit") int limit,
                                                 @Param("offset") int offset);
    int countByTitle(@Param("title") String title);

    List<PurchaseRequestSimpleDto> selectByCode(@Param("code") String code,
                                                @Param("limit") int limit,
                                                @Param("offset") int offset);
    int countByCode(@Param("code") String code);

    List<PurchaseRequestSimpleDto> selectAllRequests(@Param("limit") int limit,
                                                     @Param("offset") int offset);
    int countAllRequests();

    List<PurchaseRequestSimpleDto> selectDrafts(@Param("limit") int limit,
                                                @Param("offset") int offset);
    int countDrafts();

    PurchaseRequestDetailDto selectDetailById(@Param("id") Long id);
    PurchaseRequestDetailDto selectDraftDetailById(@Param("id") Long id);

    List<PurchaseRequestProductSimpleDto> selectProductsByRequestId(@Param("requestId") Long requestId);

    List<PurchaseRequestSimpleDto> selectBySupplierId(@Param("supplierId") Long supplierId,
                                                    @Param("limit") int limit,
                                                    @Param("offset") int offset);
    int countBySupplierId(@Param("supplierId") Long supplierId);
}
