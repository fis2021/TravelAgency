package org.fis.student;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis.student.services.FileSystemService;
import org.fis.student.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.assertions.api.Assertions.assertThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

@ExtendWith(ApplicationExtension.class)
class LoginTest {


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

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 600, 460));
        primaryStage.show();
    }

    @Test
    void testLoginAndRegister(FxRobot robot) {

        ///se verifica adaugarea unui user
        robot.clickOn("#goToRegisterButton");

        robot.clickOn("#usernameRegister");
        robot.write("user");
        robot.clickOn("#passwordRegister");
        robot.write("user");
        robot.clickOn("#password1Register");
        robot.write("user");
        robot.clickOn("#roleRegister");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#nameRegister");
        robot.write("User");
        robot.clickOn("#addressRegister");
        robot.write("Adresa");
        robot.clickOn("#emailRegister");
        robot.write("user@email.com");
        robot.clickOn("#phoneRegister");
        robot.write("0234");

        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#registrationMessage").queryText()).hasText("Account created successfully!");
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);

        ///se verifica daca se poate adauga un user cu acelasi username
        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#registrationMessage").queryText()).hasText(String.format("An account with the username %s already exists!", "user"));

        robot.clickOn("#goBackToLoginButton");

        //Login cu username gresit
        robot.clickOn("#usernameLogin");
        robot.write("user1");
        robot.clickOn("#passwordLogin");
        robot.write("user");
        robot.clickOn("#roleLogin");
        robot.type(KeyCode.ENTER);

        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#loginUsernameMessage").queryText()).hasText(String.format("An account with the username %s does not exist!","user1"));

        //sa se stearga continutul din textfields
        robot.clickOn("#goToRegisterButton");
        robot.clickOn("#goBackToLoginButton");

        //Login cu parola gresita
        robot.clickOn("#usernameLogin");
        robot.write("user");
        robot.clickOn("#passwordLogin");
        robot.write("gresit");
        robot.clickOn("#roleLogin");
        robot.type(KeyCode.ENTER);

        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#loginUsernameMessage").queryText()).hasText("Wrong password ! ");

        robot.clickOn("#goToRegisterButton");
        robot.clickOn("#goBackToLoginButton");

        //Login cu rol gresit
        robot.clickOn("#usernameLogin");
        robot.write("user");
        robot.clickOn("#passwordLogin");
        robot.write("user");
        robot.clickOn("#roleLogin");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);

        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#loginUsernameMessage").queryText()).hasText("You selected the incorrect role! ");


    }
}