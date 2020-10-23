package io.company.brewcraft.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.service.SupplierService;

@RestController
@RequestMapping(path = "/api")
public class SupplierController {
    
    private SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/suppliers")
    public List<SupplierDto> getAll() {
        return supplierService.getSuppliers();
    }
    
    @GetMapping("/suppliers/{id}")
    public SupplierDto getSupplier(@PathVariable Long id) {
        return supplierService.getSupplier(id);
    }

    @PostMapping("/suppliers")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSupplier(@Valid @RequestBody SupplierDto newSupplier) {
        supplierService.addSupplier(newSupplier);
    }
    
    @PutMapping("/suppliers/{id}")
    public void updateSupplier(@Valid @RequestBody SupplierDto updatedSupplier, @PathVariable Long id) {
        supplierService.updateSupplier(updatedSupplier, id);
    }

    @DeleteMapping("/suppliers/{id}")
    public void deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
    }

}
