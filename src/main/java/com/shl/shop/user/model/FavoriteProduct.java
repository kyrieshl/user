package com.shl.shop.user.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "FavoriteProduct")
@Entity
public class FavoriteProduct implements Serializable{

    private static final long serialVersionUID = 5835840132906900609L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer favoriteThingId;

    private Integer userId;

    private Integer productId;

    private Date collectTime;

    public FavoriteProduct(){

    }
}
