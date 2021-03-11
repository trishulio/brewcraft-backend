package io.company.brewcraft.utils;

import javax.measure.Unit;
import javax.measure.quantity.AmountOfSubstance;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;

import tec.units.ri.function.RationalConverter;
import tec.units.ri.quantity.QuantityDimension;
import tec.units.ri.unit.BaseUnit;
import tec.units.ri.unit.TransformedUnit;
import tec.units.ri.unit.Units;

public class SupportedUnits {
    //Any new units must also be added to the QTY_UNIT table
    public static final Unit<AmountOfSubstance> EACH = new BaseUnit<AmountOfSubstance>("each", QuantityDimension.AMOUNT_OF_SUBSTANCE);
    public static final Unit<Volume> MILLILITRE = new TransformedUnit<Volume>("ml", Units.LITRE, Units.LITRE.getSystemUnit(), RationalConverter.of(1, 1000));
    public static final Unit<Volume> LITRE = Units.LITRE;
    public static final Unit<Volume> HECTOLITRE = new TransformedUnit<Volume>("hl", Units.LITRE, Units.LITRE.getSystemUnit(), RationalConverter.of(100, 1));
    public static final Unit<Mass> MILLIGRAM = new TransformedUnit<Mass>("mg", Units.KILOGRAM, Units.KILOGRAM.getSystemUnit(), RationalConverter.of(1, 1000000));
    public static final Unit<Mass> GRAM = new TransformedUnit<Mass>("g", Units.KILOGRAM, Units.KILOGRAM.getSystemUnit(), RationalConverter.of(1, 1000));
    public static final Unit<Mass> KILOGRAM = Units.KILOGRAM;
}
