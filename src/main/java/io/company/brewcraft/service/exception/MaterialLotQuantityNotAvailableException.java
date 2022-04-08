package io.company.brewcraft.service.exception;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Set;

public class MaterialLotQuantityNotAvailableException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MaterialLotQuantityNotAvailableException(Set<Long> materialLotIds) {
        super(MessageFormat.format("Quantity not available for the following materialLotIds: {0}", Arrays.toString(materialLotIds.toArray())));
    }
}