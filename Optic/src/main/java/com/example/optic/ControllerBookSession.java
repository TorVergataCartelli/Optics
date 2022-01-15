package com.example.optic;

import com.example.optic.AppControllers.BookSessionAppController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.PlayerBean;
import com.example.optic.entities.Admin;
//import com.example.optic.entities.Campo;
import com.example.optic.entities.Campo;
import com.example.optic.entities.Event;
import com.example.optic.entities.ValutazionePlayer;
import com.mysql.cj.xdevapi.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerBookSession extends GraphicController {
    @FXML
    private Label user;
    @FXML
    private TableView table;
    @FXML
    private TableColumn NomeC;
    @FXML
    private TableColumn Provincia;

    @Override
    public void setUserVariables(String user){
        this.user.setText(user);
        populateCampiTable(user);
    }
    public void toProfile(ActionEvent e) throws Exception {
        this.toView("views/userProfile.fxml",user.getText());
    }
    public void toHome(ActionEvent e) throws IOException {
        this.toView("views/userHomeMap.fxml");
    }
    public void toLogin(ActionEvent e) throws IOException {
        BookSessionAppController.closeConn();
        this.toView("views/login.fxml");
    }

    public void tableview(MouseEvent e){
        try {
            Node node=((Node)e.getTarget()).getParent();
            AdminBean campo=(AdminBean) table.getSelectionModel().getSelectedItem();
            toView("views/userPgPage.fxml",user.getText(),campo);
        }
        catch(Exception z){
            z.printStackTrace();
        }
    }

    public void populateCampiTable(String user) {
        //da finire
        PlayerBean player = new PlayerBean();
        player.setUsername(user);

        try {
            ArrayList<AdminBean> lista = BookSessionAppController.getCampi();
            NomeC.setCellValueFactory(new PropertyValueFactory<>("nomeCampo"));
            Provincia.setCellValueFactory(new PropertyValueFactory<>("provincia"));
            boolean list = true;
            int k=lista.size();
            int i=0;

            AdminBean elem = new AdminBean();

            while(i<k){
                elem=lista.get(i);
                table.getItems().add(elem);
                i++;
            }
        }catch(Exception e){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Errore acquisizione campi");
            err.show();
        }


    }
}