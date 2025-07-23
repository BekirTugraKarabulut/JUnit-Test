package com.example.service.impl;

import org.springframework.stereotype.Service;

@Service
public class AracPlakaServiceImpl {

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

}
