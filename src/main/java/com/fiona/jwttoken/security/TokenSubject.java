package com.fiona.jwttoken.security;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class TokenSubject {
    private String uid;
    private String uname;
    private List<String> roles;

}
