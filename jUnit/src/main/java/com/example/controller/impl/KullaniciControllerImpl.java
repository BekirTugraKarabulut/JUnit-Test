package com.example.controller.impl;

import com.example.controller.KullaniciController;
import com.example.dto.DtoKullanici;
import com.example.dto.DtoKullaniciUI;
import com.example.service.KullaniciService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rest/api")
@Tag(name = "Kullanici Controller" , description = "API İstekleri Yapılıyor")
public class KullaniciControllerImpl implements KullaniciController {

    @Autowired
    private KullaniciService kullaniciService;

    @Override
    @PostMapping(path = "/kullanici-ekle")
    public DtoKullanici ekleKullanici(@RequestBody DtoKullaniciUI dtoKullaniciUI) {
        return kullaniciService.ekleKullanici(dtoKullaniciUI);
    }

}
