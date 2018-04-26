package com.shl.shop.user.dao;

import com.shl.shop.user.model.FavoriteSeller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteSellerDao extends JpaRepository<FavoriteSeller,Integer> {
    FavoriteSeller findByUserIdAndSellerId(Integer userId,Integer sellerId);
    Page<FavoriteSeller> findAllByUserId(Integer userId, Pageable pageable);
    void deleteByUserId(Integer userId);
    void deleteBySellerId(Integer sellerId);
}
