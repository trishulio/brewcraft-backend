package io.company.brewcraft.configuration;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.BrewStageStatus;
import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.FinishedGood;
import io.company.brewcraft.model.FinishedGoodMaterialPortion;
import io.company.brewcraft.model.FinishedGoodMixturePortion;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceAccessor;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Measure;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureMaterialPortion;
import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentAccessor;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.model.SkuAccessor;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserAccessor;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleAccessor;
import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserSalutationAccessor;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.model.user.UserStatusAccessor;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.AggregationRepository;
import io.company.brewcraft.repository.BrewRepository;
import io.company.brewcraft.repository.BrewStageRepository;
import io.company.brewcraft.repository.BrewStageStatusRepository;
import io.company.brewcraft.repository.BrewTaskRepository;
import io.company.brewcraft.repository.CollectionAccessorRefresher;
import io.company.brewcraft.repository.EquipmentRepository;
import io.company.brewcraft.repository.FinishedGoodMaterialPortionRepository;
import io.company.brewcraft.repository.FinishedGoodMixturePortionRepository;
import io.company.brewcraft.repository.FinishedGoodRepository;
import io.company.brewcraft.repository.InvoiceItemRepository;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.repository.InvoiceStatusRepository;
import io.company.brewcraft.repository.MaterialLotRepository;
import io.company.brewcraft.repository.MaterialRepository;
import io.company.brewcraft.repository.MeasureRepository;
import io.company.brewcraft.repository.MixtureMaterialPortionRepository;
import io.company.brewcraft.repository.MixtureRecordingRepository;
import io.company.brewcraft.repository.MixtureRepository;
import io.company.brewcraft.repository.ProductRepository;
import io.company.brewcraft.repository.PurchaseOrderRepository;
import io.company.brewcraft.repository.QueryResolver;
import io.company.brewcraft.repository.ShipmentRepository;
import io.company.brewcraft.repository.ShipmentStatusRepository;
import io.company.brewcraft.repository.SkuRepository;
import io.company.brewcraft.repository.StorageRepository;
import io.company.brewcraft.repository.SupplierRepository;
import io.company.brewcraft.repository.user.UserRepository;
import io.company.brewcraft.repository.user.UserRoleRepository;
import io.company.brewcraft.repository.user.UserSalutationRepository;
import io.company.brewcraft.repository.user.UserStatusRepository;
import io.company.brewcraft.service.BrewAccessor;
import io.company.brewcraft.service.BrewStageAccessor;
import io.company.brewcraft.service.BrewStageStatusAccessor;
import io.company.brewcraft.service.BrewTaskAccessor;
import io.company.brewcraft.service.EquipmentAccessor;
import io.company.brewcraft.service.FinishedGoodAccessor;
import io.company.brewcraft.service.FinishedGoodMaterialPortionAccessor;
import io.company.brewcraft.service.FinishedGoodMixturePortionAccessor;
import io.company.brewcraft.service.InvoiceItemAccessor;
import io.company.brewcraft.service.InvoiceStatusAccessor;
import io.company.brewcraft.service.MaterialAccessor;
import io.company.brewcraft.service.MaterialLotAccessor;
import io.company.brewcraft.service.MeasureAccessor;
import io.company.brewcraft.service.MixtureAccessor;
import io.company.brewcraft.service.MixtureMaterialPortionAccessor;
import io.company.brewcraft.service.MixtureRecordingAccessor;
import io.company.brewcraft.service.ParentBrewAccessor;
import io.company.brewcraft.service.ParentMixturesAccessor;
import io.company.brewcraft.service.ProductAccessor;
import io.company.brewcraft.service.PurchaseOrderAccessor;
import io.company.brewcraft.service.ShipmentStatusAccessor;
import io.company.brewcraft.service.StorageAccessor;
import io.company.brewcraft.service.SupplierAccessor;

@Configuration
public class RepositoryConfiguration {

    @Bean
    @PersistenceContext
    public QueryResolver queryResolver(EntityManager em) {
        return new QueryResolver(em);
    }

    @Bean
    public AggregationRepository aggrRepo(QueryResolver queryResolver) {
        return new AggregationRepository(queryResolver);
    }

    @Bean
    public AccessorRefresher<Long, InvoiceAccessor, Invoice> invoiceRefresher(@Lazy InvoiceRepository repo) {
        return new AccessorRefresher<>(
            Invoice.class,
            accessor -> accessor.getInvoice(),
            (accessor, invoice) -> accessor.setInvoice(invoice),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, InvoiceItemAccessor, InvoiceItem> invoiceItemRefresher(@Lazy InvoiceItemRepository repo) {
        return new AccessorRefresher<>(
            InvoiceItem.class,
            accessor -> accessor.getInvoiceItem(),
            (accessor, invoiceItem) -> accessor.setInvoiceItem(invoiceItem),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, InvoiceStatusAccessor, InvoiceStatus> invoiceStatusRefresher(@Lazy InvoiceStatusRepository repo) {
        return new AccessorRefresher<>(
            InvoiceStatus.class,
            accessor -> accessor.getInvoiceStatus(),
            (accessor, status) -> accessor.setInvoiceStatus(status),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, ShipmentAccessor, Shipment> shipmentRefresher(@Lazy ShipmentRepository repo) {
        return new AccessorRefresher<>(
            Shipment.class,
            accessor -> accessor.getShipment(),
            (accessor, shipment) -> accessor.setShipment(shipment),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MaterialLotAccessor, MaterialLot> materialLotRefresher(@Lazy MaterialLotRepository repo) {
        return new AccessorRefresher<>(
            MaterialLot.class,
            accessor -> accessor.getMaterialLot(),
            (accessor, lot) -> accessor.setMaterialLot(lot),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MaterialAccessor, Material> materialRefresher(@Lazy MaterialRepository repo) {
        return new AccessorRefresher<>(
            Material.class,
            accessor -> accessor.getMaterial(),
            (accessor, material) -> accessor.setMaterial(material),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, PurchaseOrderAccessor, PurchaseOrder> purchaseOrderRefresher(@Lazy PurchaseOrderRepository repo) {
        return new AccessorRefresher<>(
            PurchaseOrder.class,
            accessor -> accessor.getPurchaseOrder(),
            (accessor, purchaseOrder) -> accessor.setPurchaseOrder(purchaseOrder),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, ShipmentStatusAccessor, ShipmentStatus> shipmentStatusRefresher(@Lazy ShipmentStatusRepository repo) {
        return new AccessorRefresher<>(
            ShipmentStatus.class,
            accessor -> accessor.getShipmentStatus(),
            (accessor, status) -> accessor.setShipmentStatus(status),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, StorageAccessor, Storage> storageRefresher(@Lazy StorageRepository repo) {
        return new AccessorRefresher<>(
            Storage.class,
            accessor -> accessor.getStorage(),
            (accessor, status) -> accessor.setStorage(status),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, UserAccessor, User> userRefresher(@Lazy UserRepository repo) {
        return new AccessorRefresher<>(
            User.class,
            accessor -> accessor.getUser(),
            (accessor, user) -> accessor.setUser(user),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, UserRoleAccessor, UserRole> userRoleRefresher(@Lazy UserRoleRepository repo) {
        return new AccessorRefresher<>(
            UserRole.class,
            accessor -> accessor.getRole(),
            (accessor, role) -> accessor.setRole(role),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, UserSalutationAccessor, UserSalutation> userSalutationRefresher(@Lazy UserSalutationRepository repo) {
        return new AccessorRefresher<>(
            UserSalutation.class,
            accessor -> accessor.getSalutation(),
            (accessor, salutation) -> accessor.setSalutation(salutation),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, UserStatusAccessor, UserStatus> userStatusRefresher(@Lazy UserStatusRepository repo) {
        return new AccessorRefresher<>(
            UserStatus.class,
            accessor -> accessor.getStatus(),
            (accessor, status) -> accessor.setStatus(status),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, SupplierAccessor, Supplier> supplierRefresher(@Lazy SupplierRepository repo) {
        return new AccessorRefresher<>(
            Supplier.class,
            accessor -> accessor.getSupplier(),
            (accessor, supplier) -> accessor.setSupplier(supplier),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, ProductAccessor, Product> productRefresher(@Lazy ProductRepository repo) {
        return new AccessorRefresher<>(
            Product.class,
            accessor -> accessor.getProduct(),
            (accessor, product) -> accessor.setProduct(product),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, BrewAccessor, Brew> brewRefresher(@Lazy BrewRepository repo) {
        return new AccessorRefresher<>(
            Brew.class,
            accessor -> accessor.getBrew(),
            (accessor, brew) -> accessor.setBrew(brew),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, ParentBrewAccessor, Brew> parentBrewRefresher(@Lazy BrewRepository repo) {
        return new AccessorRefresher<>(
            Brew.class,
            accessor -> accessor.getParentBrew(),
            (accessor, parentBrew) -> accessor.setParentBrew(parentBrew),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, BrewStageAccessor, BrewStage> brewStageRefresher(@Lazy BrewStageRepository repo) {
        return new AccessorRefresher<>(
            BrewStage.class,
            accessor -> accessor.getBrewStage(),
            (accessor, brewStage) -> accessor.setBrewStage(brewStage),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, BrewTaskAccessor, BrewTask> brewTaskRefresher(@Lazy BrewTaskRepository repo) {
        return new AccessorRefresher<>(
            BrewTask.class,
            accessor -> accessor.getTask(),
            (accessor, brewTask) -> accessor.setTask(brewTask),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, BrewStageStatusAccessor, BrewStageStatus> brewStageStatusRefresher(@Lazy BrewStageStatusRepository repo) {
        return new AccessorRefresher<>(
            BrewStageStatus.class,
            accessor -> accessor.getStatus(),
            (accessor, brewStageStatus) -> accessor.setStatus(brewStageStatus),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MixtureAccessor, Mixture> mixtureRefresher(@Lazy MixtureRepository repo) {
        return new AccessorRefresher<>(
            Mixture.class,
            accessor -> accessor.getMixture(),
            (accessor, mixture) -> accessor.setMixture(mixture),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public CollectionAccessorRefresher<Long, ParentMixturesAccessor, Mixture> parentMixturesRefresher(@Lazy MixtureRepository repo) {
        return new CollectionAccessorRefresher<>(
            Mixture.class,
            accessor -> accessor.getParentMixtures(),
            (accessor, parentMixtures) -> accessor.setParentMixtures(new ArrayList<Mixture>(parentMixtures)),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, EquipmentAccessor, Equipment> equipmentRefresher(@Lazy EquipmentRepository repo) {
        return new AccessorRefresher<>(
            Equipment.class,
            accessor -> accessor.getEquipment(),
            (accessor, equipment) -> accessor.setEquipment(equipment),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MixtureMaterialPortionAccessor, MixtureMaterialPortion> materialPortionRefresher(@Lazy MixtureMaterialPortionRepository repo) {
        return new AccessorRefresher<>(
            MixtureMaterialPortion.class,
            accessor -> accessor.getMaterialPortion(),
            (accessor, materialPortion) -> accessor.setMaterialPortion(materialPortion),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MeasureAccessor, Measure> measureRefresher(@Lazy MeasureRepository repo) {
        return new AccessorRefresher<>(
            Measure.class,
            accessor -> accessor.getMeasure(),
            (accessor, measure) -> accessor.setMeasure(measure),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, SkuAccessor, Sku> skuRefresher(@Lazy SkuRepository repo) {
        return new AccessorRefresher<>(
            Sku.class,
            accessor -> accessor.getSku(),
            (accessor, sku) -> accessor.setSku(sku),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, FinishedGoodAccessor, FinishedGood> finishedGoodRefresher(@Lazy FinishedGoodRepository repo) {
        return new AccessorRefresher<>(
            FinishedGood.class,
            accessor -> accessor.getFinishedGood(),
            (accessor, finishedGood) -> accessor.setFinishedGood(finishedGood),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, FinishedGoodMaterialPortionAccessor, FinishedGoodMaterialPortion> finishedGoodMaterialPortionRefresher(@Lazy FinishedGoodMaterialPortionRepository repo) {
        return new AccessorRefresher<>(
            FinishedGoodMaterialPortion.class,
            accessor -> accessor.getMaterialPortion(),
            (accessor, finishedGoodMaterialPortion) -> accessor.setMaterialPortion(finishedGoodMaterialPortion),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, FinishedGoodMixturePortionAccessor, FinishedGoodMixturePortion> finishedGoodMixturePortionRefresher(@Lazy FinishedGoodMixturePortionRepository repo) {
        return new AccessorRefresher<>(
            FinishedGoodMixturePortion.class,
            accessor -> accessor.getMixturePortion(),
            (accessor, finishedGoodMixturePortion) -> accessor.setMixturePortion(finishedGoodMixturePortion),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MixtureRecordingAccessor, MixtureRecording> mixtureRecordingRefresher(@Lazy MixtureRecordingRepository repo) {
        return new AccessorRefresher<>(
                MixtureRecording.class,
            accessor -> accessor.getMixtureRecording(),
            (accessor, mixtureRecording) -> accessor.setMixtureRecording(mixtureRecording),
            ids -> repo.findAllById(ids)
        );
    }
}
