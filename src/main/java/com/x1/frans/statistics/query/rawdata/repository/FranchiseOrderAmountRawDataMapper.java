package com.x1.frans.statistics.query.rawdata.repository;

import com.x1.frans.statistics.query.rawdata.dto.FranchiseOrderAmountRawDTO;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FranchiseOrderAmountRawDataMapper {

    List<FranchiseOrderAmountRawDTO> findTotalOrderAmountByFranchise(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

}
