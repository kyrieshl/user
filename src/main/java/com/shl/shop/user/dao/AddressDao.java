package com.shl.shop.user.dao;

import com.shl.shop.user.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDao extends JpaRepository<Address,Integer> {
    Address findByAddressId(Integer addressId);
    List<Address> findAllByUserId(Integer userId);
    void deleteByUserId(Integer userId);
}
