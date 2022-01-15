package com.example.optic;

import com.example.optic.AppControllers.RegisterController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.UserBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class ControllerRegister extends GraphicController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confPassword;
    @FXML
    private ToggleGroup profile;
    @FXML
    private RadioButton userRB;
    @FXML
    private RadioButton refereeRB;
    @FXML
    private RadioButton adminRB;
    @FXML
    private Label addressLabel;
    @FXML
    private TextField addressField;
    @FXML
    private Label pgNameLabel;
    @FXML
    private TextField pgNameField;
    @FXML
    private Label pgProv;
    @FXML
    private TextField pgProvField;


    public void toLogin(ActionEvent e) throws IOException {
        this.toView("views/login.fxml");
    }

    public void register(ActionEvent e) throws Exception {
        boolean res = false;
        userRB.setUserData(1);
        adminRB.setUserData(2);
        refereeRB.setUserData(3);
        Alert err = new Alert(Alert.AlertType.ERROR);
        int prof = (int) profile.getSelectedToggle().getUserData();
        if(username.getText().isEmpty() || password.getText().isEmpty()){
            err.setContentText("Inserire i dati");
            err.show();
        }else if(password.getText().length() < 4){
            err.setContentText("La password deve contenere almeno 4 caratteri");
            err.show();
        }else if(password.getText().equals(confPassword.getText()) != true){
            err.setContentText("Le password non combaciano");
            err.show();
        }else if(prof == 2 && (addressField.getText().isEmpty() || pgNameField.getText().isEmpty() || pgProv.getText().isEmpty())) {
            err.setContentText("Inserire informazioni campo!");
            err.show();
        } else{
            String name = username.getText();
            String pw = password.getText();
            String view = "views/register.fxml";
            UserBean bean = new UserBean();
            bean.setUsername(name);
            bean.setPassword(pw);
            switch (prof) {
                //registrazione admin
                case 2 -> {
                    bean.setVia(addressField.getText());
                    bean.setNomeC(pgNameField.getText());
                    bean.setProv(pgProvField.getText());
                    res = RegisterController.isUsernameUsed(bean, 2);
                    if (!res) {
                        RegisterController.userRegister(bean, 2);
                        view = "views/modPgPage.fxml";
                    }else{
                        RegisterController.closeConn(2);
                    }
                }
                //registrazione arbitro
                case 3 -> {
                    res = RegisterController.isUsernameUsed(bean, 3);
                    if (!res) {
                        RegisterController.userRegister(bean, 3);
                        view = "views/refCampo.fxml";
                    }else{
                        RegisterController.closeConn(3);
                    }
                }
                //registrazione giocatore
                default -> {
                    res = RegisterController.isUsernameUsed(bean, 1);
                    if (!res) {
                        RegisterController.userRegister(bean, 1);
                        view = "views/userHomeMap.fxml";
                    }else{
                        RegisterController.closeConn(1);
                    }
                }

            }
            if (!res) {
                Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
                conf.setContentText("Registrazione avvenuta con successo!");
                conf.show();
                this.toView(view, name);
            } else {
                err.setContentText("Username già utilizzato");
                err.show();
            }
        }
    }

    public void showAddress(){
        addressField.setVisible(true);
        addressLabel.setVisible(true);
        pgNameLabel.setVisible(true);
        pgNameField.setVisible(true);
        pgProv.setVisible(true);
        pgProvField.setVisible(true);
    }

    public void hideAddress(){
        addressField.setVisible(false);
        addressLabel.setVisible(false);
        pgNameLabel.setVisible(false);
        pgNameField.setVisible(false);
        pgProv.setVisible(false);
        pgProvField.setVisible(false);
    }
}
