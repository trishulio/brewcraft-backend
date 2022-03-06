package io.company.brewcraft.configuration;

import java.util.ArrayList;
import java.util.UUID;

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
import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.FinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.FinishedGoodLotMixturePortion;
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
import io.company.brewcraft.model.SkuMaterial;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.model.TenantAccessor;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementAccessor;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserAccessor;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleAccessor;
import io.company.brewcraft.model.user.UserRoleBinding;
import io.company.brewcraft.model.user.UserRoleBindingAccessor;
import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserSalutationAccessor;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.model.user.UserStatusAccessor;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.AggregationRepository;
import io.company.brewcraft.repository.BrewRefresher;
import io.company.brewcraft.repository.BrewRepository;
import io.company.brewcraft.repository.BrewStageRefresher;
import io.company.brewcraft.repository.BrewStageRepository;
import io.company.brewcraft.repository.BrewStageStatusRefresher;
import io.company.brewcraft.repository.BrewStageStatusRepository;
import io.company.brewcraft.repository.BrewTaskRefresher;
import io.company.brewcraft.repository.BrewTaskRepository;
import io.company.brewcraft.repository.CollectionAccessorRefresher;
import io.company.brewcraft.repository.EquipmentRefresher;
import io.company.brewcraft.repository.EquipmentRepository;
import io.company.brewcraft.repository.FinishedGoodLotFinishedGoodLotPortionRefresher;
import io.company.brewcraft.repository.FinishedGoodLotFinishedGoodLotPortionRepository;
import io.company.brewcraft.repository.FinishedGoodLotMaterialPortionRefresher;
import io.company.brewcraft.repository.FinishedGoodLotMaterialPortionRepository;
import io.company.brewcraft.repository.FinishedGoodLotMixturePortionRefresher;
import io.company.brewcraft.repository.FinishedGoodLotMixturePortionRepository;
import io.company.brewcraft.repository.FinishedGoodLotRefresher;
import io.company.brewcraft.repository.FinishedGoodLotRepository;
import io.company.brewcraft.repository.InvoiceItemRefresher;
import io.company.brewcraft.repository.InvoiceItemRepository;
import io.company.brewcraft.repository.InvoiceRefresher;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.repository.InvoiceStatusRefresher;
import io.company.brewcraft.repository.InvoiceStatusRepository;
import io.company.brewcraft.repository.MaterialLotRefresher;
import io.company.brewcraft.repository.MaterialLotRepository;
import io.company.brewcraft.repository.MaterialRefresher;
import io.company.brewcraft.repository.MaterialRepository;
import io.company.brewcraft.repository.MeasureRefresher;
import io.company.brewcraft.repository.MeasureRepository;
import io.company.brewcraft.repository.MixtureMaterialPortionRefresher;
import io.company.brewcraft.repository.MixtureMaterialPortionRepository;
import io.company.brewcraft.repository.MixtureRecordingRefresher;
import io.company.brewcraft.repository.MixtureRecordingRepository;
import io.company.brewcraft.repository.MixtureRefresher;
import io.company.brewcraft.repository.MixtureRepository;
import io.company.brewcraft.repository.ProcurementRefresher;
import io.company.brewcraft.repository.ProcurementRepository;
import io.company.brewcraft.repository.ProductRefresher;
import io.company.brewcraft.repository.ProductRepository;
import io.company.brewcraft.repository.PurchaseOrderRefresher;
import io.company.brewcraft.repository.PurchaseOrderRepository;
import io.company.brewcraft.repository.QueryResolver;
import io.company.brewcraft.repository.Refresher;
import io.company.brewcraft.repository.ShipmentRefresher;
import io.company.brewcraft.repository.ShipmentRepository;
import io.company.brewcraft.repository.ShipmentStatusRefresher;
import io.company.brewcraft.repository.ShipmentStatusRepository;
import io.company.brewcraft.repository.SkuMaterialRefresher;
import io.company.brewcraft.repository.SkuRefresher;
import io.company.brewcraft.repository.SkuRepository;
import io.company.brewcraft.repository.StorageRefresher;
import io.company.brewcraft.repository.StorageRepository;
import io.company.brewcraft.repository.SupplierRefresher;
import io.company.brewcraft.repository.SupplierRepository;
import io.company.brewcraft.repository.TenantRefresher;
import io.company.brewcraft.repository.TenantRepository;
import io.company.brewcraft.repository.user.UserRepository;
import io.company.brewcraft.repository.user.UserRoleRepository;
import io.company.brewcraft.repository.user.UserSalutationRepository;
import io.company.brewcraft.repository.user.UserStatusRepository;
import io.company.brewcraft.repository.user.impl.UserRefresher;
import io.company.brewcraft.repository.user.impl.UserRoleBindingRefresher;
import io.company.brewcraft.repository.user.impl.UserRoleRefresher;
import io.company.brewcraft.repository.user.impl.UserSalutationRefresher;
import io.company.brewcraft.repository.user.impl.UserStatusRefresher;
import io.company.brewcraft.service.BrewAccessor;
import io.company.brewcraft.service.BrewStageAccessor;
import io.company.brewcraft.service.BrewStageStatusAccessor;
import io.company.brewcraft.service.BrewTaskAccessor;
import io.company.brewcraft.service.ChildFinishedGoodsAccessor;
import io.company.brewcraft.service.EquipmentAccessor;
import io.company.brewcraft.service.FinishedGoodLotAccessor;
import io.company.brewcraft.service.FinishedGoodLotFinishedGoodLotPortionAccessor;
import io.company.brewcraft.service.FinishedGoodLotMaterialPortionAccessor;
import io.company.brewcraft.service.FinishedGoodLotMixturePortionAccessor;
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
import io.company.brewcraft.service.SkuMaterialAccessor;
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
    public AccessorRefresher<Long, InvoiceAccessor, Invoice> invoiceAccessorRefresher(InvoiceRepository repo) {
        return new AccessorRefresher<>(
            Invoice.class,
            accessor -> accessor.getInvoice(),
            (accessor, invoice) -> accessor.setInvoice(invoice),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, InvoiceItemAccessor, InvoiceItem> invoiceItemAccessorRefresher(InvoiceItemRepository repo) {
        return new AccessorRefresher<>(
            InvoiceItem.class,
            accessor -> accessor.getInvoiceItem(),
            (accessor, invoiceItem) -> accessor.setInvoiceItem(invoiceItem),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, InvoiceStatusAccessor, InvoiceStatus> invoiceStatusAccessorRefresher(InvoiceStatusRepository repo) {
        return new AccessorRefresher<>(
            InvoiceStatus.class,
            accessor -> accessor.getInvoiceStatus(),
            (accessor, status) -> accessor.setInvoiceStatus(status),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, ShipmentAccessor, Shipment> shipmentAccessorRefresher(ShipmentRepository repo) {
        return new AccessorRefresher<>(
            Shipment.class,
            accessor -> accessor.getShipment(),
            (accessor, shipment) -> accessor.setShipment(shipment),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MaterialLotAccessor, MaterialLot> materialLotAccessorRefresher(MaterialLotRepository repo) {
        return new AccessorRefresher<>(
            MaterialLot.class,
            accessor -> accessor.getMaterialLot(),
            (accessor, lot) -> accessor.setMaterialLot(lot),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<ProcurementId, ProcurementAccessor, Procurement> procurementAccessorRefresher(ProcurementRepository repo) {
        return new AccessorRefresher<>(
            Procurement.class,
            accessor -> accessor.getProcurement(),
            (accessor, procurement) -> accessor.setProcurement(procurement),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MaterialAccessor, Material> materialAccessorRefresher(MaterialRepository repo) {
        return new AccessorRefresher<>(
            Material.class,
            accessor -> accessor.getMaterial(),
            (accessor, material) -> accessor.setMaterial(material),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, PurchaseOrderAccessor, PurchaseOrder> purchaseOrderAccessorRefresher(PurchaseOrderRepository repo) {
        return new AccessorRefresher<>(
            PurchaseOrder.class,
            accessor -> accessor.getPurchaseOrder(),
            (accessor, purchaseOrder) -> accessor.setPurchaseOrder(purchaseOrder),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, ShipmentStatusAccessor, ShipmentStatus> shipmentStatusAccessorRefresher(ShipmentStatusRepository repo) {
        return new AccessorRefresher<>(
            ShipmentStatus.class,
            accessor -> accessor.getShipmentStatus(),
            (accessor, status) -> accessor.setShipmentStatus(status),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, StorageAccessor, Storage> storageAccessorRefresher(StorageRepository repo) {
        return new AccessorRefresher<>(
            Storage.class,
            accessor -> accessor.getStorage(),
            (accessor, status) -> accessor.setStorage(status),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, UserAccessor, User> userAccessorRefresher(UserRepository repo) {
        return new AccessorRefresher<>(
            User.class,
            accessor -> accessor.getUser(),
            (accessor, user) -> accessor.setUser(user),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, UserRoleAccessor, UserRole> userRoleAccessorRefresher(UserRoleRepository repo) {
        return new AccessorRefresher<>(
            UserRole.class,
            accessor -> accessor.getRole(),
            (accessor, role) -> accessor.setRole(role),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, UserSalutationAccessor, UserSalutation> userSalutationAccessorRefresher(UserSalutationRepository repo) {
        return new AccessorRefresher<>(
            UserSalutation.class,
            accessor -> accessor.getSalutation(),
            (accessor, salutation) -> accessor.setSalutation(salutation),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, UserStatusAccessor, UserStatus> userStatusAccessorRefresher(UserStatusRepository repo) {
        return new AccessorRefresher<>(
            UserStatus.class,
            accessor -> accessor.getStatus(),
            (accessor, status) -> accessor.setStatus(status),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, SupplierAccessor, Supplier> supplierAccessorRefresher(SupplierRepository repo) {
        return new AccessorRefresher<>(
            Supplier.class,
            accessor -> accessor.getSupplier(),
            (accessor, supplier) -> accessor.setSupplier(supplier),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, ProductAccessor, Product> productAccessorRefresher(ProductRepository repo) {
        return new AccessorRefresher<>(
            Product.class,
            accessor -> accessor.getProduct(),
            (accessor, product) -> accessor.setProduct(product),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, BrewAccessor, Brew> brewAccessorRefresher(BrewRepository repo) {
        return new AccessorRefresher<>(
            Brew.class,
            accessor -> accessor.getBrew(),
            (accessor, brew) -> accessor.setBrew(brew),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, ParentBrewAccessor, Brew> parentBrewAccessorRefresher(BrewRepository repo) {
        return new AccessorRefresher<>(
            Brew.class,
            accessor -> accessor.getParentBrew(),
            (accessor, parentBrew) -> accessor.setParentBrew(parentBrew),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, BrewStageAccessor, BrewStage> brewStageAccessorRefresher(BrewStageRepository repo) {
        return new AccessorRefresher<>(
            BrewStage.class,
            accessor -> accessor.getBrewStage(),
            (accessor, brewStage) -> accessor.setBrewStage(brewStage),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, BrewTaskAccessor, BrewTask> brewTaskAccessorRefresher(BrewTaskRepository repo) {
        return new AccessorRefresher<>(
            BrewTask.class,
            accessor -> accessor.getTask(),
            (accessor, brewTask) -> accessor.setTask(brewTask),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, BrewStageStatusAccessor, BrewStageStatus> brewStageStatusAccessorRefresher(BrewStageStatusRepository repo) {
        return new AccessorRefresher<>(
            BrewStageStatus.class,
            accessor -> accessor.getStatus(),
            (accessor, brewStageStatus) -> accessor.setStatus(brewStageStatus),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MixtureAccessor, Mixture> mixtureAccessorRefresher(MixtureRepository repo) {
        return new AccessorRefresher<>(
            Mixture.class,
            accessor -> accessor.getMixture(),
            (accessor, mixture) -> accessor.setMixture(mixture),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public CollectionAccessorRefresher<Long, ParentMixturesAccessor, Mixture> parentMixturesAccessorRefresher(MixtureRepository repo) {
        return new CollectionAccessorRefresher<>(
            Mixture.class,
            accessor -> accessor.getParentMixtures(),
            (accessor, parentMixtures) -> accessor.setParentMixtures(new ArrayList<Mixture>(parentMixtures)),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, EquipmentAccessor, Equipment> equipmentAccessorRefresher(EquipmentRepository repo) {
        return new AccessorRefresher<>(
            Equipment.class,
            accessor -> accessor.getEquipment(),
            (accessor, equipment) -> accessor.setEquipment(equipment),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MixtureMaterialPortionAccessor, MixtureMaterialPortion> materialPortionAccessorRefresher(MixtureMaterialPortionRepository repo) {
        return new AccessorRefresher<>(
            MixtureMaterialPortion.class,
            accessor -> accessor.getMaterialPortion(),
            (accessor, materialPortion) -> accessor.setMaterialPortion(materialPortion),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MeasureAccessor, Measure> measureAccessorRefresher(MeasureRepository repo) {
        return new AccessorRefresher<>(
            Measure.class,
            accessor -> accessor.getMeasure(),
            (accessor, measure) -> accessor.setMeasure(measure),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, SkuAccessor, Sku> skuAccessorRefresher(SkuRepository repo) {
        return new AccessorRefresher<>(
            Sku.class,
            accessor -> accessor.getSku(),
            (accessor, sku) -> accessor.setSku(sku),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, FinishedGoodLotAccessor, FinishedGoodLot> finishedGoodAccessorRefresher(FinishedGoodLotRepository repo) {
        return new AccessorRefresher<>(
            FinishedGoodLot.class,
            accessor -> accessor.getFinishedGoodLot(),
            (accessor, finishedGood) -> accessor.setFinishedGoodLot(finishedGood),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, FinishedGoodLotMaterialPortionAccessor, FinishedGoodLotMaterialPortion> finishedGoodMaterialPortionAccessorRefresher(FinishedGoodLotMaterialPortionRepository repo) {
        return new AccessorRefresher<>(
            FinishedGoodLotMaterialPortion.class,
            accessor -> accessor.getMaterialPortion(),
            (accessor, finishedGoodMaterialPortion) -> accessor.setMaterialPortion(finishedGoodMaterialPortion),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, FinishedGoodLotMixturePortionAccessor, FinishedGoodLotMixturePortion> finishedGoodMixturePortionAccessorRefresher(FinishedGoodLotMixturePortionRepository repo) {
        return new AccessorRefresher<>(
            FinishedGoodLotMixturePortion.class,
            accessor -> accessor.getMixturePortion(),
            (accessor, finishedGoodMixturePortion) -> accessor.setMixturePortion(finishedGoodMixturePortion),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, FinishedGoodLotFinishedGoodLotPortionAccessor, FinishedGoodLotFinishedGoodLotPortion> finishedGoodFinishedGoodLotPortionAccessorRefresher(FinishedGoodLotFinishedGoodLotPortionRepository repo) {
        return new AccessorRefresher<>(
            FinishedGoodLotFinishedGoodLotPortion.class,
            accessor -> accessor.getFinishedGoodLotPortion(),
            (accessor, finishedGoodMixturePortion) -> accessor.setFinishedGoodLotPortion(finishedGoodMixturePortion),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MixtureRecordingAccessor, MixtureRecording> mixtureRecordingAccessorRefresher(MixtureRecordingRepository repo) {
        return new AccessorRefresher<>(
            MixtureRecording.class,
            accessor -> accessor.getMixtureRecording(),
            (accessor, mixtureRecording) -> accessor.setMixtureRecording(mixtureRecording),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<UUID, TenantAccessor, Tenant> tAccessorRefresher(TenantRepository repo) {
        return new AccessorRefresher<>(
            Tenant.class,
            accessor -> accessor.getTenant(),
            (accessor, tenant) -> accessor.setTenant(tenant),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public Refresher<Brew, BrewAccessor> brewRefresher(Refresher<Product, ProductAccessor> productRefresher, AccessorRefresher<Long, ParentBrewAccessor, Brew> parentBrewAccessorRefresher, AccessorRefresher<Long, BrewAccessor, Brew> brewAccessorRefresher) {
        return new BrewRefresher(productRefresher, parentBrewAccessorRefresher, brewAccessorRefresher);
    }

    @Bean
    public Refresher<BrewStage, BrewStageAccessor> brewStageRefresher(Refresher<BrewTask, BrewTaskAccessor> brewTaskRefresher, Refresher<BrewStageStatus, BrewStageStatusAccessor> brewStageStatusRefresher, Refresher<Brew, BrewAccessor> brewRefresher, AccessorRefresher<Long, BrewStageAccessor, BrewStage> brewStageAccessorRefresher) {
        return new BrewStageRefresher(brewTaskRefresher, brewStageStatusRefresher, brewRefresher, brewStageAccessorRefresher);
    }

    @Bean
    public Refresher<BrewStageStatus, BrewStageStatusAccessor> brewStageStatusRefresher(AccessorRefresher<Long, BrewStageStatusAccessor, BrewStageStatus> brewStageStatusAccessorRefresher) {
        return new BrewStageStatusRefresher(brewStageStatusAccessorRefresher);
    }

    @Bean
    public Refresher<BrewTask, BrewTaskAccessor> brewTaskRefresher(AccessorRefresher<Long, BrewTaskAccessor, BrewTask> brewTaskAccessorRefresher) {
        return new BrewTaskRefresher(brewTaskAccessorRefresher);
    }

    @Bean
    public Refresher<Equipment, EquipmentAccessor> equipmentRefresher(AccessorRefresher<Long, EquipmentAccessor, Equipment> equipmentAccessorRefresher) {
        return new EquipmentRefresher(equipmentAccessorRefresher);
    }

    @Bean
    public Refresher<FinishedGoodLotMaterialPortion, FinishedGoodLotMaterialPortionAccessor> finishedGoodLotMaterialPortionRefresher(Refresher<MaterialLot, MaterialLotAccessor> materialLotRefresher, AccessorRefresher<Long, FinishedGoodLotMaterialPortionAccessor, FinishedGoodLotMaterialPortion> materialPortionAccessorRefresher) {
        return new FinishedGoodLotMaterialPortionRefresher(materialLotRefresher, materialPortionAccessorRefresher);
    }

    @Bean
    public Refresher<FinishedGoodLotMixturePortion, FinishedGoodLotMixturePortionAccessor> finishedGoodLotMixturePortionRefresher(Refresher<Mixture, MixtureAccessor> mixtureRefresher, AccessorRefresher<Long, FinishedGoodLotMixturePortionAccessor, FinishedGoodLotMixturePortion> mixturePortionAccessorRefresher) {
        return new FinishedGoodLotMixturePortionRefresher(mixtureRefresher, mixturePortionAccessorRefresher);
    }

    @Bean
    public Refresher<FinishedGoodLotFinishedGoodLotPortion, FinishedGoodLotFinishedGoodLotPortionAccessor> finishedGoodLotFinishedGoodLotPortionRefresher(@Lazy Refresher<FinishedGoodLot, FinishedGoodLotAccessor> finishedGoodLotRefresher, AccessorRefresher<Long, FinishedGoodLotFinishedGoodLotPortionAccessor, FinishedGoodLotFinishedGoodLotPortion> finishedGoodLotPortionAccessorRefresher) {
        return new FinishedGoodLotFinishedGoodLotPortionRefresher(finishedGoodLotRefresher, finishedGoodLotPortionAccessorRefresher);
    }

    @Bean
    public Refresher<FinishedGoodLot, FinishedGoodLotAccessor> finishedGoodLotRefresher(AccessorRefresher<Long, FinishedGoodLotAccessor, FinishedGoodLot> finishedGoodAccessorRefresher, Refresher<Sku, SkuAccessor> skuRefresher, Refresher<FinishedGoodLotMixturePortion, FinishedGoodLotMixturePortionAccessor> fgMixturePortionRefresher, Refresher<FinishedGoodLotMaterialPortion, FinishedGoodLotMaterialPortionAccessor> fgMaterialPortionRefresher, Refresher<FinishedGoodLotFinishedGoodLotPortion, FinishedGoodLotFinishedGoodLotPortionAccessor> fgLotFgPortionRefresher) {
        return new FinishedGoodLotRefresher(finishedGoodAccessorRefresher, skuRefresher, fgMixturePortionRefresher, fgMaterialPortionRefresher, fgLotFgPortionRefresher);
    }

    @Bean
    public Refresher<InvoiceItem, InvoiceItemAccessor> invoiceItemRefresher(Refresher<Material, MaterialAccessor> materialRefresher, AccessorRefresher<Long, InvoiceItemAccessor, InvoiceItem> invoiceItemAccessorRefresher) {
        return new InvoiceItemRefresher(materialRefresher, invoiceItemAccessorRefresher);
    }

    @Bean
    public Refresher<Invoice, InvoiceAccessor> invoiceRefresher(AccessorRefresher<Long, InvoiceAccessor, Invoice> invoiceAccessorRefresher, Refresher<InvoiceItem, InvoiceItemAccessor> invoiceItemRefresher, Refresher<InvoiceStatus, InvoiceStatusAccessor> invoiceStatusRefresher, Refresher<PurchaseOrder, PurchaseOrderAccessor> poRefresher) {
        return new InvoiceRefresher(invoiceAccessorRefresher, invoiceItemRefresher, invoiceStatusRefresher, poRefresher);
    }

    @Bean
    public Refresher<InvoiceStatus, InvoiceStatusAccessor> invoiceStatusRefresher(AccessorRefresher<Long, InvoiceStatusAccessor, InvoiceStatus> invoiceStatusAccessorRefresher) {
        return new InvoiceStatusRefresher(invoiceStatusAccessorRefresher);
    }

    @Bean
    public Refresher<Procurement, ProcurementAccessor> procurementRefresher(AccessorRefresher<ProcurementId, ProcurementAccessor, Procurement>  accessorRefresher, InvoiceRefresher invoiceRefresher, ShipmentRefresher shipmentRefresher) {
        return new ProcurementRefresher(accessorRefresher, invoiceRefresher, shipmentRefresher);
    }

    @Bean
    public Refresher<MaterialLot, MaterialLotAccessor> materialLotRefresher(Refresher<InvoiceItem, InvoiceItemAccessor> itemRefresher, Refresher<Storage, StorageAccessor> storageRefresher, AccessorRefresher<Long, MaterialLotAccessor, MaterialLot> materialLotAccessorRefresher) {
        return new MaterialLotRefresher(itemRefresher, storageRefresher, materialLotAccessorRefresher);
    }

    @Bean
    public Refresher<Material, MaterialAccessor> materialRefresher(AccessorRefresher<Long, MaterialAccessor, Material> materialAccessorRefresher) {
        return new MaterialRefresher(materialAccessorRefresher);
    }

    @Bean
    public Refresher<Measure, MeasureAccessor> measureRefresher(AccessorRefresher<Long, MeasureAccessor, Measure> measureAccessorRefresher) {
        return new MeasureRefresher(measureAccessorRefresher);
    }

    @Bean
    public Refresher<MixtureMaterialPortion, MixtureMaterialPortionAccessor> mixtureMaterialPortionRefresher(Refresher<Mixture, MixtureAccessor> mixtureRefresher, Refresher<MaterialLot, MaterialLotAccessor> materialLotRefresher, AccessorRefresher<Long, MixtureMaterialPortionAccessor, MixtureMaterialPortion> materialPortionAccessorRefresher) {
        return new MixtureMaterialPortionRefresher(mixtureRefresher, materialLotRefresher, materialPortionAccessorRefresher);
    }

    @Bean
    public Refresher<MixtureRecording, MixtureRecordingAccessor> mixtureRecordingRefresher(Refresher<Measure, MeasureAccessor> measureRefresher, Refresher<Mixture, MixtureAccessor> mixtureRefresher, AccessorRefresher<Long, MixtureRecordingAccessor, MixtureRecording> mixtureRecordingAccessorRefresher) {
        return new MixtureRecordingRefresher(measureRefresher, mixtureRefresher, mixtureRecordingAccessorRefresher);
    }

    @Bean
    public Refresher<Mixture, MixtureAccessor> mixtureRefresher(Refresher<Equipment, EquipmentAccessor> equipmentRefresher, Refresher<BrewStage, BrewStageAccessor> brewStageRefresher, AccessorRefresher<Long, MixtureAccessor, Mixture> mixtureAccessorRefresher, CollectionAccessorRefresher<Long, ParentMixturesAccessor, Mixture> parentMixtureAccessorRefresher) {
        return new MixtureRefresher(equipmentRefresher, brewStageRefresher, mixtureAccessorRefresher, parentMixtureAccessorRefresher);
    }

    @Bean
    public Refresher<Product, ProductAccessor> productRefresher(AccessorRefresher<Long, ProductAccessor, Product> productAccessorRefresher) {
        return new ProductRefresher(productAccessorRefresher);
    }

    @Bean
    public Refresher<PurchaseOrder, PurchaseOrderAccessor> purchaseOrderRefresher(AccessorRefresher<Long, PurchaseOrderAccessor, PurchaseOrder> purchaseOrderAccessorRefresher, Refresher<Supplier, SupplierAccessor> supplierRefresher) {
        return new PurchaseOrderRefresher(purchaseOrderAccessorRefresher, supplierRefresher);
    }

    @Bean
    public Refresher<Shipment, ShipmentAccessor> shipmentRefresher(AccessorRefresher<Long, ShipmentAccessor, Shipment> shipmentAccessorRefresher, Refresher<ShipmentStatus, ShipmentStatusAccessor> shipmentStatusRefresher, Refresher<MaterialLot, MaterialLotAccessor> materialLotRefresher) {
        return new ShipmentRefresher(shipmentAccessorRefresher, shipmentStatusRefresher, materialLotRefresher);
    }

    @Bean
    public Refresher<ShipmentStatus, ShipmentStatusAccessor> shipmentStatusRefresher(AccessorRefresher<Long, ShipmentStatusAccessor, ShipmentStatus> shipmentStatusAccessorRefresher) {
        return new ShipmentStatusRefresher(shipmentStatusAccessorRefresher);
    }

    @Bean
    public Refresher<SkuMaterial, SkuMaterialAccessor> skuMaterialRefresher(Refresher<Material, MaterialAccessor> materialRefresher) {
        return new SkuMaterialRefresher(materialRefresher);
    }

    @Bean
    public Refresher<Sku, SkuAccessor> skuRefresher(Refresher<Product, ProductAccessor> productRefresher, Refresher<SkuMaterial, SkuMaterialAccessor> skuMaterialRefresher, AccessorRefresher<Long, SkuAccessor, Sku> skuAccessorRefresher) {
        return new SkuRefresher(productRefresher, skuMaterialRefresher, skuAccessorRefresher);
    }

    @Bean
    public Refresher<Storage, StorageAccessor> storageRefresher(AccessorRefresher<Long, StorageAccessor, Storage> storageAccessorRefresher) {
        return new StorageRefresher(storageAccessorRefresher);
    }

    @Bean
    public Refresher<Supplier, SupplierAccessor> supplierRefresher(AccessorRefresher<Long, SupplierAccessor, Supplier> supplierAccessorRefresher) {
        return new SupplierRefresher(supplierAccessorRefresher);
    }

    @Bean
    public Refresher<User, UserAccessor> userRefresher(AccessorRefresher<Long, UserAccessor, User> userAccessorRefresher, Refresher<UserStatus, UserStatusAccessor> statusRefresher, Refresher<UserSalutation, UserSalutationAccessor> salutationRefresher, @Lazy Refresher<UserRoleBinding, UserRoleBindingAccessor> roleBindingRefresher) {
        return new UserRefresher(userAccessorRefresher, statusRefresher, salutationRefresher, roleBindingRefresher);
    }

    @Bean
    public Refresher<UserRoleBinding, UserRoleBindingAccessor> userRoleBindingRefresher(Refresher<UserRole, UserRoleAccessor> userRoleRefresher) {
        return new UserRoleBindingRefresher(userRoleRefresher);
    }

    @Bean
    public Refresher<UserRole, UserRoleAccessor> userRoleRefresher(AccessorRefresher<Long, UserRoleAccessor, UserRole> userRoleAccessorRefresher) {
        return new UserRoleRefresher(userRoleAccessorRefresher);
    }

    @Bean
    public Refresher<UserSalutation, UserSalutationAccessor> userSalutationRefresher(AccessorRefresher<Long, UserSalutationAccessor, UserSalutation> userSalutationAccessorRefresher) {
        return new UserSalutationRefresher(userSalutationAccessorRefresher);
    }

    @Bean
    public Refresher<UserStatus, UserStatusAccessor> userStatusRefresher(AccessorRefresher<Long, UserStatusAccessor, UserStatus> userStatusAccessorRefresher) {
        return new UserStatusRefresher(userStatusAccessorRefresher);
    }

    @Bean
    public Refresher<Tenant, TenantAccessor> tenantRefresher(AccessorRefresher<UUID, TenantAccessor, Tenant> tenantAccessRefresher) {
        return new TenantRefresher(tenantAccessRefresher);
    }
}
