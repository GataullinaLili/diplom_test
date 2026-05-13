package com.itemstorage.repository;

import com.itemstorage.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    // Поиск по точному номеру медицинской карты
    Optional<Patient> findByMedicalCardNumber(String medicalCardNumber);
    
    // Поиск по части ФИО
    @Query("SELECT p FROM Patient p WHERE LOWER(p.fullName) LIKE LOWER(CONCAT('%', :fio, '%'))")
    List<Patient> findByFullNameContaining(String fio);
    
    // Поиск по ФИО и дате рождения
    @Query("SELECT p FROM Patient p WHERE LOWER(p.fullName) LIKE LOWER(CONCAT('%', :fio, '%')) " +
           "AND p.birthDate = :birthDate")
    List<Patient> findByFullNameAndBirthDate(String fio, LocalDate birthDate);
    
    // Поиск по номеру карты (содержит)
    @Query("SELECT p FROM Patient p WHERE p.medicalCardNumber LIKE CONCAT('%', :cardNumber, '%')")
    List<Patient> findByMedicalCardNumberContaining(String cardNumber);
}
