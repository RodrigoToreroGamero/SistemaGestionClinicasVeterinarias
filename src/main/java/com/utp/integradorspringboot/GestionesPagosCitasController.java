package com.utp.integradorspringboot;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jcerv
 */
@Controller
public class GestionesPagosCitasController {
    
    @RequestMapping("/GestionesPagosCitas")
    public String page() {
        //model.addAttribute("attribute", "value");
        return "/recepcionista/GestionPagosCitas";
    }
}
