package io.company.brewcraft.dto;

import java.util.List;

public class GetSupplierContactsDto {

    List<SupplierContactWithSupplierDto> supplierContacts;

    Long totalItems;

    int totalPages;

    public GetSupplierContactsDto() {

    }

    public GetSupplierContactsDto(List<SupplierContactWithSupplierDto> supplierContacts, Long totalItems, int totalPages) {
        this.supplierContacts = supplierContacts;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public List<SupplierContactWithSupplierDto> getSupplierContacts() {
        return supplierContacts;
    }

    public void setSupplierContacts(List<SupplierContactWithSupplierDto> supplierContacts) {
        this.supplierContacts = supplierContacts;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}
