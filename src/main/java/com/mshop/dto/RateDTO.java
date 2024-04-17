package com.mshop.dto;

import com.mshop.entity.User;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
public class RateDTO {
    private Long id;
    private Double star;
    private String comment;
    private Date rateDate;
    private UserDTO user;
    private ProductDTO product;
}
