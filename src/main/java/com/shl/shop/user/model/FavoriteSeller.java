package com.shl.shop.user.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "FavoriteSeller")
@Entity
public class FavoriteSeller implements Serializable{

    private static final long serialVersionUID = 3268155740517892922L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Integer favoriteSellerId;

    private  Integer userId;

    private  Integer sellerId;

    private  Date collectTime;

    public FavoriteSeller(){

    }
}
