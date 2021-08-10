package io.company.brewcraft.configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.BrewStageStatus;
import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.MaterialPortion;
import io.company.brewcraft.model.Measure;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.ShipmentStatus;
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
import io.company.brewcraft.repository.EquipmentRepository;
import io.company.brewcraft.repository.InvoiceItemRepository;
import io.company.brewcraft.repository.InvoiceStatusRepository;
import io.company.brewcraft.repository.MaterialLotRepository;
import io.company.brewcraft.repository.MaterialPortionRepository;
import io.company.brewcraft.repository.MaterialRepository;
import io.company.brewcraft.repository.MeasureRepository;
import io.company.brewcraft.repository.MixtureRepository;
import io.company.brewcraft.repository.ProductRepository;
import io.company.brewcraft.repository.PurchaseOrderRepository;
import io.company.brewcraft.repository.ShipmentStatusRepository;
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
import io.company.brewcraft.service.InvoiceItemAccessor;
import io.company.brewcraft.service.InvoiceStatusAccessor;
import io.company.brewcraft.service.MaterialAccessor;
import io.company.brewcraft.service.MaterialLotAccessor;
import io.company.brewcraft.service.MaterialPortionAccessor;
import io.company.brewcraft.service.MeasureAccessor;
import io.company.brewcraft.service.MixtureAccessor;
import io.company.brewcraft.service.ParentBrewAccessor;
import io.company.brewcraft.service.ParentMixtureAccessor;
import io.company.brewcraft.service.ProductAccessor;
import io.company.brewcraft.service.PurchaseOrderAccessor;
import io.company.brewcraft.service.ShipmentStatusAccessor;
import io.company.brewcraft.service.StorageAccessor;
import io.company.brewcraft.service.SupplierAccessor;

@Configuration
public class RepositoryConfiguration {

    @Bean
    @PersistenceContext
    public AggregationRepository aggrRepo(EntityManager em) {
        return new AggregationRepository(em);
    }

    @Bean
    public AccessorRefresher<Long, InvoiceItemAccessor, InvoiceItem> invoiceItemRefresher(InvoiceItemRepository repo) {
        return new AccessorRefresher<>(
            InvoiceItem.class,
            accessor -> accessor.getInvoiceItem(),
            (accessor, invoiceItem) -> accessor.setInvoiceItem(invoiceItem),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, InvoiceStatusAccessor, InvoiceStatus> invoiceStatusRefresher(InvoiceStatusRepository repo) {
        return new AccessorRefresher<>(
            InvoiceStatus.class,
            accessor -> accessor.getStatus(),
            (accessor, status) -> accessor.setStatus(status),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MaterialLotAccessor, MaterialLot> materialLotRefresher(MaterialLotRepository repo) {
        return new AccessorRefresher<>(
            MaterialLot.class,
            accessor -> accessor.getMaterialLot(),
            (accessor, lot) -> accessor.setMaterialLot(lot),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MaterialAccessor, Material> materialRefresher(MaterialRepository repo) {
        return new AccessorRefresher<>(
            Material.class,
            accessor -> accessor.getMaterial(),
            (accessor, material) -> accessor.setMaterial(material),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, PurchaseOrderAccessor, PurchaseOrder> purchaseOrderRefresher(PurchaseOrderRepository repo) {
        return new AccessorRefresher<>(
            PurchaseOrder.class,
            accessor -> accessor.getPurchaseOrder(),
            (accessor, purchaseOrder) -> accessor.setPurchaseOrder(purchaseOrder),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, ShipmentStatusAccessor, ShipmentStatus> shipmentStatusRefresher(ShipmentStatusRepository repo) {
        return new AccessorRefresher<>(
            ShipmentStatus.class,
            accessor -> accessor.getStatus(),
            (accessor, status) -> accessor.setStatus(status),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, StorageAccessor, Storage> storageRefresher(StorageRepository repo) {
        return new AccessorRefresher<>(
            Storage.class,
            accessor -> accessor.getStorage(),
            (accessor, status) -> accessor.setStorage(status),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, UserAccessor, User> userRefresher(UserRepository repo) {
        return new AccessorRefresher<>(
            User.class,
            accessor -> accessor.getUser(),
            (accessor, user) -> accessor.setUser(user),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, UserRoleAccessor, UserRole> userRoleRefresher(UserRoleRepository repo) {
        return new AccessorRefresher<>(
            UserRole.class,
            accessor -> accessor.getRole(),
            (accessor, role) -> accessor.setRole(role),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, UserSalutationAccessor, UserSalutation> userSalutationRefresher(UserSalutationRepository repo) {
        return new AccessorRefresher<>(
            UserSalutation.class,
            accessor -> accessor.getSalutation(),
            (accessor, salutation) -> accessor.setSalutation(salutation),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, UserStatusAccessor, UserStatus> userStatusRefresher(UserStatusRepository repo) {
        return new AccessorRefresher<>(
            UserStatus.class,
            accessor -> accessor.getStatus(),
            (accessor, status) -> accessor.setStatus(status),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, SupplierAccessor, Supplier> supplierRefresher(SupplierRepository repo) {
        return new AccessorRefresher<>(
            Supplier.class,
            accessor -> accessor.getSupplier(),
            (accessor, supplier) -> accessor.setSupplier(supplier),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, ProductAccessor, Product> productRefresher(ProductRepository repo) {
        return new AccessorRefresher<>(
            Product.class,
            accessor -> accessor.getProduct(),
            (accessor, product) -> accessor.setProduct(product),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, BrewAccessor, Brew> brewRefresher(BrewRepository repo) {
        return new AccessorRefresher<>(
            Brew.class,
            accessor -> accessor.getBrew(),
            (accessor, brew) -> accessor.setBrew(brew),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, ParentBrewAccessor, Brew> parentBrewRefresher(BrewRepository repo) {
        return new AccessorRefresher<>(
            Brew.class,
            accessor -> accessor.getParentBrew(),
            (accessor, parentBrew) -> accessor.setParentBrew(parentBrew),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, BrewStageAccessor, BrewStage> brewStageRefresher(BrewStageRepository repo) {
        return new AccessorRefresher<>(
            BrewStage.class,
            accessor -> accessor.getBrewStage(),
            (accessor, brewStage) -> accessor.setBrewStage(brewStage),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, BrewTaskAccessor, BrewTask> brewTaskRefresher(BrewTaskRepository repo) {
        return new AccessorRefresher<>(
            BrewTask.class,
            accessor -> accessor.getTask(),
            (accessor, brewTask) -> accessor.setTask(brewTask),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, BrewStageStatusAccessor, BrewStageStatus> brewStageStatusRefresher(BrewStageStatusRepository repo) {
        return new AccessorRefresher<>(
            BrewStageStatus.class,
            accessor -> accessor.getStatus(),
            (accessor, brewStageStatus) -> accessor.setStatus(brewStageStatus),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, ParentMixtureAccessor, Mixture> parentMixtureRefresher(MixtureRepository repo) {
        return new AccessorRefresher<>(
            Mixture.class,
            accessor -> accessor.getParentMixture(),
            (accessor, parentMixture) -> accessor.setParentMixture(parentMixture),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MixtureAccessor, Mixture> mixtureRefresher(MixtureRepository repo) {
        return new AccessorRefresher<>(
            Mixture.class,
            accessor -> accessor.getMixture(),
            (accessor, mixture) -> accessor.setMixture(mixture),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, EquipmentAccessor, Equipment> equipmentRefresher(EquipmentRepository repo) {
        return new AccessorRefresher<>(
            Equipment.class,
            accessor -> accessor.getEquipment(),
            (accessor, equipment) -> accessor.setEquipment(equipment),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MaterialPortionAccessor, MaterialPortion> materialPortionRefresher(MaterialPortionRepository repo) {
        return new AccessorRefresher<>(
            MaterialPortion.class,
            accessor -> accessor.getMaterialPortion(),
            (accessor, materialPortion) -> accessor.setMaterialPortion(materialPortion),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MeasureAccessor, Measure> measureRefresher(MeasureRepository repo) {
        return new AccessorRefresher<>(
            Measure.class,
            accessor -> accessor.getMeasure(),
            (accessor, measure) -> accessor.setMeasure(measure),
            ids -> repo.findAllById(ids)
        );
    }
}
