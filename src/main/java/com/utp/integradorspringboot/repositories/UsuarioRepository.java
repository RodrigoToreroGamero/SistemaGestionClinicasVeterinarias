/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.repositories;
import com.utp.integradorspringboot.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author UTP
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
