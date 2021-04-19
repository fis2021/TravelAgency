package org.fis.student.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
}