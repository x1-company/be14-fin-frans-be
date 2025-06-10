package com.x1.frans.franchise.command.domain.repository;

import com.x1.frans.franchise.command.domain.aggregate.FranchiseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FranchiseCommandRepository extends JpaRepository<FranchiseEntity, Integer> {

    Optional<FranchiseEntity> findById(Integer id);

}
