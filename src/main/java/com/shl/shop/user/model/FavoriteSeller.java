package com.shl.shop.user.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "FavoriteSeller")
@Entity
public class FavoriteSeller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Integer favoriteSellerId;

    private  Integer userId;

    private  Integer sellerId;

    private  Date collectTime;

    public FavoriteSeller(){

    }
}
