package com.x1.frans.returns.command.domain.repository;

import com.x1.frans.returns.command.domain.aggregate.ReturnEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnCommandRepository extends JpaRepository<ReturnEntity, Long> {

    @Query("SELECT COUNT(r) FROM ReturnEntity r WHERE r.code LIKE CONCAT(:prefix, '%')")
    int countByReturnCodePrefix(@Param("prefix") String prefix);
}
