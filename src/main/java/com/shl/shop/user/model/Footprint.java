package com.shl.shop.user.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "footprint")
@Entity
public class Footprint implements Serializable{

    private static final long serialVersionUID = 667277198157573928L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer footprintId;

    private Integer userId;

    private Integer productId;

    private Date addTime;

    private Boolean deleted;
}
