package com.utp.integradorspringboot.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.utp.integradorspringboot.models.Boleta_pago;
import com.utp.integradorspringboot.models.Cita;
import com.utp.integradorspringboot.models.Dueno;
import com.utp.integradorspringboot.models.Rol;
import com.utp.integradorspringboot.models.Sesion;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.models.Usuario_rol;
import com.utp.integradorspringboot.repositories.BoletaPagoRepository;
import com.utp.integradorspringboot.repositories.DuenoRepository;
import com.utp.integradorspringboot.repositories.GestionCitaRepository;
import com.utp.integradorspringboot.repositories.RolRepository;
import com.utp.integradorspringboot.repositories.SesionRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import com.utp.integradorspringboot.repositories.UsuarioRolRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class DuenoController {

    @Autowired
    private DuenoRepository duenoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioRolRepository usuarioRolRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private GestionCitaRepository gestionCitaRepository;
    @Autowired
    private BoletaPagoRepository boletaPagoRepository;
    @Autowired
    private SesionRepository sesionRepository;

    @GetMapping("/recepcionista/clientes")
    public String gestionarClientes(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        // TODO: Add authentication/authorization check if needed
        List<Dueno> duenos = duenoRepository.findAll();
        model.addAttribute("duenos", duenos);
        return "recepcionista/gestionar_clientes";
    }

    @PostMapping("/recepcionista/clientes/enroll")
    @ResponseBody
    public Map<String, Object> enrollCliente(@RequestBody Map<String, String> data) {
        Map<String, Object> resp = new HashMap<>();
        try {
            String email = data.get("email");
            if (email == null || email.trim().isEmpty()) {
                resp.put("success", false);
                resp.put("error", "El email es obligatorio");
                return resp;
            }
            Usuario usuario = new Usuario();
            usuario.setNombres(data.get("nombres"));
            usuario.setApellidos(data.get("apellidos"));
            usuario.setDni(data.get("dni"));
            usuario.setCelular(data.get("celular"));
            usuario.setFecha_nacimiento(LocalDate.parse(data.get("fecha_nacimiento")));
            usuario.setFecha_registro(LocalDateTime.now());
            usuario = usuarioRepository.save(usuario);
            // Asignar rol DUENO
            Rol rolDueno = rolRepository.findByNombre("DUENO").orElseThrow();
            Usuario_rol ur = new Usuario_rol();
            ur.setUsuario(usuario);
            ur.setRol(rolDueno);
            usuarioRolRepository.save(ur);
            // Crear Dueno
            Dueno dueno = new Dueno();
            dueno.setUsuario(usuario);
            duenoRepository.save(dueno);
            // Crear Sesion asociada
            Sesion sesion = new Sesion();
            sesion.setUsuario(usuario);
            sesion.setCorreo(email);
            sesionRepository.save(sesion);
            resp.put("success", true);
            resp.put("dueno", dueno);
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("error", e.getMessage());
        }
        return resp;
    }

    @GetMapping("/recepcionista/clientes/{duenoId}/citas")
    @ResponseBody
    public List<Map<String, Object>> getCitasByDueno(@PathVariable Long duenoId) {
        // Custom query needed in GestionCitaRepository
        List<Cita> citas = gestionCitaRepository.findAll().stream()
            .filter(c -> c.getDueno() != null && c.getDueno().getId().equals(duenoId))
            .collect(Collectors.toList());
        return citas.stream().map(cita -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", cita.getId());
            map.put("fecha", cita.getFecha());
            map.put("hora", cita.getHora());
            map.put("estado", cita.getEstado());
            String motivo = "";
            if (cita.getDetalleCita() != null && cita.getDetalleCita().getMotivo_cita() != null) {
                motivo = cita.getDetalleCita().getMotivo_cita().getNombre();
            }
            map.put("motivo", motivo);
            map.put("veterinario", cita.getVeterinario() != null && cita.getVeterinario().getUsuario() != null ? cita.getVeterinario().getUsuario().getNombres() + " " + cita.getVeterinario().getUsuario().getApellidos() : "");
            // Buscar boleta de pago asociada
            Boleta_pago boleta = null;
            if (cita.getDetalleCita() != null) {
                boleta = boletaPagoRepository.findByDetalleCita(cita.getDetalleCita()).orElse(null);
            }
            if (boleta != null) {
                map.put("pago_estado", boleta.getMetodo_pago());
                map.put("monto", boleta.getMonto_total());
            } else {
                map.put("pago_estado", "Pendiente");
                map.put("monto", "");
            }
            return map;
        }).collect(Collectors.toList());
    }
} 