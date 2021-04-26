package io.company.brewcraft.model.user;

import io.company.brewcraft.model.common.FixedTypeEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "user_role_type")
@Table
@SequenceGenerator(name = "fixed_type_generator", sequenceName = "user_role_type_sequence", allocationSize = 1)
public class UserRoleType extends FixedTypeEntity {

}
