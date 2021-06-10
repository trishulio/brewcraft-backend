package io.company.brewcraft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.AddBrewDto;
import io.company.brewcraft.dto.BrewTransferEventDto;
import io.company.brewcraft.dto.RecordMeasuresEventDto;
import io.company.brewcraft.dto.AddBrewTransferEventDto;
import io.company.brewcraft.dto.AddRecordMeasuresEventDto;
import io.company.brewcraft.dto.UpdateBrewDto;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.pojo.BrewTransferEvent;
import io.company.brewcraft.pojo.IBrewEvent;
import io.company.brewcraft.pojo.IBrewEventDto;
import io.company.brewcraft.pojo.RecordMeasuresEvent;

@Mapper(uses = { QuantityMapper.class, QuantityUnitMapper.class, BrewMeasureValueMapper.class})
public interface BrewEventMapper {

    BrewEventMapper INSTANCE = Mappers.getMapper(BrewEventMapper.class);

    BrewTransferEvent fromDto(AddBrewTransferEventDto dto);
    
    @Mapping(target = "log.recordedMeasures", source = "details.recordedMeasures")
    RecordMeasuresEvent fromDto(AddRecordMeasuresEventDto dto);

    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "parentBrew.id", source = "parentBrewId")
    Brew fromDto(AddBrewDto dto);
    
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "parentBrew.id", source = "parentBrewId")
    Brew fromDto(UpdateBrewDto dto);

    @Mapping(target = "log.type", source = "log.type.name")
    @Mapping(target = "log.brewStage.task", source = "log.brewStage.task.name")
    @Mapping(target = "log.brewStage.status", source = "log.brewStage.status.name")
    @Mapping(target = "log.brewStage.brewId", source = "log.brewStage.brew.id")
    BrewTransferEventDto toDto(BrewTransferEvent brewTransferEvent);
    
    @Mapping(target = "log.type", source = "log.type.name")
    @Mapping(target = "log.brewStage.task", source = "log.brewStage.task.name")
    @Mapping(target = "log.brewStage.status", source = "log.brewStage.status.name")
    @Mapping(target = "log.brewStage.brewId", source = "log.brewStage.brew.id")
    RecordMeasuresEventDto toDto(RecordMeasuresEvent recordMeasuresEvent);
   
    default IBrewEventDto toDto(IBrewEvent brewEvent) {
        if (brewEvent instanceof BrewTransferEvent) {
            return toDto((BrewTransferEvent) brewEvent);
        } else if(brewEvent instanceof RecordMeasuresEvent) {
            return toDto((RecordMeasuresEvent) brewEvent);
        }  else {
            throw new RuntimeException("Unsupported event type");
        }
    }
    
}
