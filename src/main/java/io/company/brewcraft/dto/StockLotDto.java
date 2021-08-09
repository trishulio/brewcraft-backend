package io.company.brewcraft.dto;

public class StockLotDto extends BaseDto {
    private Long id;
    private String lotNumber;
    private QuantityDto qty;
    private InvoiceItemDto invoiceItem;
    private StorageDto storage;

    public StockLotDto() {
    }

    public StockLotDto(Long id) {
        this();
        this.setId(id);
    }

    public StockLotDto(Long id, String lotNumber, QuantityDto qty, InvoiceItemDto invoiceItem, StorageDto storage) {
        this(id);
        this.setLotNumber(lotNumber);
        this.setQuantity(qty);
        this.setInvoiceItem(invoiceItem);
        this.setStorage(storage);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLotNumber() {
        return this.lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public QuantityDto getQuantity() {
        return this.qty;
    }

    public void setQuantity(QuantityDto qty) {
        this.qty = qty;
    }

    public InvoiceItemDto getInvoiceItem() {
        return this.invoiceItem;
    }

    public void setInvoiceItem(InvoiceItemDto invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public StorageDto getStorage() {
        return this.storage;
    }

    public void setStorage(StorageDto storage) {
        this.storage = storage;
    }
}
