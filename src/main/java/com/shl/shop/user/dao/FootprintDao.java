package com.shl.shop.user.dao;

import com.shl.shop.user.model.Footprint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FootprintDao extends JpaRepository<Footprint,Integer>{
    Footprint findByFootprintId(Integer footprintId);
    List<Footprint> findAllByUserId(Integer userId, Pageable pageable);
}
