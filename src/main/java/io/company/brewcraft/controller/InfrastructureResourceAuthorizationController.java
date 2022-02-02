package io.company.brewcraft.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.AddInfrastructureResourceAuthorizationDto;
import io.company.brewcraft.dto.InfrastructureResourceAuthorizationDto;

@RestController
@RequestMapping(value = "/api/v1/infra/authorization", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class InfrastructureResourceAuthorizationController {

    public InfrastructureResourceAuthorizationDto post(AddInfrastructureResourceAuthorizationDto payload) {
        
        // TODO:
        return null;
    }
}
