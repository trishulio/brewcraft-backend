package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.service.CrudService;
import io.company.brewcraft.service.UpdatableEntity;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.BaseMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

public class CrudControllerService<
    ID,
    Entity extends UpdateEntity,
    AddEntity,
    UpdateEntity extends UpdatableEntity<ID>,
    EntityDto extends BaseDto,
    AddDto extends BaseDto,
    UpdateDto extends BaseDto
> {
    private AttributeFilter filter;
    private BaseMapper<Entity, EntityDto, AddDto, UpdateDto> mapper;
    private CrudService<ID, Entity, AddEntity, UpdateEntity, ?> service;
    private String entityName;

    public CrudControllerService(
        AttributeFilter filter,
        BaseMapper<Entity, EntityDto, AddDto, UpdateDto> mapper,
        CrudService<ID, Entity, AddEntity, UpdateEntity, ?> service,
        String entityName
    ) {
        this.filter = filter;
        this.mapper = mapper;
        this.service = service;
        this.entityName = entityName;
    }

    public PageDto<EntityDto> getAll(Page<Entity> entities, Set<String> attributes) {
        final List<EntityDto> content = entities.stream().map(i -> mapper.toDto(i)).collect(Collectors.toList());
        content.forEach(invoice -> this.filter(invoice, attributes));

        final PageDto<EntityDto> dto = new PageDto<>();
        dto.setContent(content);
        dto.setTotalElements(entities.getTotalElements());
        dto.setTotalPages(entities.getTotalPages());
        return dto;
    }

    public EntityDto get(ID id, Set<String> attributes) {
        Entity e = this.service.get(id);
        Validator.assertion(e != null, EntityNotFoundException.class, this.entityName, id.toString());

        EntityDto dto = mapper.toDto(e);
        this.filter(dto, attributes);

        return dto;
    }

    public List<EntityDto> add(List<AddDto> addDtos) {
        List<AddEntity> additions = (List<AddEntity>) addDtos.stream().map(dto -> mapper.fromAddDto(dto)).collect(Collectors.toList());
        List<Entity> added = this.service.add(additions);

        return added.stream().map(entity -> mapper.toDto(entity)).collect(Collectors.toList());
    }

    public List<EntityDto> put(List<UpdateDto> updateDtos) {
        List<UpdateEntity> updates = updateDtos.stream().map(dto -> mapper.fromUpdateDto(dto)).collect(Collectors.toList());
        List<Entity> updated = this.service.put(updates);

        return updated.stream().map(entity -> mapper.toDto(entity)).collect(Collectors.toList());
    }

    public List<EntityDto> patch(List<UpdateDto> updateDtos) {
        List<UpdateEntity> updates = updateDtos.stream().map(dto -> mapper.fromUpdateDto(dto)).collect(Collectors.toList());
        List<Entity> patched = this.service.patch(updates);

        return patched.stream().map(entity -> mapper.toDto(entity)).collect(Collectors.toList());
    }

    public int delete(Set<ID> ids) {
        return this.service.delete(ids);
    }

    private void filter(BaseDto dto, Set<String> retainAttr) {
        if (retainAttr != null && retainAttr.size() > 0) {
            this.filter.retain(dto, retainAttr);
        }
    }
}
