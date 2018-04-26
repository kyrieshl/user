package com.shl.shop.user.dao;

import com.shl.shop.user.model.FavoriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteProductDao extends JpaRepository<FavoriteProduct,Integer> {
    FavoriteProduct findByUserIdAndProductId(Integer userId, Integer productId);
    void deleteByUserId(Integer userId);
}
