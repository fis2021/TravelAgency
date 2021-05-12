package org.fis.student.services;

import org.apache.commons.io.FileUtils;
import org.fis.student.exceptions.EmailAlreadyUsedException;
import org.fis.student.exceptions.EmptyTextfieldsException;
import org.fis.student.exceptions.UsernameAlreadyExistsException;
import org.fis.student.exceptions.WrongPasswordConfirmationException;
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
    @DisplayName("When creating account, all fields must be completed")
    void testCheckAllFieldsCompleted(){
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextfields("","","","","","","");
        });
    }

}