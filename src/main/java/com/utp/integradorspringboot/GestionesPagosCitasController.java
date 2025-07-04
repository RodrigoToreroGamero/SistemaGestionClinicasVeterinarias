package com.utp.integradorspringboot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jcerv
 */
@Controller
public class GestionesPagosCitasController {
    @Autowired
    @RequestMapping("/GestionesPagosCitas")
    public String page() {
        //model.addAttribute("attribute", "value");
        return "/VistaSecretario/GestionPagosCitas";
    }
}