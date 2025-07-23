package com.example.exception;

import lombok.Getter;

@Getter
public enum MessageType {

    TCKN_UZUNLUGU_HATALI("10" ,  "TCKN UZUNLUGU HATALI"),
    TCKN_İLK_RAKAMI_0_OLAMAZ("11" , "TCKN İLK RAKAMI 0 OLAMAZ"),
    TCKN_SON_RAKAMI_TEK_OLAMAZ("12" , "TCKN Son RAKAMI TEK OLAMAZ"),
    TCKN_KULLANILMIS("13" , "TCKN KULLANILMIS"),
    TCKN_HATALI("14" , "TCKN HATALI"),
    KULLANICI_BULUNAMADI("15" , "Kullanici Bulunamadi");

    private String code;
    private String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }


}
