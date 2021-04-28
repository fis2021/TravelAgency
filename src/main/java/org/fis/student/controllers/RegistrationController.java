package org.fis.student.controllers;

import javafx.event.ActionEvent;
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
import org.fis.student.exceptions.EmailAlreadyUsedException;
import org.fis.student.exceptions.EmptyTextfieldsException;
import org.fis.student.exceptions.UsernameAlreadyExistsException;
import org.fis.student.exceptions.WrongPasswordConfirmationException;
import org.fis.student.services.UserService;

public class RegistrationController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox role;
    @FXML
    private Text registrationMessage;
    @FXML
    private PasswordField passwordField1;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;

    @FXML
    public void initialize() {
        role.getItems().addAll("Customer", "Travel Agency");
    }

    @FXML
    public void handleRegisterAction() {
        try {
            UserService.addUser(usernameField.getText(), passwordField.getText(), (String) role.getValue(), nameField.getText(), addressField.getText(), emailField.getText(), phoneField.getText(), passwordField1.getText());
            registrationMessage.setText("Account created successfully!");
        } catch (EmptyTextfieldsException e){
            registrationMessage.setText(e.getMessage());
        } catch (WrongPasswordConfirmationException e) {
            registrationMessage.setText(e.getMessage());
        } catch (UsernameAlreadyExistsException e) {
            registrationMessage.setText(e.getMessage());
        } catch (EmailAlreadyUsedException e){
            registrationMessage.setText(e.getMessage());
        }
    }

    public void goBackToLogin(javafx.event.ActionEvent login)throws Exception{
        Parent backToLogin = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        Stage window = (Stage) ((Node) login.getSource()).getScene().getWindow();;
        window.setTitle("Login");
        window.setScene(new Scene(backToLogin, 600, 460));
        window.show();
    }
}