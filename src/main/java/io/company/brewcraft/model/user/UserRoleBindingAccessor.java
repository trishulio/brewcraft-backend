package io.company.brewcraft.model.user;

import java.util.List;

public interface UserRoleBindingAccessor {

    List<UserRoleBinding> getRoleBindings();

    void setRoleBindings(List<UserRoleBinding> roleBindings);

}
