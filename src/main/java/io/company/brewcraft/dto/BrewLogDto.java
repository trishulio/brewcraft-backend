package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class BrewLogDto extends BaseDto {
    
    private Long id;
        
    private String type;
    
    private Long userId;
    
    private BrewStageDto brewStage;
    
    private LocalDateTime recordedAt;
            
    private String comment;
            
    private Integer version;

    public BrewLogDto(Long id, String type, Long userId, BrewStageDto brewStage, LocalDateTime recordedAt,
            String comment, Integer version) {
        super();
        this.id = id;
        this.type = type;
        this.userId = userId;
        this.brewStage = brewStage;
        this.recordedAt = recordedAt;
        this.comment = comment;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BrewStageDto getBrewStage() {
        return brewStage;
    }

    public void setBrewStage(BrewStageDto brewStage) {
        this.brewStage = brewStage;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
}
