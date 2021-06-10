package io.company.brewcraft.pojo;

import java.util.List;

import javax.measure.Quantity;

import io.company.brewcraft.model.BrewLog;
import io.company.brewcraft.model.BrewMeasureValue;

public class BrewEventRebuilder {
    
    @SuppressWarnings("rawtypes")
    public static IBrewEvent rebuild(BrewLog log) {
        IBrewEvent event = null;
        
        switch (log.getType().getName()) {
            case AddMaterialsEvent.EVENT_NAME:
                event = rebuildAddMaterialsEvent(log);
                break;
            case RecordMeasuresEvent.EVENT_NAME:
                event = rebuildRecordMeasuresEvent(log);
                break;
            case BrewTransferEvent.EVENT_NAME:
                event = rebuildTranserEvent(log);
                break;
            //case RecordWasteEvent.EVENT_NAME:
            //  break;
            default:
                throw new RuntimeException("Unspported brew log type");
        }
        
        return event;
    }
    
    private static AddMaterialsEvent rebuildAddMaterialsEvent(BrewLog log) {
//       List<MaterialLot> currentMaterials = log.getMixture().getMaterials();
        
        BrewLog previousLog = log.getPreviousLog();
        
//        List<MaterialLot> previousMaterials = previousLog && previousLog.getMixture() ? previousLog.getMixture().getMaterials() : new ArrayList<>();
//        
//        List<MaterialLot> difference = someFunction(currentMaterials, previousMaterials);
//        
        //AddMaterialsEventDetails details = new AddMaterialsEventDetails(difference);
        return null; //new AddMaterialsEvent(log, details);
    }
    
    private static RecordMeasuresEvent rebuildRecordMeasuresEvent(BrewLog log) {
        List<BrewMeasureValue> recordedMeasures = log.getRecordedMeasures();
        
        RecordMeasuresEventDetails details = new RecordMeasuresEventDetails(recordedMeasures);
        return new RecordMeasuresEvent(log, details);
    }
        
    private static BrewTransferEvent rebuildTranserEvent(BrewLog log) {
        Long fromEquipmentId = log.getPreviousLog().getMixture().getEquipment().getId();
        Long toEquipmentId = log.getMixture().getEquipment().getId();
        Quantity<?> quantity = log.getMixture().getQuantity();
             
        BrewTransferEventDetails details = new BrewTransferEventDetails(fromEquipmentId, toEquipmentId, quantity);
        return new BrewTransferEvent(log, details);
    }
}
