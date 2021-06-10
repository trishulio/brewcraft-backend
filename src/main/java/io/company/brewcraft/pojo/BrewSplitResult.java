package io.company.brewcraft.pojo;

import io.company.brewcraft.model.Brew;

public class BrewSplitResult {
    
    private Brew parent;
    
    private Brew child;

    public BrewSplitResult(Brew parent, Brew child) {
        super();
        this.parent = parent;
        this.child = child;
    }

    public Brew getParent() {
        return parent;
    }

    public void setParent(Brew parent) {
        this.parent = parent;
    }

    public Brew getChild() {
        return child;
    }

    public void setChild(Brew child) {
        this.child = child;
    }
    
}
