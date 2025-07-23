package com.example.service;

import com.example.service.impl.AracPlakaServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("Araç Plaka Doğrulama Testleri")
public class AracPlakaServiceImplTest {

    private final AracPlakaServiceImpl aracPlakaDogrulama = new AracPlakaServiceImpl();

    @Test
    @DisplayName("Geçerli plaka - 34ABC123")
    void testGecerliPlaka() {
        String sonuc = aracPlakaDogrulama.aracPlakaDogrulama("34ABC123");
        assertEquals("34ABC123", sonuc);
    }

    @Test
    @DisplayName("Plaka uzunluğu geçersiz (çok kısa)")
    void testPlakaCokKisa() {
        String sonuc = aracPlakaDogrulama.aracPlakaDogrulama("3A1");
        assertEquals("Plaka uzunluğu geçersiz", sonuc);
    }

    @Test
    @DisplayName("Geçersiz il kodu")
    void testGecersizIlKodu() {
        String sonuc = aracPlakaDogrulama.aracPlakaDogrulama("99ABC123");
        assertEquals("Geçersiz il kodu", sonuc);
    }

    @Test
    @DisplayName("Geçersiz harf karakteri (küçük harf)")
    void testGecersizHarfKucuk() {
        String sonuc = aracPlakaDogrulama.aracPlakaDogrulama("34abc123");
        assertEquals("Geçersiz harf karakteri", sonuc);
    }

    @Test
    @DisplayName("Geçersiz harf karakteri (Türkçe karakter)")
    void testGecersizHarfTurkce() {
        String sonuc = aracPlakaDogrulama.aracPlakaDogrulama("34ÇŞĞ123");
        assertEquals("Geçersiz harf karakteri", sonuc);
    }

    @Test
    @DisplayName("Harf sayısı 0 olduğunda hata verir")
    void testHarfSayisiYok() {
        String sonuc = aracPlakaDogrulama.aracPlakaDogrulama("34 1234");
        assertEquals("En az 1 harf olmalı", sonuc);
    }

    @Test
    @DisplayName("Harf sayısı 4 olduğunda hata verir")
    void testHarfSayisiFazla() {
        String sonuc = aracPlakaDogrulama.aracPlakaDogrulama("34ABCD123");
        assertEquals("En fazla 3 harf olabilir", sonuc);
    }

    @Test
    @DisplayName("Sayı kısmı 1 basamaklı olduğunda hata verir")
    void testSayiCokKisa() {
        String sonuc = aracPlakaDogrulama.aracPlakaDogrulama("34ABC1");
        assertEquals("Son kısmı 2-4 basamaklı sayı olmalı", sonuc);
    }

    @Test
    @DisplayName("Sayı kısmı harf içerdiğinde hata verir")
    void testSayiHarfIceriyor() {
        String sonuc = aracPlakaDogrulama.aracPlakaDogrulama("34ABC12A");
        assertEquals("Son kısmı 2-4 basamaklı sayı olmalı", sonuc);
    }

    @Test
    @DisplayName("Geçerli kısa il kodu - 7AB12")
    void testGecerliTekHaneliIlKodu() {
        String sonuc = aracPlakaDogrulama.aracPlakaDogrulama("7AB12");
        assertEquals("7AB12", sonuc);
    }

}
