package io.company.brewcraft.util;

import io.company.brewcraft.util.validator.Validator;

public class ThreadLocalUtilityProvider implements UtilityProvider {
    
    private InheritableThreadLocal<Validator> validatorCache;
    
    public ThreadLocalUtilityProvider() {
        this.validatorCache = new InheritableThreadLocal<Validator>();
    }

    @Override
    public Validator getValidator() {
        return this.validatorCache.get();
    }

    @Override
    public void setValidator(Validator validator) {
        this.validatorCache.set(validator);
    }
}
