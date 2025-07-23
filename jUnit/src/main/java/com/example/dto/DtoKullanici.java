package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoKullanici {

    private String tckn;

    private String ad;

    private String soyad;

    private String password;

    private String aracPlaka;

}
