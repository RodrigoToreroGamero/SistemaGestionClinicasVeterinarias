/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utp.integradorspringboot.models.Cita;

/**
 *
 * @author USER
 */
public interface GestionCitaRepository extends JpaRepository<Cita, Long>{
    
    @Query("SELECT c FROM Cita c " +
           "LEFT JOIN FETCH c.dueno d " +
           "LEFT JOIN FETCH d.usuario " +
           "LEFT JOIN FETCH c.veterinario v " +
           "LEFT JOIN FETCH v.usuario " +
           "LEFT JOIN FETCH c.mascota " +
           "WHERE c.fecha = :fecha ORDER BY c.hora ASC")
    List<Cita> findByFecha(@Param("fecha") LocalDate fecha);
    
}