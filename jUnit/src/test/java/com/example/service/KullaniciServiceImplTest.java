package com.example.service;

import com.example.exception.BaseException;
import com.example.exception.MessageType;
import com.example.repository.KullaniciRepository;
import com.example.service.impl.KullaniciServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TCKN Doğrulama Testleri")
class KullaniciServiceImplTest {

    @Mock
    private KullaniciRepository kullaniciRepository;

    @InjectMocks
    private KullaniciServiceImpl kullaniciService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Geçerli TCKN - Başarılı doğrulama")
    void tcknDogrulama_GecerliTckn_BasariliSonuc() {
        String gecerliTckn = "12345678910";

        String sonuc = kullaniciService.tcknDogrulama(gecerliTckn);

        assertEquals(gecerliTckn, sonuc);
        assertDoesNotThrow(() -> kullaniciService.tcknDogrulama(gecerliTckn));
    }

    @Test
    @DisplayName("Geçerli TCKN - Farklı örnek")
    void tcknDogrulama_GecerliTckn2_BasariliSonuc() {

        String gecerliTckn = "98765432108";

        String sonuc = kullaniciService.tcknDogrulama(gecerliTckn);

        assertEquals(gecerliTckn, sonuc);
    }

    @Test
    @DisplayName("TCKN Uzunluğu 11'den kısa - Hata fırlatmalı")
    void tcknDogrulama_KisaUzunluk_BaseExceptionFirlatir() {

        String kisaTckn = "1234567891"; // 10 haneli

        BaseException exception = assertThrows(BaseException.class,
                () -> kullaniciService.tcknDogrulama(kisaTckn));

       assertEquals(MessageType.TCKN_UZUNLUGU_HATALI, exception.getMessage());
       assertEquals(10, exception.getMessage());
    }

    @Test
    @DisplayName("TCKN Uzunluğu 11'den uzun - Hata fırlatmalı")
    void tcknDogrulama_UzunUzunluk_BaseExceptionFirlatir() {

        String uzunTckn = "123456789101"; // 12 haneli

        BaseException exception = assertThrows(BaseException.class,
                () -> kullaniciService.tcknDogrulama(uzunTckn));

        assertEquals(MessageType.TCKN_UZUNLUGU_HATALI, exception.getMessage());
        assertEquals(12, exception.getMessage());
    }

    @Test
    @DisplayName("TCKN Boş string - Hata fırlatmalı")
    void tcknDogrulama_BosTckn_BaseExceptionFirlatir() {

        String bosTckn = "";

        BaseException exception = assertThrows(BaseException.class,
                () -> kullaniciService.tcknDogrulama(bosTckn));

          assertEquals(MessageType.TCKN_UZUNLUGU_HATALI, exception.getMessage());
    }

    @Test
    @DisplayName("TCKN ilk rakamı 0 - Hata fırlatmalı")
    void tcknDogrulama_IlkRakamSifir_BaseExceptionFirlatir() {

        String sifirBaslayanTckn = "01234567891";

        BaseException exception = assertThrows(BaseException.class,
                () -> kullaniciService.tcknDogrulama(sifirBaslayanTckn));

        assertEquals(MessageType.TCKN_İLK_RAKAMI_0_OLAMAZ, exception.getMessage());
        assertEquals('0', exception.getMessage());
    }

    @Test
    @DisplayName("TCKN matematiksel kontrolü başarısız - 9. hane hatası")
    void tcknDogrulama_DokuzuncuHaneHatali_BaseExceptionFirlatir() {

        String hataliTckn = "12345678900";

        BaseException exception = assertThrows(BaseException.class,
                () -> kullaniciService.tcknDogrulama(hataliTckn));

        assertEquals(MessageType.TCKN_HATALI, exception.getMessage());
        assertEquals(hataliTckn, exception.getMessage());
    }

    @Test
    @DisplayName("TCKN matematiksel kontrolü başarısız - 10. hane hatası")
    void tcknDogrulama_OnuncuHaneHatali_BaseExceptionFirlatir() {

        String hataliTckn = "12345678911";

        BaseException exception = assertThrows(BaseException.class,
                () -> kullaniciService.tcknDogrulama(hataliTckn));

        assertEquals(MessageType.TCKN_HATALI, exception.getMessage());
        assertEquals(hataliTckn, exception.getMessage());
    }

    @Test
    @DisplayName("TCKN null değer - NullPointerException beklenir")
    void tcknDogrulama_NullTckn_NullPointerExceptionFirlatir() {

        String nullTckn = null;


        assertThrows(NullPointerException.class,
                () -> kullaniciService.tcknDogrulama(nullTckn));
    }

    @Test
    @DisplayName("TCKN alfabetik karakter içeriyor - NumberFormatException beklenir")
    void tcknDogrulama_AlfabetikKarakter_NumberFormatExceptionFirlatir() {

        String alfabetikTckn = "1234567891A";


        assertThrows(NumberFormatException.class,
                () -> kullaniciService.tcknDogrulama(alfabetikTckn));
    }

    @Test
    @DisplayName("Son rakam tek sayı kontrolü - Kod hatası nedeniyle bu test geçmeyecek")
    void tcknDogrulama_SonRakamTekKontrolHatasi() {

        String sonRakamiTekTckn = "12345678901";

        assertDoesNotThrow(() -> kullaniciService.tcknDogrulama(sonRakamiTekTckn));
    }

    @Test
    @DisplayName("Minimum geçerli TCKN - 10000000000")
    void tcknDogrulama_MinimumGecerliTckn() {

        String minTckn = "10000000018";

        assertDoesNotThrow(() -> kullaniciService.tcknDogrulama(minTckn));
    }

    @Test
    @DisplayName("Maximum geçerli TCKN - 99999999999")
    void tcknDogrulama_MaximumGecerliTckn() {

        String maxTckn = "99999999998";

        assertDoesNotThrow(() -> kullaniciService.tcknDogrulama(maxTckn));
    }

    @Test
    @DisplayName("Bilinen geçerli TCKN örnekleri")
    void tcknDogrulama_BilinenGecerliOrnekler() {

        String[] gecerliTcknler = {
                "11111111110",
                "22222222220",
                "33333333330"
        };

        for (String tckn : gecerliTcknler) {
            assertDoesNotThrow(() -> kullaniciService.tcknDogrulama(tckn),
                    "TCKN " + tckn + " geçerli olmalı");
        }
    }
}