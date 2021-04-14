// package io.company.brewcraft.repository.test;

// import javax.measure.Quantity;
// import javax.persistence.CascadeType;
// import javax.persistence.Entity;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.OneToOne;

// import io.company.brewcraft.model.QuantityEntity;
// import io.company.brewcraft.service.mapper.QuantityMapper;

// @Entity
// public class TestEntity {
//     private static Long QTY_ID = 1L;

//     @Id
//     private Long id;

//     @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
//     @JoinColumn(name = "qty_id", referencedColumnName = "id")
//     private QuantityEntity qty;

//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public Quantity<?> getQuantity() {
//         return QuantityMapper.INSTANCE.fromEntity(qty);
//     }

//     public void setQuantity(Quantity<?> quantity) {
//         qty = QuantityMapper.INSTANCE.toEntity(quantity);
//     }
// }
