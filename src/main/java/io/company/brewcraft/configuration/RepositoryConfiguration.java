package io.company.brewcraft.configuration;

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
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.BrewRepository;
import io.company.brewcraft.repository.BrewStageRepository;
import io.company.brewcraft.repository.BrewStageStatusRepository;
import io.company.brewcraft.repository.BrewTaskRepository;
import io.company.brewcraft.repository.EquipmentRepository;
import io.company.brewcraft.repository.InvoiceItemRepository;
import io.company.brewcraft.repository.InvoiceStatusRepository;
import io.company.brewcraft.repository.MaterialLotRepository;
import io.company.brewcraft.repository.MaterialRepository;
import io.company.brewcraft.repository.MixtureRepository;
import io.company.brewcraft.repository.ProductRepository;
import io.company.brewcraft.repository.PurchaseOrderRepository;
import io.company.brewcraft.repository.ShipmentStatusRepository;
import io.company.brewcraft.repository.StorageRepository;
import io.company.brewcraft.repository.user.UserRepository;
import io.company.brewcraft.service.BrewStageAccessor;
import io.company.brewcraft.service.BrewStageStatusAccessor;
import io.company.brewcraft.service.BrewTaskAccessor;
import io.company.brewcraft.service.EquipmentAccessor;
import io.company.brewcraft.service.InvoiceItemAccessor;
import io.company.brewcraft.service.InvoiceStatusAccessor;
import io.company.brewcraft.service.MaterialAccessor;
import io.company.brewcraft.service.MaterialLotAccessor;
import io.company.brewcraft.service.ParentBrewAccessor;
import io.company.brewcraft.service.ParentMixtureAccessor;
import io.company.brewcraft.service.ProductAccessor;
import io.company.brewcraft.service.PurchaseOrderAccessor;
import io.company.brewcraft.service.ShipmentStatusAccessor;
import io.company.brewcraft.service.StorageAccessor;
import io.company.brewcraft.service.UserAccessor;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public AccessorRefresher<Long, InvoiceItemAccessor, InvoiceItem> invoiceItemRefresher(InvoiceItemRepository repo) {
        return new AccessorRefresher<>(
            accessor -> accessor.getInvoiceItem(),
            (accessor, invoiceItem) -> accessor.setInvoiceItem(invoiceItem),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, InvoiceStatusAccessor, InvoiceStatus> invoiceStatusRefresher(InvoiceStatusRepository repo) {
        return new AccessorRefresher<>(
            accessor -> accessor.getStatus(),
            (accessor, status) -> accessor.setStatus(status),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MaterialLotAccessor, MaterialLot> materialLotRefresher(MaterialLotRepository repo) {
        return new AccessorRefresher<>(
            accessor -> accessor.getMaterialLot(),
            (accessor, lot) -> accessor.setMaterialLot(lot),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, MaterialAccessor, Material> materialRefresher(MaterialRepository repo) {
        return new AccessorRefresher<>(
            accessor -> accessor.getMaterial(),
            (accessor, material) -> accessor.setMaterial(material),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, PurchaseOrderAccessor, PurchaseOrder> purchaseOrderRefresher(PurchaseOrderRepository repo) {
        return new AccessorRefresher<>(
            accessor -> accessor.getPurchaseOrder(),
            (accessor, purchaseOrder) -> accessor.setPurchaseOrder(purchaseOrder),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, ShipmentStatusAccessor, ShipmentStatus> shipmentStatusRefresher(ShipmentStatusRepository repo) {
        return new AccessorRefresher<>(
            accessor -> accessor.getStatus(),
            (accessor, status) -> accessor.setStatus(status),
            ids -> repo.findAllById(ids)
        );
    }

    @Bean
    public AccessorRefresher<Long, StorageAccessor, Storage> storageRefresher(StorageRepository repo) {
        return new AccessorRefresher<>(
            accessor -> accessor.getStorage(),
            (accessor, status) -> accessor.setStorage(status),
            ids -> repo.findAllById(ids)
        );
    }
    
    @Bean
    public AccessorRefresher<Long, ProductAccessor, Product> productRefresher(ProductRepository repo) {
        return new AccessorRefresher<>(
            accessor -> accessor.getProduct(),
            (accessor, product) -> accessor.setProduct(product),
            ids -> repo.findAllById(ids)
        );
    }
    
    @Bean
    public AccessorRefresher<Long, ParentBrewAccessor, Brew> brewRefresher(BrewRepository repo) {
        return new AccessorRefresher<>(
            accessor -> accessor.getParentBrew(),
            (accessor, parentBrew) -> accessor.setParentBrew(parentBrew),
            ids -> repo.findAllById(ids)
        );
    }
    
    @Bean
    public AccessorRefresher<Long, BrewStageAccessor, BrewStage> brewStageRefresher(BrewStageRepository repo) {
        return new AccessorRefresher<>(
            accessor -> accessor.getBrewStage(),
            (accessor, brewStage) -> accessor.setBrewStage(brewStage),
            ids -> repo.findAllById(ids)
        );
    }
    
    @Bean
    public AccessorRefresher<Long, BrewTaskAccessor, BrewTask> brewTaskRefresher(BrewTaskRepository repo) {
        return new AccessorRefresher<>(
            accessor -> accessor.getTask(),
            (accessor, brewTask) -> accessor.setTask(brewTask),
            ids -> repo.findAllById(ids)
        );
    }
    
    @Bean
    public AccessorRefresher<Long, BrewStageStatusAccessor, BrewStageStatus> brewStageStatusRefresher(BrewStageStatusRepository repo) {
        return new AccessorRefresher<>(
            accessor -> accessor.getStatus(),
            (accessor, brewStageStatus) -> accessor.setStatus(brewStageStatus),
            ids -> repo.findAllById(ids)
        );
    }
    
    @Bean
    public AccessorRefresher<Long, ParentMixtureAccessor, Mixture> mixtureRefresher(MixtureRepository repo) {
        return new AccessorRefresher<>(
            accessor -> accessor.getParentMixture(),
            (accessor, parentMixture) -> accessor.setParentMixture(parentMixture),
            ids -> repo.findAllById(ids)
        );
    }
    
    @Bean
    public AccessorRefresher<Long, EquipmentAccessor, Equipment> equipmentRefresher(EquipmentRepository repo) {
        return new AccessorRefresher<>(
            accessor -> accessor.getEquipment(),
            (accessor, equipment) -> accessor.setEquipment(equipment),
            ids -> repo.findAllById(ids)
        );
    }
    
    @Bean
    public AccessorRefresher<Long, UserAccessor, User> userRefresher(UserRepository repo) {
        return new AccessorRefresher<>(
            accessor -> accessor.getUser(),
            (accessor, user) -> accessor.setUser(user),
            ids -> repo.findAllById(ids)
        );
    }
    
}
