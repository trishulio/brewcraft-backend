package io.company.brewcraft.controller;

import java.util.Set;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.util.controller.AttributeFilter;

public abstract class BaseController {

    private AttributeFilter filter;

    public BaseController(AttributeFilter filter) {
        this.filter = filter;
    }

    public void filter(BaseDto dto, Set<String> retainAttr) {
        if (retainAttr != null && retainAttr.size() > 0) {
            this.filter.retain(dto, retainAttr);
        }
    }

}
