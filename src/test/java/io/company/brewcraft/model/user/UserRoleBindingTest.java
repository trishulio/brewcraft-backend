package io.company.brewcraft.model.user;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserRoleBindingTest {

    private UserRoleBinding binding;

    @BeforeEach
    public void init() {
        binding = new UserRoleBinding();
    }

    @Test
    public void testAllArgConstructor_SetsAllFields() {
        binding = new UserRoleBinding(1L, new UserRole(10L), new User(100L));

        assertEquals(1L, binding.getId());
        assertEquals(new UserRole(10L), binding.getRole());
        assertEquals(new User(100L), binding.getUser());
    }

    @Test
    public void testAccessRole() {
        assertNull(binding.getRole());
        binding.setRole(new UserRole(100L));
        assertEquals(new UserRole(100L), binding.getRole());
    }

    @Test
    public void testAccessUser() {
        assertNull(binding.getUser());
        binding.setUser(new User(100L));
        assertEquals(new User(100L), binding.getUser());
    }
}