package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceItemDto extends BaseDto {
    private Long id;
    private String description;
    private QuantityDto quantity;
    private MoneyDto price;
    private TaxDto tax;
    private MoneyDto amount;
    private MaterialDto material;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private Integer version;

    public InvoiceItemDto() {
    }

    public InvoiceItemDto(Long id) {
        this();
        setId(id);
    }

    public InvoiceItemDto(Long id, String description, QuantityDto quantity, MoneyDto price, TaxDto tax, MoneyDto amount, MaterialDto material, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setDescription(description);
        setQuantity(quantity);
        setPrice(price);
        setTax(tax);
        setAmount(amount);
        setMaterial(material);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setVersion(version);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public QuantityDto getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }

    public MoneyDto getPrice() {
        return price;
    }

    public void setPrice(MoneyDto price) {
        this.price = price;
    }

    public TaxDto getTax() {
        return tax;
    }

    public void setTax(TaxDto tax) {
        this.tax = tax;
    }

    public MoneyDto getAmount() {
        return amount;
    }

    public void setAmount(MoneyDto amount) {
        this.amount = amount;
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

    public Integer getVersion() {
        return version;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
