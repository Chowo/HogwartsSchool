package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PortServiceDefault implements PortService {

    @Value("${server.port}")
    private String port;

    @Override
    public String getPort() {
        return port;
    }
}
