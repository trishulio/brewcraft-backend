package io.company.brewcraft.util;

import javax.measure.Quantity;
import javax.measure.quantity.AmountOfSubstance;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;

public class QuantityCalculator {

    @SuppressWarnings("unchecked")
    public static Quantity<?> subtract(Quantity<?> q1, Quantity<?> q2) {
        if (SupportedUnits.DEFAULT_MASS.isCompatible(q1.getUnit())) {
            Quantity<Mass> mass1 = (Quantity<Mass>) q1;
            Quantity<Mass> mass2 = (Quantity<Mass>) q2;

            return mass1.subtract(mass2);
        } else if (SupportedUnits.DEFAULT_VOLUME.isCompatible(q1.getUnit())) {
            Quantity<Volume> volume1 = (Quantity<Volume>) q1;
            Quantity<Volume> volume2 = (Quantity<Volume>) q2;

            return volume1.subtract(volume2);
        } else if (SupportedUnits.EACH.isCompatible(q1.getUnit())) {
            Quantity<AmountOfSubstance> amount1 = (Quantity<AmountOfSubstance>) q1;
            Quantity<AmountOfSubstance> amount2 = (Quantity<AmountOfSubstance>) q2;

            return amount1.subtract(amount2);
        } else {
            throw new RuntimeException("Unsupported quantity type");
        }
    }
}
