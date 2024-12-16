package com.example.demo.Member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Member {
    private int id;
    private String nama;
    private String email;
    private String password;
}
