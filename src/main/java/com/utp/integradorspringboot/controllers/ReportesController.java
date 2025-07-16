package com.utp.integradorspringboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.utp.integradorspringboot.repositories.BoletaPagoRepository;
import com.utp.integradorspringboot.repositories.GestionCitaRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ReportesController {

    @Autowired
    private BoletaPagoRepository boletaPagoRepository;

    @Autowired
    private GestionCitaRepository citaRepository;

    @GetMapping("/recepcionista/reportes")
    public String reportesPage(Model model) {
        return "recepcionista/reportes";
    }

    @GetMapping("/api/reportes/pagos-por-fecha")
    @ResponseBody
    public Map<String, Object> getPagosPorFecha(@RequestParam String fecha) {
        Map<String, Object> response = new HashMap<>();
        try {
            LocalDate fechaLocal = LocalDate.parse(fecha);
            
            // Obtener todas las boletas de pago para la fecha especificada
            List<Map<String, Object>> boletas = boletaPagoRepository.findAll().stream()
                .filter(boleta -> {
                    if (boleta.getFecha_emision() != null) {
                        LocalDate fechaBoleta = boleta.getFecha_emision().toLocalDate();
                        return fechaBoleta.equals(fechaLocal);
                    }
                    return false;
                })
                .map(boleta -> {
                    Map<String, Object> boletaMap = new HashMap<>();
                    boletaMap.put("id", boleta.getId());
                    boletaMap.put("fecha_emision", boleta.getFecha_emision());
                    boletaMap.put("monto_total", boleta.getMonto_total());
                    boletaMap.put("metodo_pago", boleta.getMetodo_pago());
                    try {
                        if (boleta.getDetalle_cita() != null && boleta.getDetalle_cita().getCita() != null) {
                            var cita = boleta.getDetalle_cita().getCita();
                            boletaMap.put("cita_id", cita.getId());
                            boletaMap.put("cita_fecha", cita.getFecha());
                            boletaMap.put("cita_hora", cita.getHora());
                            // Información del cliente
                            if (cita.getDueno() != null && cita.getDueno().getUsuario() != null) {
                                var usuario = cita.getDueno().getUsuario();
                                boletaMap.put("cliente_nombre", usuario.getNombres() + " " + usuario.getApellidos());
                                boletaMap.put("cliente_dni", usuario.getDni());
                            }
                            // Información de la mascota
                            if (cita.getMascota() != null) {
                                boletaMap.put("mascota_nombre", cita.getMascota().getNombre());
                            }
                            // Información del veterinario
                            if (cita.getVeterinario() != null && cita.getVeterinario().getUsuario() != null) {
                                var vetUsuario = cita.getVeterinario().getUsuario();
                                boletaMap.put("veterinario_nombre", vetUsuario.getNombres() + " " + vetUsuario.getApellidos());
                            }
                            // Información del motivo de cita
                            if (cita.getDetalleCita() != null && cita.getDetalleCita().getMotivo_cita() != null) {
                                boletaMap.put("motivo_nombre", cita.getDetalleCita().getMotivo_cita().getNombre());
                                boletaMap.put("motivo_precio", cita.getDetalleCita().getMotivo_cita().getPrecio());
                            }
                        }
                    } catch (Exception ex) {
                        // Si hay error de referencia, simplemente no agregues info extra
                    }
                    return boletaMap;
                })
                .collect(Collectors.toList());
            
            // Calcular totales
            double totalEfectivo = boletas.stream()
                .filter(b -> "Efectivo".equals(b.get("metodo_pago")))
                .mapToDouble(b -> (Double) b.get("monto_total"))
                .sum();
                
            double totalPOS = boletas.stream()
                .filter(b -> "POS".equals(b.get("metodo_pago")))
                .mapToDouble(b -> (Double) b.get("monto_total"))
                .sum();
                
            double totalYape = boletas.stream()
                .filter(b -> "Yape".equals(b.get("metodo_pago")))
                .mapToDouble(b -> (Double) b.get("monto_total"))
                .sum();
                
            double totalPlin = boletas.stream()
                .filter(b -> "Plin".equals(b.get("metodo_pago")))
                .mapToDouble(b -> (Double) b.get("monto_total"))
                .sum();
                
            double totalTransferencia = boletas.stream()
                .filter(b -> "Transferencia".equals(b.get("metodo_pago")))
                .mapToDouble(b -> (Double) b.get("monto_total"))
                .sum();
                
            double totalOtros = boletas.stream()
                .filter(b -> !"Efectivo".equals(b.get("metodo_pago")) && 
                           !"POS".equals(b.get("metodo_pago")) && 
                           !"Yape".equals(b.get("metodo_pago")) && 
                           !"Plin".equals(b.get("metodo_pago")) && 
                           !"Transferencia".equals(b.get("metodo_pago")))
                .mapToDouble(b -> (Double) b.get("monto_total"))
                .sum();
                
            double totalGeneral = boletas.stream()
                .mapToDouble(b -> (Double) b.get("monto_total"))
                .sum();
            
            response.put("success", true);
            response.put("boletas", boletas);
            response.put("totales", Map.of(
                "efectivo", totalEfectivo,
                "pos", totalPOS,
                "yape", totalYape,
                "plin", totalPlin,
                "transferencia", totalTransferencia,
                "otros", totalOtros,
                "general", totalGeneral
            ));
            response.put("fecha", fecha);
            response.put("cantidad_boletas", boletas.size());
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        
        return response;
    }
} 