package io.company.brewcraft.util;

import io.company.brewcraft.util.validator.Validator;

public interface UtilityProvider {
    Validator getValidator();

    void setValidator(Validator validator);
}
