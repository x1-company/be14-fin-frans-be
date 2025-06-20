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

    PurchaseRequestDetailDto selectDetailById(@Param("id") Long id);

    List<PurchaseRequestProductSimpleDto> selectProductsByRequestId(@Param("requestId") Long requestId);
}