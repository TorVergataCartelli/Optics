package com.example.optic;

import com.example.optic.AppControllers.LoginController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.RefereeBean;
import com.example.optic.dao.AdminDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class ControllerLogin extends GraphicController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ToggleGroup profileL;
    @FXML
    private RadioButton userRB;
    @FXML
    private RadioButton refereeRB;
    @FXML
    private RadioButton adminRB;

    public void login(ActionEvent e) throws Exception {
        boolean res = false;
        Alert err = new Alert(Alert.AlertType.ERROR);
        if(username.getText().isEmpty() || password.getText().isEmpty()){
            err.setContentText("Inserire i dati");
            err.show();
        }else {
            String name = username.getText();
            String pw = password.getText();

            userRB.setUserData(1);
            adminRB.setUserData(2);
            refereeRB.setUserData(3);

            int prof = (int) profileL.getSelectedToggle().getUserData();
            String view;
            switch (prof) {
                case 2 -> {
                    AdminBean a = new AdminBean();
                    a.setUsername(name);
                    a.setPassword(pw);
                    res = LoginController.adminLogin(a);
                    view = "views/modPgPage.fxml";
                    if(!res){
                        LoginController.closeConn(2);
                    }
                }
                case 3 ->{
                    RefereeBean r= new RefereeBean();
                    r.setUsername(name);
                    r.setPassword(pw);
                    res = LoginController.refereeLogin(r);
                    view = "views/refCampo.fxml";
                    if(!res){
                        LoginController.closeConn(3);
                    }
                }
                default -> {
                    PlayerBean p = new PlayerBean();
                    p.setUsername(name);
                    p.setPassword(pw);
                    res = LoginController.playerLogin(p);
                    view = "views/userHomeMap.fxml";
                    if(!res){
                        LoginController.closeConn(1);
                    }
                }

            }
            if (res) {
                this.toView(view, name);
            } else {
                err.setContentText("Credenziali errate");
                err.show();
            }
        }
    }

    public void toRegister(ActionEvent e) throws IOException {
        this.toView("views/register.fxml");
    }
}
