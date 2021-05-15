package org.fis.student.services;

import org.apache.commons.io.FileUtils;
import org.fis.student.exceptions.*;
import org.fis.student.model.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class UserServiceTest {
    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
    }

    @AfterEach
    void tearDown(){
        System.out.println("After class");
        UserService.getDatabase().close();
    }

    @Test
    @DisplayName("Database is initialized, and there are no users")
    void testDatabaseIsInitializedAndNoUserIsPersisted() {
        assertThat(UserService.getAllUsers()).isNotNull();
        assertThat(UserService.getAllUsers()).isEmpty();
    }

    @Test
    @DisplayName("User is successfully added to database")
    void testCustomerIsAddedToDatabase() throws UsernameAlreadyExistsException, WrongPasswordConfirmationException, EmailAlreadyUsedException, EmptyTextfieldsException {
        UserService.addUser("user","user","Customer","User","adresa","user@email.com","0728","user");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user = UserService.getAllUsers().get(0);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("user");
        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword("user","user"));
        assertThat(user.getRole()).isEqualTo("Customer");
        assertThat(user.getName()).isEqualTo("User");
        assertThat(user.getAddress()).isEqualTo("adresa");
        assertThat(user.getEmail()).isEqualTo("user@email.com");
        assertThat(user.getPhone()).isEqualTo("0728");
    }

    @Test
    @DisplayName("Travel Agency is successfully added to database")
    void testTravelAgencyIsAddedToDatabase() throws UsernameAlreadyExistsException, WrongPasswordConfirmationException, EmailAlreadyUsedException, EmptyTextfieldsException {
        UserService.addUser("user","user","Travel Agency","User","adresa","user@email.com","0728","user");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user = UserService.getAllUsers().get(0);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("user");
        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword("user","user"));
        assertThat(user.getRole()).isEqualTo("Travel Agency");
        assertThat(user.getName()).isEqualTo("User");
        assertThat(user.getAddress()).isEqualTo("adresa");
        assertThat(user.getEmail()).isEqualTo("user@email.com");
        assertThat(user.getPhone()).isEqualTo("0728");
    }

    @Test
    @DisplayName("User cannot be added twice")
    void testUserCannotBeAddedTwice() {
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            UserService.addUser("user","user","Customer","User","adresa","user@email.com","0728","user");
            UserService.addUser("user","user","Customer","User","adresa","user@email.com","0728","user");
        });
    }

    @Test
    @DisplayName("The password is encoded correctly")
    void testEncodePassword() {
        assertThat("test").isNotEqualTo(UserService.encodePassword("user","test"));
    }

    @Test
    @DisplayName("The user already exists exception tested")
    void testCheckUserAlreadyExists(){
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            UserService.addUser("user", "user", "Customer", "User", "adresa", "user@email.com", "0728", "user");
            assertThat(UserService.getAllUsers()).isNotEmpty();
            assertThat(UserService.getAllUsers()).size().isEqualTo(1);
            UserService.checkUserDoesNotAlreadyExist("user");
        });
    }

    @Test
    @DisplayName("The same email can't be used for more than one account")
    void testCheckEmailAlreadyUsed(){
        assertThrows(EmailAlreadyUsedException.class, () -> {
            UserService.addUser("user", "user", "Customer", "User", "adresa", "user@email.com", "0728", "user");
            assertThat(UserService.getAllUsers()).isNotEmpty();
            assertThat(UserService.getAllUsers()).size().isEqualTo(1);
            UserService.checkUsedEmail("user@email.com");
        });
    }

    @Test
    @DisplayName("The password must be confirmed correctly")
    void testCheckPasswordConfirmation(){
        assertThrows(WrongPasswordConfirmationException.class, () -> {
            UserService.checkPasswordConfirmation("pw1","pw2");
        });
    }

    @Test
    @DisplayName("When creating account, username field must be completed")
    void testCheckUsernameFieldCompleted(){
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextfields("","1","1","1","1","1","1");
        });
    }
    @Test
    @DisplayName("When creating account, password field must be completed")
    void testCheckPasswordFieldCompleted(){
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextfields("1","","1","1","1","1","1");
        });
    }
    @Test
    @DisplayName("When creating account, role  must be completed")
    void testCheckRoleCompleted(){
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextfields("1","1","","1","1","1","1");
        });
    }
    @Test
    @DisplayName("When creating account, name field must be completed")
    void testCheckNameFieldCompleted(){
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextfields("1","1","1","","1","1","1");
        });
    }
    @Test
    @DisplayName("When creating account, address field must be completed")
    void testCheckAddressFieldCompleted(){
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextfields("1","1","1","1","","1","1");
        });
    }
    @Test
    @DisplayName("When creating account, email field must be completed")
    void testCheckEmailFieldCompleted(){
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextfields("1","1","1","1","1","","1");
        });
    }
    @Test
    @DisplayName("When creating account, phone field must be completed")
    void testCheckPhoneFieldCompleted(){
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextfields("1","1","1","1","1","1","");
        });
    }


    @Test
    @DisplayName("Login successfully")
    void testCheckCredentialsWhenLogin() throws Exception{
        UserService.addUser("user","user","Customer","User","adresa","user@email.com","0728","user");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user = UserService.getAllUsers().get(0);
        assertThat(user).isNotNull();
        UserService.checkUserCredentials("user","user","Customer");
    }

    @Test
    @DisplayName("The username must be correct when login")
    void testCheckUsernameWhenLogin(){
        assertThrows(UsernameDoesNotExistException.class, () -> {
            UserService.addUser("user", "user", "Customer", "User", "adresa", "user@email.com", "0728", "user");
            assertThat(UserService.getAllUsers()).isNotEmpty();
            assertThat(UserService.getAllUsers()).size().isEqualTo(1);
            UserService.checkUserCredentials("user1","user","Customer");
        });
    }

    @Test
    @DisplayName("The password must be correct when login")
    void testCheckPasswordWhenLogin(){
        assertThrows(WrongPasswordException.class, () -> {
            UserService.addUser("user", "user", "Customer", "User", "adresa", "user@email.com", "0728", "user");
            assertThat(UserService.getAllUsers()).isNotEmpty();
            assertThat(UserService.getAllUsers()).size().isEqualTo(1);
            UserService.checkUserCredentials("user","user2","Customer");
        });
    }

    @Test
    @DisplayName("The role must be correct when login")
    void testCheckRoleWhenLogin(){
        assertThrows(WrongRoleException.class, () -> {
            UserService.addUser("user", "user", "Customer", "User", "adresa", "user@email.com", "0728", "user");
            assertThat(UserService.getAllUsers()).isNotEmpty();
            assertThat(UserService.getAllUsers()).size().isEqualTo(1);
            UserService.checkUserCredentials("user","user","Travel Agency");
        });
    }
}