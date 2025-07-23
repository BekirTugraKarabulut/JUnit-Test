package com.example.repository;

import com.example.model.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KullaniciRepository extends JpaRepository<Kullanici,String> {

    Optional<Kullanici> findByTckn(String tckn);

}
