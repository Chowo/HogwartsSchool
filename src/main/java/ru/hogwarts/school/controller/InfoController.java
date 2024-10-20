package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.PortService;

@RestController
public class InfoController {

    private final PortService service;

    public InfoController(PortService service) {
        this.service = service;
    }


    @GetMapping("/get/port")
    public String getPort() {
        return service.getPort();
    }

}
