package com.itemstorage.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String login;
    private String password;
    private String fullName;
    private String role;
}
