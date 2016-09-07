package pl.java.scalatech.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.model.Vets;
import pl.java.scalatech.service.ClinicService;

@RestController
@RequestMapping("/vetXml")
@Slf4j
public class VetXmlController {

    
    private final ClinicService clinicService;

    public VetXmlController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @RequestMapping("/")
    public Vets  showVetList() {        
        Vets vets = new Vets();
        vets.getVetList().addAll(this.clinicService.findVets());
        return vets;
        
        
    }
}
