package com.example.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kullanici")
@Schema(description = "Kullanici Bilgileri")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kullanici {

    @Id
    @Column(name = "tckn")
    private String tckn;

    @Column(name = "ad")
    private String ad;

    @Column(name = "soyad")
    private String soyad;

    @Column(name = "password")
    private String password;

}
