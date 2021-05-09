package io.company.brewcraft.service.impl.user;

import io.company.brewcraft.model.common.FixedTypeEntity;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleType;
import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserStatus;
import io.company.brewcraft.repository.user.UserRepository;
import io.company.brewcraft.security.idp.IdentityProviderClient;
import io.company.brewcraft.security.idp.UserAttributeType;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.ValidationException;
import io.company.brewcraft.util.validator.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private UserRepository userRepository;

    private IdentityProviderClient idpClient;

    private UtilityProvider utilProvider;

    private UserServiceImpl userService;


    @BeforeEach
    public void init() {
        userRepository = mock(UserRepository.class);
        idpClient = mock(IdentityProviderClient.class);
        utilProvider = mock(UtilityProvider.class);
        when(utilProvider.getValidator()).thenReturn(new Validator());
        userService = new UserServiceImpl(userRepository, idpClient, utilProvider);
    }

    @Test
    public void testGetUser_ReturnsNull_WhenUserNotFound() {
        final Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        final User user = userService.getUser(userId);

        assertNull(user);
    }

    @Test
    public void testGetUser_ReturnsUser_WhenUserFound() {
        final Long userId = 1L;
        final User savedUser = createUser(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(savedUser));

        final User userActual = userService.getUser(userId);

        assertEquals(userActual, savedUser);
    }

    @Test
    public void testGetUsers_ReturnsUserPageMatchedToGivenCriteria_WhenValuesProvidedForCriteria() {
        final Set<Long> ids = Collections.singleton(1L);
        final Set<Long> excludeIds = Collections.singleton(2L);
        final Set<String> userNames = Collections.singleton("testUserName");
        final Set<String> displayNames = Collections.singleton("testDisplayName");
        final Set<String> emails = Collections.singleton("testEmail");
        final Set<String> phoneNumbers = Collections.singleton("testPhoneNumber");
        final Set<String> status = Collections.singleton("testStatus");
        final Set<String> salutations = Collections.singleton("testSalutation");
        final Set<String> roles = Collections.singleton("testRole");
        final int page = 1;
        final int size = 100;
        final Set<String> sort = Collections.singleton("testUserName");
        final boolean orderAscending = true;

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Specification<User>> specificationCaptor = ArgumentCaptor.forClass(Specification.class);
        ArgumentCaptor<PageRequest> pageRequestCaptor = ArgumentCaptor.forClass(PageRequest.class);

        final User user = createUser(ids.iterator().next());
        when(userRepository.findAll(specificationCaptor.capture(), pageRequestCaptor.capture())).thenReturn(new PageImpl<>(Collections.singletonList(user)));

        final Page<User> userPage = userService.getUsers(ids, excludeIds, userNames, displayNames, emails, phoneNumbers, status, salutations, roles, page, size, sort, orderAscending);

        assertEquals(1, userPage.getTotalElements());

        final User matchedUser = userPage.getContent().get(0);
        assertEquals(ids.iterator().next(), matchedUser.getId());
        assertEquals(userNames.iterator().next(), matchedUser.getUserName());
        assertEquals(displayNames.iterator().next(), matchedUser.getDisplayName());
        assertEquals(emails.iterator().next(), matchedUser.getEmail());
        assertEquals(phoneNumbers.iterator().next(), matchedUser.getPhoneNumber());
        assertEquals(salutations.iterator().next(), matchedUser.getSalutation().getName());
        assertEquals(status.iterator().next(), matchedUser.getStatus().getName());
        assertEquals(roles.iterator().next(), matchedUser.getRoles().get(0).getUserRoleType().getName());

        final PageRequest pageRequest = pageRequestCaptor.getValue();
        final Sort.Order order = pageRequest.getSort().get().findFirst().get();
        assertEquals(sort.iterator().next(), order.getProperty());
        assertEquals(orderAscending, order.getDirection().isAscending());

        final Specification<User> userSpecification = specificationCaptor.getValue();

        //TODO verify  userSpecification lambda
    }

    @Test
    public void testAddUser_WhenUserNotProvided_ThrowsValidationException() {
        assertThrows(ValidationException.class, () -> userService.addUser(null));
    }

    @Test
    public void testAddUser_PersistUserInDB_WhenUserIsProvided() {
        final User addingUser = createUser(null);
        final Long userId = 1L;
        when(userRepository.mapAndSave(addingUser)).thenReturn(createUser(userId));
        doNothing().when(idpClient).createUser(anyString(), anyList());

        final User persistedUser = userService.addUser(addingUser);

        assertEquals(userId, persistedUser.getId());
        assertPersistedUser(addingUser, persistedUser);
    }

    @Test
    public void testAddUser_SaveUserToIdpWithEmailAttributeByUserName_WhenProvidedUserPersistedToDB() {
        final String userEmail = "testEmail";
        final User addingUser = createUserWithEmail(null, userEmail);
        final User persistedUser = createUserWithEmail(1L, userEmail);
        when(userRepository.mapAndSave(addingUser)).thenReturn(persistedUser);
        ArgumentCaptor<String> userNameCaptor = ArgumentCaptor.forClass(String.class);
        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<UserAttributeType>> userAttributesCaptor = ArgumentCaptor.forClass(List.class);
        doNothing().when(idpClient).createUser(userNameCaptor.capture(), userAttributesCaptor.capture());

        userService.addUser(addingUser);

        assertEquals(persistedUser.getUserName(), userNameCaptor.getValue());
        final List<UserAttributeType> userAttributes = userAttributesCaptor.getValue();
        assertEquals(2, userAttributes.size());
        final UserAttributeType emailAttribute = userAttributes.get(0);
        assertEquals(userEmail, emailAttribute.getValue());
        final UserAttributeType emailVerifiedAttribute = userAttributes.get(1);
        assertEquals("true", emailVerifiedAttribute.getValue());
    }

    @Test
    public void testPutUser_WhenUserNotProvided_ThrowsValidationException() {
        assertThrows(ValidationException.class, () -> userService.putUser(1L, null));
    }

    @Test
    public void testPutUser_UpdateUserInDB_WhenUserIsProvided() {
        final Long userId = 1L;
        final User updatingUser = createUserWithEmail(null, null);
        final User updatedUser = createUserWithEmail(userId, null);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.mapAndSave(userCaptor.capture())).thenReturn(updatedUser);

        final User actualUser = userService.putUser(userId, updatingUser);

        assertEquals(updatedUser, actualUser);
        assertEquals(userId, userCaptor.getValue().getId());
    }

    @Test
    public void testPutUser_PreventsUpdatingIdpEmailAttribute_WhenUserIsProvidedWithoutEmail() {
        final Long userId = 1L;
        final User updatingUser = createUserWithEmail(null, "");
        final User updatedUser = createUserWithEmail(userId, "");
        when(userRepository.mapAndSave(updatingUser)).thenReturn(updatedUser);

        userService.putUser(userId, updatingUser);

        verifyNoInteractions(idpClient);
    }

    @Test
    public void testPutUser_PreventsUpdatingIdpEmailAttribute_WhenEmailIsNotUpdatedInProvidedUser() {
        final Long userId = 1L;
        final String updatingUserEmail = "sameTestEmail";
        final User updatingUser = createUserWithEmail(null, updatingUserEmail);
        final User updatedUser = createUserWithEmail(userId, updatingUserEmail);
        when(userRepository.mapAndSave(updatingUser)).thenReturn(updatedUser);
        when(userRepository.findEmailPerId(userId)).thenReturn(Optional.of(updatingUserEmail));

        userService.putUser(userId, updatingUser);

        verifyNoInteractions(idpClient);
    }

    @Test
    public void testPutUser_ThrowsEntityNotFoundException_WhenEmailDoesNotExistForProvidedUser() {
        final Long userId = 1L;
        final String updatingUserEmail = "sameTestEmail";
        final User updatingUser = createUserWithEmail(null, updatingUserEmail);

        when(userRepository.findEmailPerId(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.putUser(userId, updatingUser));
    }

    @Test
    public void testPutUser_ThrowsEntityNotFoundException_WhenUserDoesNotExistForProvidedUser() {
        final Long userId = 1L;
        final String updatingUserEmail = "differentTestEmail";
        final String existingUserEmail = "existingUserEmail";
        final User updatingUser = createUserWithEmail(null, updatingUserEmail);
        final User updatedUser = createUserWithEmail(userId, updatingUserEmail);
        when(userRepository.mapAndSave(updatingUser)).thenReturn(updatedUser);
        when(userRepository.findEmailPerId(userId)).thenReturn(Optional.of(existingUserEmail));
        when(userRepository.findUserNamePerId(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.putUser(userId, updatingUser));
    }

    @Test
    public void testPutUser_UpdatesIdpEmailAttribute_WhenEmailIsUpdatedInPersistedProvidedUser() {
        final Long userId = 1L;
        final String updatingUserEmail = "differentTestEmail";
        final String existingUserEmail = "existingUserEmail";
        final User updatingUser = createUserWithEmail(null, updatingUserEmail);
        final User updatedUser = createUserWithEmail(userId, updatingUserEmail);
        when(userRepository.mapAndSave(updatingUser)).thenReturn(updatedUser);
        when(userRepository.findEmailPerId(userId)).thenReturn(Optional.of(existingUserEmail));
        when(userRepository.findUserNamePerId(userId)).thenReturn(Optional.of(updatedUser.getUserName()));
        ArgumentCaptor<String> userNameCaptor = ArgumentCaptor.forClass(String.class);
        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<UserAttributeType>> userAttributesCaptor = ArgumentCaptor.forClass(List.class);
        doNothing().when(idpClient).updateUser(userNameCaptor.capture(), userAttributesCaptor.capture());

        userService.putUser(userId, updatingUser);

        assertEquals(updatedUser.getUserName(), userNameCaptor.getValue());
        final List<UserAttributeType> userAttributes = userAttributesCaptor.getValue();
        assertEquals(1, userAttributes.size());
        final UserAttributeType emailAttribute = userAttributes.get(0);
        assertEquals(updatingUserEmail, emailAttribute.getValue());
    }

    @Test
    public void testPatchUser_WhenUserNotProvided_ThrowsValidationException() {
        assertThrows(ValidationException.class, () -> userService.patchUser(1L, null));
    }

    @Test
    public void testPatchUser_ThrowsEntityNotFoundException_WhenUserDoesNotExistForProvidedId() {
        final Long userId = 1L;
        final User updatingUser = createUserWithEmail(null, null);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.patchUser(1L, updatingUser));
    }

    @Test
    public void testPatchUser_UpdatesOnlyProvidedAttributes_WhenUserProvidedWithUpdatedAttributes() {
        final Long userId = 1L;
        final String updatingDisplayName = "changed display name";
        final User updatingUser = createUserWithDisplayName(null, updatingDisplayName);
        final User existingUser = createUser(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        final User updatedUser = createUser(userId);
        updatedUser.setDisplayName(updatingDisplayName);

        when(userRepository.mapAndSave(any(User.class))).thenReturn(updatedUser);

        final User actualUser = userService.patchUser(userId, updatingUser);

        assertEquals(updatingDisplayName, actualUser.getDisplayName());
    }

    @Test
    public void testPatchUser_PreventsUpdatingIdpEmailAttribute_WhenUserIsProvidedWithoutEmail() {
        final Long userId = 1L;
        final User updatingUser = createUserWithEmail(null, "");
        final User existingUser = createUserWithEmail(userId, "ExistingEmail");
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.mapAndSave(updatingUser)).thenReturn(createUserWithEmail(userId, ""));

        userService.patchUser(userId, updatingUser);

        verifyNoInteractions(idpClient);
    }

    @Test
    public void testPatchUser_PreventsUpdatingIdpEmailAttribute_WhenEmailIsNotUpdatedInProvidedUser() {
        final Long userId = 1L;
        final String updatingUserEmail = "sameTestEmail";
        final User updatingUser = createUserWithEmail(null, updatingUserEmail);
        final User existingUser = createUserWithEmail(userId, updatingUserEmail);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.mapAndSave(updatingUser)).thenReturn(createUserWithEmail(userId, updatingUserEmail));

        userService.patchUser(userId, updatingUser);

        verifyNoInteractions(idpClient);
    }

    @Test
    public void testPatchUser_ThrowsEntityNotFoundException_WhenUserDoesNotExistForProvidedUser() {
        final Long userId = 1L;
        final String updatingUserEmail = "differentTestEmail";
        final String existingUserEmail = "existingUserEmail";
        final User updatingUser = createUserWithEmail(null, updatingUserEmail);
        final User existingUser = createUserWithEmail(userId, existingUserEmail);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.mapAndSave(updatingUser)).thenReturn(createUserWithEmail(userId, updatingUserEmail));
        when(userRepository.findUserNamePerId(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.patchUser(userId, updatingUser));
    }

    @Test
    public void testPatchUser_UpdatesIdpEmailAttribute_WhenEmailIsUpdatedInPersistedProvidedUser() {
        final Long userId = 1L;
        final String updatingUserEmail = "differentTestEmail";
        final String existingUserEmail = "existingUserEmail";
        final User updatingUser = createUserWithEmail(null, updatingUserEmail);
        final User updatedUser = createUserWithEmail(userId, updatingUserEmail);
        final User existingUser = createUserWithEmail(userId, existingUserEmail);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.mapAndSave(updatingUser)).thenReturn(updatedUser);
        when(userRepository.findUserNamePerId(userId)).thenReturn(Optional.of(updatedUser.getUserName()));
        ArgumentCaptor<String> userNameCaptor = ArgumentCaptor.forClass(String.class);
        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<UserAttributeType>> userAttributesCaptor = ArgumentCaptor.forClass(List.class);
        doNothing().when(idpClient).updateUser(userNameCaptor.capture(), userAttributesCaptor.capture());

        userService.patchUser(userId, updatingUser);

        assertEquals(updatedUser.getUserName(), userNameCaptor.getValue());
        final List<UserAttributeType> userAttributes = userAttributesCaptor.getValue();
        assertEquals(1, userAttributes.size());
        final UserAttributeType emailAttribute = userAttributes.get(0);
        assertEquals(updatingUserEmail, emailAttribute.getValue());
    }

    @Test
    public void testDeleteUser_ThrowsEntityNotFoundException_WhenUserNotFound() {
        final Long userId = 1L;

        when(userRepository.findUserNamePerId(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(userId));
    }

    @Test
    public void testDeleteUser_DeletesUser_WhenUserExistsForProvidedId() {
        final Long userId = 1L;
        final String userName = "testUserName";

        when(userRepository.findUserNamePerId(userId)).thenReturn(Optional.of(userName));

        ArgumentCaptor<Long> userIdCaptor = ArgumentCaptor.forClass(Long.class);
        doNothing().when(userRepository).deleteById(userIdCaptor.capture());
        doNothing().when(idpClient).deleteUser(userName);

        userService.deleteUser(userId);

        assertEquals(userId, userIdCaptor.getValue());
    }

    @Test
    public void testDeleteUser_RemovesUserFromIdpByUserName_WhenUserDeletedFromDB() {
        final Long userId = 1L;
        final String userName = "testUserName";

        when(userRepository.findUserNamePerId(userId)).thenReturn(Optional.of(userName));

        ArgumentCaptor<String> userNameCaptor = ArgumentCaptor.forClass(String.class);
        doNothing().when(userRepository).deleteById(userId);
        doNothing().when(idpClient).deleteUser(userNameCaptor.capture());

        userService.deleteUser(userId);

        assertEquals(userName, userNameCaptor.getValue());
    }

    private void assertPersistedUser(final User addingUser, final User persistedUser) {
        assertEquals(addingUser.getUserName(), persistedUser.getUserName());
        assertEquals(addingUser.getDisplayName(), persistedUser.getDisplayName());
        assertEquals(addingUser.getFirstName(), persistedUser.getFirstName());
        assertEquals(addingUser.getLastName(), persistedUser.getLastName());
        assertEquals(addingUser.getEmail(), persistedUser.getEmail());
        assertEquals(addingUser.getPhoneNumber(), persistedUser.getPhoneNumber());
        assertEquals(addingUser.getStatus().getName(), persistedUser.getStatus().getName());
        assertEquals(addingUser.getSalutation().getName(), persistedUser.getSalutation().getName());
        assertEquals(addingUser.getRoles().size(), persistedUser.getRoles().size());
        assertEquals(addingUser.getRoles().get(0).getUserRoleType().getName(), persistedUser.getRoles().get(0).getUserRoleType().getName());
    }

    private User createUser(final Long userId) {
        final User user = new User();
        user.setId(userId);
        user.setUserName("testUserName");
        user.setDisplayName("testDisplayName");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setEmail("testEmail");
        user.setPhoneNumber("testPhoneNumber");
        user.setImageUrl("testImageUrl");
        user.setStatus(createFixedType(UserStatus::new, "testStatus"));
        user.setSalutation(createFixedType(UserSalutation::new, "testSalutation"));
        user.setRoles(Collections.singletonList(createUserRole("testRole")));
        return user;
    }

    private UserRole createUserRole(final String roleName) {
        final UserRole userRole = new UserRole();
        userRole.setUserRoleType(createFixedType(UserRoleType::new, roleName));
        return userRole;
    }

    private <T extends FixedTypeEntity> T createFixedType(final Supplier<T> fixedTypeSupplier, final String name) {
        final T fixedType = fixedTypeSupplier.get();
        fixedType.setName(name);
        return fixedType;
    }


    private User createUserWithDisplayName(final Long id, final String displayName) {
        final User user = new User();
        user.setDisplayName(displayName);
        return user;
    }

    private User createUserWithEmail(final Long id, final String email) {
        final User user = new User();
        user.setId(id);
        user.setUserName("testUserName");
        user.setEmail(email);
        return user;
    }
}
