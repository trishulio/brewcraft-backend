//package io.company.brewcraft.service.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.transaction.annotation.Transactional;
//
//import io.company.brewcraft.model.Brew;
//import io.company.brewcraft.model.BrewLog;
//import io.company.brewcraft.model.BrewStage;
//import io.company.brewcraft.model.BrewStageStatus;
//import io.company.brewcraft.model.Mixture;
//import io.company.brewcraft.pojo.AddMaterialsEvent;
//import io.company.brewcraft.pojo.AddMaterialsEventDetails;
//import io.company.brewcraft.pojo.BrewEventRebuilder;
//import io.company.brewcraft.pojo.BrewSplitResult;
//import io.company.brewcraft.pojo.BrewTransferEvent;
//import io.company.brewcraft.pojo.BrewTransferEventDetails;
//import io.company.brewcraft.pojo.IAddMaterialsEvent;
//import io.company.brewcraft.pojo.IBrewEvent;
//import io.company.brewcraft.pojo.IBrewTransferEvent;
//import io.company.brewcraft.pojo.IMergeBrewEvent;
//import io.company.brewcraft.pojo.IRecordMeasuresEvent;
//import io.company.brewcraft.pojo.ISplitBrewEvent;
//import io.company.brewcraft.pojo.RecordMeasuresEvent;
//import io.company.brewcraft.pojo.RecordMeasuresEventDetails;
//import io.company.brewcraft.pojo.SplitBrewEvent;
//import io.company.brewcraft.pojo.SplitBrewEventDetails;
//import io.company.brewcraft.pojo.MergeBrewEvent;
//import io.company.brewcraft.pojo.MergeBrewEventDetails;
//import io.company.brewcraft.service.BaseService;
//import io.company.brewcraft.service.BrewEventService;
//import io.company.brewcraft.service.BrewLogService;
//import io.company.brewcraft.service.BrewService;
//import io.company.brewcraft.service.BrewStageService;
//import io.company.brewcraft.service.exception.EntityNotFoundException;
//
//@Transactional
//public class BrewEventServiceImpl extends BaseService implements BrewEventService {
//    
//    private BrewService brewService;
//    
//    private BrewStageService brewStageService; 
//    
//    private BrewLogService brewLogService;
//
//    public BrewEventServiceImpl(BrewService brewService, BrewStageService brewStageService, BrewLogService brewLogService) {
//        this.brewService = brewService;  
//        this.brewStageService = brewStageService;
//        this.brewLogService = brewLogService;
//    }
//
//    @SuppressWarnings("rawtypes")
//    @Override
//    public Page<IBrewEvent> getBrewEvents(Set<Long> brewIds, Set<Long> stageIds, Set<Long> logIds, Set<String> types, int page, int size,
//            Set<String> sort, boolean orderAscending) {
//              
//        Page<BrewLog> brewLogs = brewLogService.getBrewLogs(logIds, brewIds, stageIds, types, page, size, sort, orderAscending);
//        
//        List<IBrewEvent> events = new ArrayList<>();
//        
//        brewLogs.getContent().forEach(log -> {
//            IBrewEvent event = BrewEventRebuilder.rebuild(log);
//            events.add(event);
//        });
//        
//        return new PageImpl<>(events);
//    }
//
//    @SuppressWarnings("rawtypes")
//    @Override
//    public IBrewEvent getBrewEvent(Long brewLogId) {
//        BrewLog log = brewLogService.getBrewLog(brewLogId);
//        IBrewEvent event = BrewEventRebuilder.rebuild(log); 
//        
//        return event;
//    }
//    
//    @Override
//    public AddMaterialsEvent addMaterials(Long stageId, IAddMaterialsEvent event) {       
//        //better to pass in equipmentId rather than mixtureId due to race conditions.  what if mixture has been updated and front-end sends old mixture id
//        //issue with having one brew consist of multiple mixtures in different equipment
//        
//        //General race condition issues because of event service.
//        
//        //Should pass in entire new mixture entity with version to prevent race conditions
//        
//        //should basically call mixtureService.patch(mixtureId, mixture) where mixture has the new updated list of materials
//               
//        BrewStage stage = Optional.ofNullable(brewStageService.getBrewStage(stageId)).orElseThrow(() -> new EntityNotFoundException("BrewStage", stageId.toString()));      
//        BrewLog log = event.getLog();
//        
//        Mixture newMixture = null ; //mixtureService.addMaterials(mixtureId, event.getDetails().getMaterialsLots());
//        
//        log.setMixture(newMixture);
//        BrewLog addedBrewLog = brewLogService.addBrewLog(log, stageId, AddMaterialsEvent.EVENT_NAME);
//        
//        return new AddMaterialsEvent(addedBrewLog, new AddMaterialsEventDetails(event.getDetails().getMaterialLots()));
//    }
//
//    @Override
//    public BrewTransferEvent transferBrew(Long stageId, IBrewTransferEvent event) {         
//        BrewStage stage = Optional.ofNullable(brewStageService.getBrewStage(stageId)).orElseThrow(() -> new EntityNotFoundException("BrewStage", stageId.toString()));      
//        BrewLog log = event.getLog();
//
//        Long mixtureId = event.getDetails().getMixtureId();//stage.getMixture().getId();
//        List<Mixture> newMixtures = null ;//mixtureService.transferMixtures(mixtureId, event.getDetails().getToEquipmentId());
//                
//        log.setMixture(newMixture);
//        BrewLog addedBrewLog = brewLogService.addBrewLog(log, stageId, BrewTransferEvent.EVENT_NAME);
//        
//        return new BrewTransferEvent(addedBrewLog, new BrewTransferEventDetails(null, event.getDetails().getToEquipmentId(), event.getDetails().getQuantity()));
//    }
//
//    @Override
//    public RecordMeasuresEvent recordMeasures(Long stageId, IRecordMeasuresEvent event) {
//        BrewStage stage = Optional.ofNullable(brewStageService.getBrewStage(stageId)).orElseThrow(() -> new EntityNotFoundException("BrewStage", stageId.toString()));
//
//        BrewLog log = event.getLog();
//                
//        Mixture mixture = stage.getMixture();
//        log.setMixture(mixture);
//        
//        BrewLog addedBrewLog = brewLogService.addBrewLog(log, stageId, RecordMeasuresEvent.EVENT_NAME);
//
//        return new RecordMeasuresEvent(addedBrewLog, new RecordMeasuresEventDetails(addedBrewLog.getRecordedMeasures()));
//
//    }
//}
