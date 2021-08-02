package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.company.brewcraft.dto.AddPurchaseOrderDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.PurchaseOrderDto;
import io.company.brewcraft.dto.UpdatePurchaseOrderDto;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.PurchaseOrderMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/purchases/orders")
public class PurchaseOrderController extends BaseController {

    private PurchaseOrderService service;

    public PurchaseOrderController(AttributeFilter filter, PurchaseOrderService service) {
        super(filter);
        this.service = service;
    }

    @GetMapping
    public PageDto<PurchaseOrderDto> getAllPurchaseOrders(
        @RequestParam(name = "ids", required = false) Set<Long> ids,
        @RequestParam(name = "exclude_ids", required = false) Set<Long> excludeIds,
        @RequestParam(name = "order_numbers", required = false) Set<String> orderNumbers,
        @RequestParam(name = "supplier_ids", required = false) Set<Long> supplierIds,
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
        @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes
    ) {

        Page<PurchaseOrder> orders = service.getAllPurchaseOrders(ids, excludeIds, orderNumbers, supplierIds, sort, orderAscending, page, size);
        
        return response(orders, attributes);
    }

    @GetMapping("/{id}")
    public PurchaseOrderDto getPurchaseOrder(@PathVariable("id") Long id) {
        PurchaseOrder po = service.getPurchaseOrder(id);

        PurchaseOrderDto dto = PurchaseOrderMapper.INSTANCE.toDto(po);
        
        Validator.assertion(dto != null, EntityNotFoundException.class, "Purchase Order", id.toString());
        
        return dto;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public PurchaseOrderDto postPurchaseOrder(@Valid @NotNull @RequestBody AddPurchaseOrderDto dto) {
        PurchaseOrder addition = PurchaseOrderMapper.INSTANCE.fromDto(dto);
        
        PurchaseOrder po = service.add(addition);
        
        return PurchaseOrderMapper.INSTANCE.toDto(po);
    }
    
    @PutMapping("/{purchaseOrderId}")
    public PurchaseOrderDto putPurchaseOrder(@PathVariable("purchaseOrderId") Long purchaseOrderId, @Valid @NotNull @RequestBody UpdatePurchaseOrderDto dto) {
        PurchaseOrder addition = PurchaseOrderMapper.INSTANCE.fromDto(dto);
        
        PurchaseOrder po = service.put(purchaseOrderId, addition);
        
        return PurchaseOrderMapper.INSTANCE.toDto(po);
    }
    
    @PatchMapping("/{purchaseOrderId}")
    public PurchaseOrderDto patchPurchaseOrder(@PathVariable("purchaseOrderId") Long purchaseOrderId, @Valid @NotNull @RequestBody UpdatePurchaseOrderDto dto) {
        PurchaseOrder addition = PurchaseOrderMapper.INSTANCE.fromDto(dto);
        
        PurchaseOrder po = service.patch(purchaseOrderId, addition);
        
        return PurchaseOrderMapper.INSTANCE.toDto(po);
    }
    
    @DeleteMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void deletePurchaseOrder(@RequestParam("ids") Set<Long> ids) {
        this.service.delete(ids);
    }

    private PageDto<PurchaseOrderDto> response(Page<PurchaseOrder> purchaseOrder, Set<String> attributes) {
        List<PurchaseOrderDto> content = purchaseOrder.stream().map(i -> PurchaseOrderMapper.INSTANCE.toDto(i)).collect(Collectors.toList());
        content.forEach(invoice -> filter(invoice, attributes));

        PageDto<PurchaseOrderDto> dto = new PageDto<PurchaseOrderDto>();
        dto.setContent(content);
        dto.setTotalElements(purchaseOrder.getTotalElements());
        dto.setTotalPages(purchaseOrder.getTotalPages());
        return dto;
    }
}
