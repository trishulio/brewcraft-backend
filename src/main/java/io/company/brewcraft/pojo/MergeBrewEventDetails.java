package io.company.brewcraft.pojo;

public class MergeBrewEventDetails implements IMergeBrewEventDetails {
    
    private Long mergeBrewId;

    public MergeBrewEventDetails(Long mergeBrewId) {
        super();
        this.mergeBrewId = mergeBrewId;
    }

    public Long getMergeBrewId() {
        return mergeBrewId;
    }

    public void setMergeBrewId(Long mergeBrewId) {
        this.mergeBrewId = mergeBrewId;
    }
    
}
