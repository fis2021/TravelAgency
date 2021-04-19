package org.fis.student.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fis.student.exceptions.UsernameDoesNotExistException;
import org.fis.student.exceptions.WrongPasswordException;
import org.fis.student.exceptions.WrongRoleException;
import org.fis.student.services.UserService;

public class LoginController {

    @FXML
    private Text loginUsernameMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private ChoiceBox role;

    @FXML
    public void initialize() {
        role.getItems().addAll("Customer", "Travel Agency");
    }

    @FXML
    public void handleLoginAction() {
        try {
            UserService.checkUserCredentials(usernameField.getText(), passwordField.getText(), (String) role.getValue());
            loginUsernameMessage.setText("Login successfully!");
        } catch (UsernameDoesNotExistException e) {
            loginUsernameMessage.setText(e.getMessage());
        } catch (WrongRoleException e){
            loginUsernameMessage.setText((e.getMessage()));
        } catch (WrongPasswordException e){
            loginUsernameMessage.setText(e.getMessage());
        }
    }
    public void goBackToResgisterScene(javafx.event.ActionEvent login)throws Exception{
        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        Stage window = (Stage) ((Node) login.getSource()).getScene().getWindow();;
        window.setTitle("Registration");
        window.setScene(new Scene(root1, 600, 460));
        window.show();
    }
}