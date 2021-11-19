package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import javax.measure.Quantity;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class MixtureTest {

    private Mixture mixture;

    @BeforeEach
    public void init() {
        mixture = new Mixture();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        List<Mixture> parentMixtures = List.of(new Mixture(9L));
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE);
        Equipment equipment = new Equipment(3L);
        BrewStage brewStage = new BrewStage(4L);
        List<MixtureMaterialPortion> materialPortions = List.of(new MixtureMaterialPortion(5L));
        List<MixtureRecording> recordedMeasures = List.of(new MixtureRecording(6L));
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Mixture mixture = new Mixture(id, parentMixtures, quantity, equipment, materialPortions, recordedMeasures, brewStage, created, lastUpdated, version);

        assertEquals(1L, mixture.getId());
        assertEquals(List.of(new Mixture(9L)), mixture.getParentMixtures());
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), mixture.getQuantity());
        assertEquals(new Equipment(3L), mixture.getEquipment());
        assertEquals(new BrewStage(4L), mixture.getBrewStage());

        MixtureMaterialPortion expectedMaterialPortion = new MixtureMaterialPortion(5L);
        expectedMaterialPortion.setMixture(mixture);
        assertEquals(List.of(expectedMaterialPortion), mixture.getMaterialPortions());

        MixtureRecording expectedMixtureRecording = new MixtureRecording(6L);
        expectedMixtureRecording.setMixture(mixture);
        assertEquals(List.of(expectedMixtureRecording), mixture.getRecordedMeasures());

        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), mixture.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), mixture.getLastUpdated());
        assertEquals(1, mixture.getVersion());
    }

    @Test
    public void testGetSetId() {
        mixture.setId(1L);
        assertEquals(1L, mixture.getId());
    }

    @Test
    public void testGetSetParentMixtures() {
        mixture.setParentMixtures(List.of(new Mixture(9L)));
        assertEquals(List.of(new Mixture(9L)), mixture.getParentMixtures());
    }

    @Test
    public void testGetSetEquipment() {
        mixture.setEquipment(new Equipment(3L));
        assertEquals(new Equipment(3L), mixture.getEquipment());
    }

    @Test
    public void testGetSetBrewStage() {
        mixture.setBrewStage(new BrewStage(4L));
        assertEquals(new BrewStage(4L), mixture.getBrewStage());
    }

    @Test
    public void testGetSetMaterialPortions() {
        mixture.setMaterialPortions(List.of(new MixtureMaterialPortion(5L)));

        MixtureMaterialPortion expectedMaterialPortion = new MixtureMaterialPortion(5L);
        expectedMaterialPortion.setMixture(mixture);

        assertEquals(List.of(expectedMaterialPortion), mixture.getMaterialPortions());
    }

    @Test
    public void testGetSetRecordedMeasures() {
        mixture.setRecordedMeasures(List.of(new MixtureRecording(6L)));

        MixtureRecording expectedMixtureRecording = new MixtureRecording(6L);
        expectedMixtureRecording.setMixture(mixture);

        assertEquals(List.of(expectedMixtureRecording), mixture.getRecordedMeasures());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        mixture.setCreatedAt(created);
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), mixture.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        mixture.setLastUpdated(lastUpdated);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), mixture.getLastUpdated());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        mixture.setVersion(version);
        assertEquals(version, mixture.getVersion());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        List<Mixture> parentMixtures = List.of(new Mixture(9L));
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE);
        Equipment equipment = new Equipment(3L);
        BrewStage brewStage = new BrewStage(4L);
        List<MixtureMaterialPortion> materialPortions = List.of(new MixtureMaterialPortion(5L));
        List<MixtureRecording> recordedMeasures = List.of(new MixtureRecording(6L));
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Mixture mixture = new Mixture(id, parentMixtures, quantity, equipment, materialPortions, recordedMeasures, brewStage, created, lastUpdated, version);

        final String json = "{\"id\":1,\"parentMixtures\":[{\"id\":9,\"parentMixtures\":null,\"quantity\":null,\"equipment\":null,\"brewStage\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null}],\"quantity\":{\"symbol\":\"hl\",\"value\":100},\"equipment\":{\"id\":3,\"facility\":null,\"name\":null,\"type\":null,\"status\":null,\"maxCapacityValue\":null,\"maxCapacityUnit\":null,\"maxCapacityDisplayUnit\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null,\"maxCapacityInDisplayUnit\":null,\"maxCapacity\":null},\"brewStage\":{\"id\":4,\"brew\":null,\"status\":null,\"task\":null,\"startedAt\":null,\"endedAt\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"createdAt\":\"2019-01-02T03:04:00\",\"lastUpdated\":\"2020-01-02T03:04:00\",\"version\":1}";
        JSONAssert.assertEquals(json, mixture.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
