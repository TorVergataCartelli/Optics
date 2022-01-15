package com.example.optic;

import com.example.optic.AppControllers.ModPGPageAppController;
import com.example.optic.bean.AdminBean;
import com.example.optic.entities.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControllerSocialModPG extends GraphicController{
    @FXML
    private Pane idS;
    @FXML
    private Label user;
    @FXML
    private TextField urlFacebook;
    @FXML
    private TextField urlInstagram;
    @FXML
    private TextField numWhatsapp;

    @Override
    public void setUserVariables(String user){
        this.user.setText(user);
        Admin a = null;
        AdminBean bean = new AdminBean();
        bean.setUsername(user);
        a = ModPGPageAppController.getAdmin(bean);
        if(a != null){
            urlFacebook.setText(a.getFb());
            urlInstagram.setText(a.getIg());
            numWhatsapp.setText(a.getWa());
        }
    }

    public void exitSocial(ActionEvent e) {
        Stage social = (Stage)this.idS.getScene().getWindow();
        social.close();
    }

    //CONVENZIONE per quanto riguarda i social con exit esco senza salvare con conferma esco e salvo
    public  void conferma(){
        boolean res = true;
        Alert err = new Alert(Alert.AlertType.ERROR);
        AdminBean bean = new AdminBean();
        bean.setUsername(user.getText());
        bean.setFb(urlFacebook.getText());
        bean.setIg(urlInstagram.getText());
        bean.setWa(numWhatsapp.getText());
        //controllo se gli url sono validi
        if(urlFacebook.getText() != null) {
            if(!(urlFacebook.getText().isEmpty())) {
                if (!(urlFacebook.getText().contains("https://www.facebook.com"))) {
                    res = false;
                    err.setContentText("Url facebook non valido.");
                    err.show();
                } else if (urlFacebook.getText().length() > 200) {
                    res = false;
                    err.setContentText("Url facebook troppo lungo.");
                    err.show();
                }
            }
        }
        if(urlInstagram.getText() != null) {
            if(!(urlInstagram.getText().isEmpty())) {
                if (!(urlInstagram.getText().contains("https://www.instagram.com"))) {
                    res = false;
                    err.setContentText("Url instagram non valido.");
                    err.show();
                } else if (urlInstagram.getText().length() > 200) {
                    res = false;
                    err.setContentText("Url instagram troppo lungo.");
                    err.show();
                }
            }
        }
        if (numWhatsapp.getText() != null){
            if(!(numWhatsapp.getText().isEmpty())) {
                if (numWhatsapp.getText().length() != 10) {
                    res = false;
                    err.setContentText("Numero di telefono non valido");
                    err.show();
                }
            }
        }
        if(res){
            ModPGPageAppController.setAdminSocial(bean);
            ActionEvent event = new ActionEvent();
            exitSocial(event);
        }
    }

}
