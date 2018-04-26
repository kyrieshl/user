package com.shl.shop.user.dao;

import com.shl.shop.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User,Integer>{
    User findByUserNameAndPassword(String userName,String password);
    User findByUserName(String userName);
    Page<User> findAllByUserIdIn(List<Integer> userIdList, Pageable pageable);
}
