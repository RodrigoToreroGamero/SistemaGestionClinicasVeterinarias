package com.utp.integradorspringboot.controllers;

import com.utp.integradorspringboot.models.Cita;
import com.utp.integradorspringboot.repositories.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CitaController {

    @Autowired
    private CitaRepository citaRepository;

    @GetMapping("/citas/{id}/detalle")
    public String verDetalleCita(@PathVariable Long id, Model model) {
        Cita cita = citaRepository.findById(id).orElse(null);
        model.addAttribute("cita", cita);
        return "cita_detalle";
    }
}