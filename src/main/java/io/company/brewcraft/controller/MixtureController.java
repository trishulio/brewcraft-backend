//package io.company.brewcraft.controller;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import javax.validation.Valid;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//import io.company.brewcraft.dto.AddBrewLogDto;
//import io.company.brewcraft.dto.AddMixtureDto;
//import io.company.brewcraft.dto.AddMixtureWithLogDto;
//import io.company.brewcraft.dto.MixtureDto;
//import io.company.brewcraft.dto.PageDto;
//import io.company.brewcraft.dto.UpdateMixtureDto;
//import io.company.brewcraft.dto.UpdateMixtureWithLogDto;
//import io.company.brewcraft.model.BrewLog;
//import io.company.brewcraft.model.Mixture;
//import io.company.brewcraft.service.MixtureService;
//import io.company.brewcraft.service.exception.EntityNotFoundException;
//import io.company.brewcraft.service.mapper.BrewLogMapper;
//import io.company.brewcraft.service.mapper.MixtureMapper;
//import io.company.brewcraft.util.controller.AttributeFilter;
//import io.company.brewcraft.util.validator.Validator;
//
//@RestController
//@RequestMapping(path = "/api/v1/mixtures", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//public class MixtureController extends BaseController {
//    private static final Logger log = LoggerFactory.getLogger(MixtureController.class);
//    
//    private MixtureService mixtureService;
//    
//    private MixtureMapper mixtureMapper = MixtureMapper.INSTANCE;
//    
//    private BrewLogMapper brewLogMapper = BrewLogMapper.INSTANCE;
//        
//    public MixtureController(MixtureService mixtureService, AttributeFilter filter) {
//        super(filter);
//        this.mixtureService = mixtureService;
//    }
//    
//    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
//    public PageDto<MixtureDto> getMixtures(
//            @RequestParam(required = false) Set<Long> ids,
//            @RequestParam(required = false, name = "parent_mixture_ids") Set<Long> parentMixtureIds,
//            @RequestParam(required = false, name = "equipment_ids") Set<Long> equipmentIds,
//            @RequestParam(required = false, name = "brew_ids") Set<Long> brewIds,
//            @RequestParam(required = false, name = "brew_batch_ids") Set<Long> brewBatchIds,
//            @RequestParam(required = false, name = "stage_status_ids") Set<Long> stageStatusIds,
//            @RequestParam(required = false, name = "stage_task_ids") Set<Long> stageTaskIds,
//            @RequestParam(required = false, name = "product_ids") Set<Long> productIds,
//            @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) Set<String> sort,
//            @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
//            @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
//            @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size) {
//        
//        Page<Mixture> mixturePage = mixtureService.getMixtures(ids, parentMixtureIds, equipmentIds, brewIds, brewBatchIds, stageStatusIds, stageTaskIds, productIds, page, size, sort, orderAscending);
//        
//        List<MixtureDto> mixtureList = mixturePage.stream()
//                .map(mixture -> mixtureMapper.toDto(mixture)).collect(Collectors.toList());
//
//        PageDto<MixtureDto> dto = new PageDto<MixtureDto>(mixtureList, mixturePage.getTotalPages(), mixturePage.getTotalElements());
//        
//        return dto;
//    }
//        
//    @GetMapping(value = "/{mixtureId}", consumes = MediaType.ALL_VALUE)
//    public MixtureDto getMixture(@PathVariable Long mixtureId) {
//        Validator validator = new Validator();
//        
//        Mixture mixture = mixtureService.getMixture(mixtureId);
//                
//        validator.assertion(mixture != null, EntityNotFoundException.class, "Mixture", mixtureId.toString());
//
//        return mixtureMapper.toDto(mixture);
//    }
//
//    @PostMapping("")
//    @ResponseStatus(HttpStatus.CREATED)
//    public MixtureDto addMixture(@Valid @RequestBody AddMixtureWithLogDto addMixtureWithLog) {
//    	AddMixtureDto addMixtureDto = addMixtureWithLog.getAddMixtureDto();
//    	AddBrewLogDto addBrewLogDto = addMixtureWithLog.getAddBrewLogDto();
//
//        Mixture mixture = mixtureMapper.fromDto(addMixtureDto);
//        BrewLog brewLog = brewLogMapper.fromDto(addBrewLogDto);
//        
//        Mixture addedMixture = mixtureService.addMixture(mixture, addMixtureDto.getParentMixtureId(), addMixtureDto.getEquipmentId(), brewLog, addBrewLogDto.getUserId(), addBrewLogDto.getBrewStageId());
//        
//        return mixtureMapper.toDto(addedMixture);
//    }
//    
//    @PutMapping("/{mixtureId}")
//    public MixtureDto putMixture(@Valid @RequestBody UpdateMixtureWithLogDto updateMixtureWithLogDto, @PathVariable Long mixtureId) {
//    	UpdateMixtureDto updateMixtureDto = updateMixtureWithLogDto.getUpdateMixtureDto();
//    	AddBrewLogDto addBrewLogDto = updateMixtureWithLogDto.getAddBrewLogDto();
//    	
//    	Mixture mixture = mixtureMapper.fromDto(updateMixtureDto);
//        BrewLog brewLog = brewLogMapper.fromDto(addBrewLogDto);
//        
//        Mixture putMixture = mixtureService.putMixture(mixtureId, mixture, updateMixtureDto.getParentMixtureId(), updateMixtureDto.getEquipmentId(), brewLog, addBrewLogDto.getUserId(), addBrewLogDto.getBrewStageId());
//
//        return mixtureMapper.toDto(putMixture);
//    }
//    
//    @PatchMapping("/{mixtureId}")
//    public MixtureDto patchMixture(@Valid @RequestBody UpdateMixtureWithLogDto updateMixtureWithLogDto, @PathVariable Long mixtureId) {        
//    	UpdateMixtureDto updateMixtureDto = updateMixtureWithLogDto.getUpdateMixtureDto();
//    	AddBrewLogDto addBrewLogDto = updateMixtureWithLogDto.getAddBrewLogDto();
//
//    	Mixture mixture = mixtureMapper.fromDto(updateMixtureDto);
//        BrewLog brewLog = brewLogMapper.fromDto(addBrewLogDto);
//        
//        Mixture patchedMixture = mixtureService.patchMixture(mixtureId, mixture, updateMixtureDto.getParentMixtureId(), updateMixtureDto.getEquipmentId(), brewLog, addBrewLogDto.getUserId(), addBrewLogDto.getBrewStageId());
//        
//        return mixtureMapper.toDto(patchedMixture);
//    }
//
//    @DeleteMapping(value = "/{mixtureId}", consumes = MediaType.ALL_VALUE)
//    public void deleteMixture(@PathVariable Long mixtureId) {
//        mixtureService.deleteMixture(mixtureId);
//    }
//}
