package io.company.brewcraft.model.user;

import io.company.brewcraft.model.common.FixedTypeEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "user_salutation")
@Table
@SequenceGenerator(name = "fixed_type_generator", sequenceName = "user_salutation_sequence", allocationSize = 1)
public class UserSalutation extends FixedTypeEntity {
}