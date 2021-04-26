package io.company.brewcraft.model.user;


import io.company.brewcraft.model.common.FixedTypeEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "user_status")
@Table
@SequenceGenerator(name = "fixed_type_generator", sequenceName = "user_status_sequence", allocationSize = 1)
public class UserStatus extends FixedTypeEntity {

    public static final String DEFAULT_STATUS_NAME = "DISABLED";
}
