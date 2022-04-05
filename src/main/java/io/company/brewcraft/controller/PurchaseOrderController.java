package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.AddPurchaseOrderDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.PurchaseOrderDto;
import io.company.brewcraft.dto.UpdatePurchaseOrderDto;
import io.company.brewcraft.model.BasePurchaseOrder;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.UpdatePurchaseOrder;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.mapper.PurchaseOrderMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/purchases/orders")
public class PurchaseOrderController extends BaseController {
    private CrudControllerService<
        Long,
        PurchaseOrder,
        BasePurchaseOrder,
        UpdatePurchaseOrder,
        PurchaseOrderDto,
        AddPurchaseOrderDto,
        UpdatePurchaseOrderDto
    > controller;

    private final PurchaseOrderService purchaseOrderService;

    protected PurchaseOrderController(CrudControllerService<
            Long,
            PurchaseOrder,
            BasePurchaseOrder,
            UpdatePurchaseOrder,
            PurchaseOrderDto,
            AddPurchaseOrderDto,
            UpdatePurchaseOrderDto
        > controller, PurchaseOrderService purchaseOrderService)
    {
        this.controller = controller;
        this.purchaseOrderService = purchaseOrderService;
    }

    @Autowired
    public PurchaseOrderController(PurchaseOrderService purchaseOrderService, AttributeFilter filter) {
        this(new CrudControllerService<>(filter, PurchaseOrderMapper.INSTANCE, purchaseOrderService, "PurchaseOrder"), purchaseOrderService);
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
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
        Page<PurchaseOrder> orders = purchaseOrderService.getPurchaseOrders(ids, excludeIds, orderNumbers, supplierIds, sort, orderAscending, page, size);

        return this.controller.getAll(orders, attributes);
    }

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public PurchaseOrderDto getPurchaseOrder(@PathVariable("id") Long id, @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
        return this.controller.get(id, attributes);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<PurchaseOrderDto> postPurchaseOrder(@Valid @NotNull @RequestBody List<AddPurchaseOrderDto> addDtos) {
        return this.controller.add(addDtos);
    }

    @PutMapping
    public List<PurchaseOrderDto> putPurchaseOrder(@Valid @NotNull @RequestBody List<UpdatePurchaseOrderDto> updateDtos) {
        return this.controller.put(updateDtos);
    }

    @PatchMapping
    public List<PurchaseOrderDto> patchPurchaseOrder(@Valid @NotNull @RequestBody List<UpdatePurchaseOrderDto> updateDtos) {
        return this.controller.patch(updateDtos);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public long deletePurchaseOrder(@RequestParam("ids") Set<Long> ids) {
        return this.controller.delete(ids);
    }
}
