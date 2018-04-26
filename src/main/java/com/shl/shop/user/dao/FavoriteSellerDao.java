package com.shl.shop.user.dao;

import com.shl.shop.user.model.FavoriteSeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteSellerDao extends JpaRepository<FavoriteSeller,Integer> {
    FavoriteSeller findByUserIdAndSellerId(Integer userId,Integer sellerId);
    List<FavoriteSeller> findAllByUserId(Integer userId);
    void deleteByUserId(Integer userId);
    void deleteBySellerId(Integer sellerId);
}
