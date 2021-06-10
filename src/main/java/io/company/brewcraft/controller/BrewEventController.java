//package io.company.brewcraft.controller;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import javax.validation.Valid;
//
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
//import io.company.brewcraft.dto.AddAddMaterialsEventDto;
//import io.company.brewcraft.dto.AddBrewDto;
//import io.company.brewcraft.dto.BrewDto;
//import io.company.brewcraft.dto.BrewTransferEventDto;
//import io.company.brewcraft.dto.MergeBrewEventDto;
//import io.company.brewcraft.dto.AddBrewTransferEventDto;
//import io.company.brewcraft.dto.AddMaterialsEventDto;
//import io.company.brewcraft.dto.AddRecordMeasuresEventDto;
//import io.company.brewcraft.dto.PageDto;
//import io.company.brewcraft.dto.RecordMeasuresEventDto;
//import io.company.brewcraft.dto.SplitBrewEventDto;
//import io.company.brewcraft.dto.UpdateBrewDto;
//import io.company.brewcraft.model.Brew;
//import io.company.brewcraft.pojo.AddMaterialsEvent;
//import io.company.brewcraft.pojo.BaseBrewEvent;
//import io.company.brewcraft.pojo.BrewTransferEvent;
//import io.company.brewcraft.pojo.IAddMaterialsEvent;
//import io.company.brewcraft.pojo.IBrewEvent;
//import io.company.brewcraft.pojo.IBrewEventDto;
//import io.company.brewcraft.pojo.IBrewTransferEvent;
//import io.company.brewcraft.pojo.IRecordMeasuresEvent;
//import io.company.brewcraft.pojo.RecordMeasuresEvent;
//import io.company.brewcraft.service.BrewEventService;
//import io.company.brewcraft.service.BrewService;
//import io.company.brewcraft.service.exception.EntityNotFoundException;
//import io.company.brewcraft.service.mapper.BrewEventMapper;
//import io.company.brewcraft.service.mapper.BrewMapper;
//import io.company.brewcraft.util.validator.Validator;
//
//@RestController
//@RequestMapping(path = "/api/v1/brews", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//public class BrewEventController {
//    
//    private BrewEventService brewEventService;
//    
//    private BrewEventMapper brewEventMapper = BrewEventMapper.INSTANCE;
//        
//    public BrewEventController(BrewEventService brewEventService) {
//        this.brewEventService = brewEventService;
//    }
//    
//    @GetMapping(value = "/stages/events", consumes = MediaType.ALL_VALUE)
//    public List<IBrewEventDto> getBrewEvents(
//    //public PageDto<IBrewEventDto> getBrewEvents(
//            @PathVariable(required = false) Set<Long> brewIds,
//            @PathVariable(required = false) Set<Long> stageIds,
//            @RequestParam(required = false) Set<Long> logIds,
//            @RequestParam(required = false) Set<String> types,
//            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size,
//            @RequestParam(defaultValue = "id") Set<String> sort, @RequestParam(defaultValue = "true", name = "order_asc") boolean orderAscending) {
//        Page<IBrewEvent> brewEventPage = brewEventService.getBrewEvents(brewIds, stageIds, logIds, types, page, size, sort, orderAscending);
//        
//        List<IBrewEventDto> brewEventList = brewEventPage.stream()
//                .map(brewEvent -> brewEventMapper.toDto(brewEvent)).collect(Collectors.toList());
//
//        //PageDto<IBrewEventDto> dto = new PageDto<IBrewEventDto>(brewEventList, brewEventPage.getTotalPages(), brewEventPage.getTotalElements());
//        
//        return brewEventList;
//    }
//        
//    @GetMapping(value = "/stages/events/{eventId}", consumes = MediaType.ALL_VALUE)
//    public IBrewEventDto getBrewEvent(@PathVariable Long eventId) {
//        Validator validator = new Validator();
//        
//        IBrewEvent brewEvent = brewEventService.getBrewEvent(eventId);
//                
//        validator.assertion(brewEvent != null, EntityNotFoundException.class, "BrewEvent", eventId.toString());
//
//        return brewEventMapper.toDto(brewEvent);
//    }
//    
//    @PostMapping("/stages/{stageId}/events/addMaterials")
//    @ResponseStatus(HttpStatus.CREATED)
//    public AddMaterialsEventDto addMaterialsEvent(@PathVariable Long stageId, @Valid @RequestBody AddAddMaterialsEventDto addMaterialsEventDto) {
//        IAddMaterialsEvent addMaterialsEvent = brewEventMapper.fromDto(addMaterialsEventDto);
//          
//        AddMaterialsEvent addedAddMaterialsEvent = brewEventService.addMaterials(stageId, addMaterialsEvent);
//        
//        return brewEventMapper.toDto(addedAddMaterialsEvent);
//    }
//
//    @PostMapping("/stages/{stageId}/events/transfer")
//    @ResponseStatus(HttpStatus.CREATED)
//    public BrewTransferEventDto addTransferEvent(@PathVariable Long stageId, @Valid @RequestBody AddBrewTransferEventDto brewTransferEventDto) {
//        IBrewTransferEvent brewTransferEvent = brewEventMapper.fromDto(brewTransferEventDto);
//        
//        BrewTransferEvent addedTransferEvent = brewEventService.transferBrew(stageId, brewTransferEvent);
//        
//        return brewEventMapper.toDto(addedTransferEvent);
//    }
//    
//    @PostMapping("/stages/{stageId}/events/recordMeasures")
//    @ResponseStatus(HttpStatus.CREATED)
//    public RecordMeasuresEventDto addRecordMeasuresEvent(@PathVariable Long stageId, @Valid @RequestBody AddRecordMeasuresEventDto recordMeasuresEventDto) {
//        IRecordMeasuresEvent recordMeasuresEvent = brewEventMapper.fromDto(recordMeasuresEventDto);
//        
//        RecordMeasuresEvent addedRecordMeasuresEvent = brewEventService.recordMeasures(stageId, recordMeasuresEvent);
//        
//        return brewEventMapper.toDto(addedRecordMeasuresEvent);
//    }
//    
//    @PostMapping("/stages/{stageId}/events/merge/{mergeBrewId}")
//    @ResponseStatus(HttpStatus.CREATED)
//    public MergeBrewEventDto addMergeEvent(@PathVariable Long stageId, @Valid @RequestBody AddBrewTransferEventDto brewTransferEventDto) {
//        IBrewTransferEvent brewTransferEvent = brewEventMapper.fromDto(brewTransferEventDto);
//        
//        BrewTransferEvent addedTransferEvent = brewEventService.transferBrew(stageId, brewTransferEvent);
//        
//        return brewEventMapper.toDto(addedTransferEvent);
//    }
//    
//    @PostMapping("/stages/{stageId}/events/split")
//    @ResponseStatus(HttpStatus.CREATED)
//    public SplitBrewEventDto addSplitEvent(@PathVariable Long stageId, @Valid @RequestBody AddBrewSplitEvent brewSplitEventDto) {
//        IBrewSplitEvent brewSplitEvent = brewEventMapper.fromDto(brewSplitEventDto);
//        
//        BrewTransferEvent addedTransferEvent = brewEventService.transferBrew(stageId, brewTransferEvent);
//        
//        return brewEventMapper.toDto(addedTransferEvent);
//    }
//    
//    
////    
////    @PutMapping("/{brewId}")
////    public BrewEventDto putBrewEvent(@Valid @RequestBody UpdateBrewDto updateBrewDto, @PathVariable Long brewId) {
////        Brew brew = brewEventMapper.fromDto(updateBrewDto);
////        
////        Brew putBrew = brewEventService.putBrew(brewId, brew, updateBrewDto.getProductId(), updateBrewDto.getParentBrewId());
////
////        return brewEventMapper.toDto(putBrew);
////    }
////    
////    @PatchMapping("/{brewId}")
////    public BrewEventDto patchBrewEvent(@Valid @RequestBody UpdateBrewDto updateBrewDto, @PathVariable Long brewId) {        
////        Brew brew = brewEventMapper.fromDto(updateBrewDto);
////        
////        Brew patchedBrew = brewEventService.patchBrew(brewId, brew, updateBrewDto.getProductId(), updateBrewDto.getParentBrewId());
////        
////        return brewEventMapper.toDto(patchedBrew);
////    }
////
////    @DeleteMapping(value = "/{brewId}", consumes = MediaType.ALL_VALUE)
////    public void deleteBrewEvent(@PathVariable Long brewId) {
////        brewEventService.deleteBrewEvent(brewId);
////    }
//}
