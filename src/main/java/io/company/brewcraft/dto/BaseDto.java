package io.company.brewcraft.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.company.brewcraft.model.BaseModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseDto extends BaseModel {
}
