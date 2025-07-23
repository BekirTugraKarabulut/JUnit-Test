package com.example.service.impl;

import com.example.dto.DtoKullanici;
import com.example.dto.DtoKullaniciUI;
import com.example.exception.BaseException;
import com.example.exception.ErrorMessage;
import com.example.exception.MessageType;
import com.example.model.Kullanici;
import com.example.repository.KullaniciRepository;
import com.example.service.KullaniciService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KullaniciServiceImpl implements KullaniciService {

    @Autowired
    private KullaniciRepository kullaniciRepository;

    public String aracPlakaDogrulama(String aracPlaka) {

        String plaka = aracPlaka;

        if (plaka.length() < 5 || plaka.length() > 9) {
            return "Plaka uzunluğu geçersiz";
        }

        int ilKodu;

        if (Character.isDigit(plaka.charAt(1))) {
            ilKodu = Integer.parseInt(plaka.substring(0, 2));
        } else {
            ilKodu = Integer.parseInt(plaka.substring(0, 1));
        }

        if (ilKodu < 1 || ilKodu > 81) {
            return "Geçersiz il kodu";
        }

        int harfBaslangicIndex = (ilKodu < 10) ? 1 : 2;

        int harfSayisi = 0;
        while (harfBaslangicIndex + harfSayisi < plaka.length() &&
                Character.isLetter(plaka.charAt(harfBaslangicIndex + harfSayisi))) {

            char c = plaka.charAt(harfBaslangicIndex + harfSayisi);
            if (!Character.isUpperCase(c) || c == 'Ç' || c == 'Ş' || c == 'Ğ' || c == 'Ü' || c == 'İ' || c == 'Ö') {
                return "Geçersiz harf karakteri";
            }

            harfSayisi++;
            if (harfSayisi > 3) {
                return "En fazla 3 harf olabilir";
            }
        }

        if (harfSayisi < 1) {
            return "En az 1 harf olmalı";
        }

        int sayiBaslangicIndex = harfBaslangicIndex + harfSayisi;
        String sayiKismi = plaka.substring(sayiBaslangicIndex);

        if (sayiKismi.length() < 2 || sayiKismi.length() > 4 || !sayiKismi.chars().allMatch(Character::isDigit)) {
            return "Son kısmı 2-4 basamaklı sayı olmalı";
        }

        return plaka;
    }

    public String tcknDogrulama(String tckn){

        String dogrulanacakTckn = tckn;

        if (dogrulanacakTckn.length() != 11){
            throw new BaseException(new ErrorMessage(MessageType.TCKN_UZUNLUGU_HATALI , dogrulanacakTckn.length()));
        }

        if (Character.getNumericValue(dogrulanacakTckn.charAt(0)) == 0){
            throw new BaseException(new ErrorMessage(MessageType.TCKN_İLK_RAKAMI_0_OLAMAZ , dogrulanacakTckn.charAt(0)));
        }

        if(Character.getNumericValue(dogrulanacakTckn.charAt(10)) == 1 &&
                Character.getNumericValue(dogrulanacakTckn.charAt(10)) == 3 &&
                Character.getNumericValue(dogrulanacakTckn.charAt(10)) == 5 &&
                Character.getNumericValue(dogrulanacakTckn.charAt(10)) == 7 &&
                Character.getNumericValue(dogrulanacakTckn.charAt(10)) == 9 &&
                Character.getNumericValue(dogrulanacakTckn.charAt(10)) == 11){
            throw new BaseException(new ErrorMessage(MessageType.TCKN_SON_RAKAMI_TEK_OLAMAZ , dogrulanacakTckn));
        }

        Integer[] tcknDogrulama = new Integer[11];

        for (int i = 0; i < 11; i++){

            tcknDogrulama[i] = Character.getNumericValue(dogrulanacakTckn.charAt(i));

        }

        Integer tekBasamakToplamlari = tcknDogrulama[0] + tcknDogrulama[2] + tcknDogrulama[4] + tcknDogrulama[6] + tcknDogrulama[8];
        Integer ciftBasamakToplamlari = tcknDogrulama[1] + tcknDogrulama[3] + tcknDogrulama[5] + tcknDogrulama[7];
        Integer tekBasamakCarpimSonucu = tekBasamakToplamlari * 7;

        Integer eldeEdilenSonuc = tekBasamakCarpimSonucu - ciftBasamakToplamlari;

        if(eldeEdilenSonuc % 10 != tcknDogrulama[9]){
            throw new BaseException(new ErrorMessage(MessageType.TCKN_HATALI , dogrulanacakTckn));
        }

        Integer ilk10BasamakToplami = tekBasamakToplamlari + ciftBasamakToplamlari + tcknDogrulama[9];

        if(ilk10BasamakToplami % 10 != tcknDogrulama[10]){
            throw new BaseException(new ErrorMessage(MessageType.TCKN_HATALI , dogrulanacakTckn));
        }

        return dogrulanacakTckn;
    }

    @Override
    public DtoKullanici ekleKullanici(DtoKullaniciUI dtoKullaniciUI) {

        Kullanici kullanici = new Kullanici();

        kullanici.setTckn(tcknDogrulama(dtoKullaniciUI.getTckn()));
        kullanici.setAd(dtoKullaniciUI.getAd());
        kullanici.setSoyad(dtoKullaniciUI.getSoyad());
        kullanici.setPassword(dtoKullaniciUI.getPassword());

        Kullanici dbKullanici = kullaniciRepository.save(kullanici);
        DtoKullanici dto = new DtoKullanici();
        BeanUtils.copyProperties(dbKullanici, dto);

        return dto;
    }

}
