package com.mshop.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private String password;
    private Boolean gender;
    private String address;
    private String phone;
    //    private String image;
    private Date registerDate;
    private Boolean status;
    private Boolean role;
}
