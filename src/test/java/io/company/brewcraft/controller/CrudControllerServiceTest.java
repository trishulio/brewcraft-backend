package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.model.BaseEntity;
import io.company.brewcraft.service.CrudService;
import io.company.brewcraft.service.UpdatableEntity;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.BaseMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

public class CrudControllerServiceTest {
    private CrudControllerService<Long, Pojo, BasePojo, UpdatePojo, Dto, AddDto, UpdateDto> controller;

    private CrudService<Long, Pojo, BasePojo, UpdatePojo, ?> mService;
    private AttributeFilter mFilter;

    @BeforeEach
    public void init() {
        BaseMapper<Pojo, Dto, AddDto, UpdateDto> mapper = PojoMapper.INSTANCE;
        mService = mock(CrudService.class);

        mFilter = mock(AttributeFilter.class);
        doNothing().when(mFilter).retain(any(), anySet());

        controller = new CrudControllerService<>(mFilter, mapper, mService, "Pojo");
    }

    @Test
    public void testGetAll_ReturnsDtosBuiltFromEntitiesAndFilterAttributes() {
        Page<Pojo> page = new PageImpl<>(List.of(new Pojo(100L, 100), new Pojo(200L, 200), new Pojo(300L, 300)));
        PageDto<Dto> expected = new PageDto<>(List.of(new Dto(100L, 100), new Dto(200L, 200), new Dto(300L, 300)), 1, 3);

        PageDto<Dto> dto = controller.getAll(page, Set.of("prop1", "prop2"));

        assertEquals(expected, dto);
        verify(mFilter, times(1)).retain(expected.getContent().get(0), Set.of("prop1", "prop2"));
        verify(mFilter, times(1)).retain(expected.getContent().get(1), Set.of("prop1", "prop2"));
        verify(mFilter, times(1)).retain(expected.getContent().get(2), Set.of("prop1", "prop2"));
    }

    @Test
    public void testGet_ReturnsDtoFromServicePojoWithAllAttributes_WhenServiceReturnsPojo() {
        doReturn(new Pojo(100L, 100)).when(mService).get(100L);

        Dto dto = this.controller.get(100L, Set.of("prop1", "prop2"));

        Dto expected = new Dto(100L, 100);

        assertEquals(expected, dto);
        verify(mFilter, times(1)).retain(expected, Set.of("prop1", "prop2"));
    }

    @Test
    public void testGet_ThrowsEntityNotFoundException_WhenServiceReturnsNull() {
        doReturn(null).when(mService).get(100L);

        assertThrows(EntityNotFoundException.class, () -> controller.get(100L, Set.of("")));
    }

    @Test
    public void testAdd_ReturnsListOfDtosFromServiceAddReturnValues() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mService).add(anyList());

        List<Dto> dtos = this.controller.add(List.of(new AddDto()));

        List<Dto> expected = List.of(new Dto(null, null));

        assertEquals(expected, dtos);
    }

    @Test
    public void testPut_ReturnsListOfDtosFromServiceAddReturnValues() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mService).put(anyList());

        List<Dto> dtos = this.controller.put(List.of(new UpdateDto(100L, 100)));

        List<Dto> expected = List.of(new Dto(100L, 100));

        assertEquals(expected, dtos);
    }

    @Test
    public void testPatch_ReturnsListOfDtosFromServiceAddReturnValues() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mService).patch(anyList());

        List<Dto> dtos = this.controller.patch(List.of(new UpdateDto(100L, 100)));

        List<Dto> expected = List.of(new Dto(100L, 100));

        assertEquals(expected, dtos);
    }

    @Test
    public void testDelete_ReturnsCountFromServiceDelete() {
        doReturn(100L).when(mService).delete(Set.of(1L, 2L, 3L));

        assertEquals(100L, controller.delete(Set.of(1L, 2L, 3L)));
    }
}

class Pojo extends BaseEntity implements UpdatePojo {
    private Long id;
    private Integer version;

    public Pojo(Long id, Integer version) {
        setId(id);
        setVersion(version);
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}

interface BasePojo {
}

interface UpdatePojo extends BasePojo, UpdatableEntity<Long> {
}

class AddDto extends BaseDto {
}

class Dto extends BaseDto {
    private Long id;
    private Integer version;

    public Dto(Long id, Integer version) {
        setId(id);
        setVersion(version);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}

class UpdateDto extends BaseDto {
    private Long id;
    private Integer version;

    public UpdateDto(Long id, Integer version) {
        setId(id);
        setVersion(version);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}

@Mapper
interface PojoMapper extends BaseMapper<Pojo, Dto, AddDto, UpdateDto> {
    PojoMapper INSTANCE = Mappers.getMapper(PojoMapper.class);
}