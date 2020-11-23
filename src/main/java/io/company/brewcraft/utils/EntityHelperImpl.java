package io.company.brewcraft.utils;

import java.lang.reflect.Field;

public class EntityHelperImpl implements EntityHelper {

    /*
     * Copy existingEntity field values for each null field of updatedEntity
     */
    public <T> void applyUpdate(T updatedEntity, T existingEntity) {
        for (Field field : updatedEntity.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(updatedEntity);

                if (value == null) {
                    field.set(updatedEntity, field.get(existingEntity));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    
}
