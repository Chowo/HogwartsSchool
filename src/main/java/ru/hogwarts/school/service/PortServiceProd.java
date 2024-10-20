package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class PortServiceProd implements PortService {

    @Value("${server.port}")
    private String port;

    @Override
    public String getPort() {
        return port;
    }
}
