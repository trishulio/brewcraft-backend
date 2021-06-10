package io.company.brewcraft.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import io.company.brewcraft.pojo.AddMaterialsEvent;
import io.company.brewcraft.pojo.BrewTransferEvent;
import io.company.brewcraft.pojo.IAddMaterialsEvent;
import io.company.brewcraft.pojo.IBrewEvent;
import io.company.brewcraft.pojo.IBrewTransferEvent;
import io.company.brewcraft.pojo.IMergeBrewEvent;
import io.company.brewcraft.pojo.IRecordMeasuresEvent;
import io.company.brewcraft.pojo.ISplitBrewEvent;
import io.company.brewcraft.pojo.MergeBrewEvent;
import io.company.brewcraft.pojo.RecordMeasuresEvent;
import io.company.brewcraft.pojo.SplitBrewEvent;

public interface BrewEventService {
    
    @SuppressWarnings("rawtypes")
    Page<IBrewEvent> getBrewEvents(Set<Long> brewIds, Set<Long> stageIds, Set<Long> logIds, Set<String> types, int page, int size, Set<String> sort, boolean orderAscending);
    
    @SuppressWarnings("rawtypes")
    IBrewEvent getBrewEvent(Long brewLogId);
    
    AddMaterialsEvent addMaterials(Long stageId, IAddMaterialsEvent event) ;
    
    BrewTransferEvent transferBrew(Long stageId, IBrewTransferEvent event);  
    
    RecordMeasuresEvent recordMeasures(Long stageId, IRecordMeasuresEvent event);
    
    SplitBrewEvent splitBrew(Long stageId, ISplitBrewEvent event);
    
    MergeBrewEvent mergeBrew(Long stageId, IMergeBrewEvent event);

//    public BrewRecordWasteEventDetails recordWaste(BrewRecordWasteEvent event);



 }

