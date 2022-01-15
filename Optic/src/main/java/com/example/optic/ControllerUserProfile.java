package com.example.optic;

import com.example.optic.AppControllers.UserProfileAppController;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.UserBean;
import com.example.optic.entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ControllerUserProfile extends GraphicController{
    @FXML
    private Pane id;
    @FXML
    private Label star1;
    @FXML
    private Label star2;
    @FXML
    private Label star3;
    @FXML
    private Label star4;
    @FXML
    private Label star5;
    @FXML
    private Label nVal;
    @FXML
    private TextArea description;
    @FXML
    private TextField urlFacebook;
    @FXML
    private TextField urlInstagram;
    @FXML
    private GridPane modifica;
    @FXML
    private Button salva;
    @FXML
    private Label user;
    @FXML
    private ListView reviews;
    @FXML
    private TableView partite;
    @FXML
    private TableColumn date;
    @FXML
    private TableColumn playground;
    @FXML
    private Label userType;

    @Override
    public void setUserVariables(String user) throws Exception {
        this.user.setText(user);
        Player p = null;
        try {
            PlayerBean player = new PlayerBean();
            player.setUsername(user);
            this.populateReviewList(user);
            this.populateGamesTable(user);
            p = UserProfileAppController.getPlayer(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(p != null) {
            //descrizione
            this.description.setText(p.getDescrizione());
            //se il giocatore è valutato positivamente, nome giallo
            if(p.getStato().equals("positivo")){
                //profilo plus
            }
            this.urlInstagram.setText(p.getIg());;
            this.urlFacebook.setText(p.getFb());;
        }
    }

    public void eventList(ActionEvent e) throws Exception {
        try {
            Stage list = new Stage();
            Stage obj = (Stage) id.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource("views/eventList.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 580);
            GraphicController controller = fxmlLoader.getController();
            controller.setUserVariables(userType.getText());
            scene.setFill(Color.TRANSPARENT);
            list.setResizable(false);
            list.initOwner(obj);
            list.initModality(Modality.APPLICATION_MODAL);
            list.initStyle(StageStyle.TRANSPARENT);
            list.setScene(scene);
            list.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void reportList(ActionEvent e) throws Exception {
        try {
            Stage list = new Stage();
            Stage obj = (Stage) id.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource("views/reportList.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 580);
            GraphicController controller = fxmlLoader.getController();
            controller.setUserVariables(user.getText());
            scene.setFill(Color.TRANSPARENT);
            list.setResizable(false);
            list.initOwner(obj);
            list.initModality(Modality.APPLICATION_MODAL);
            list.initStyle(StageStyle.TRANSPARENT);
            list.setScene(scene);
            list.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //popolo la lista di review e utilizzo i dati delle valutazioni per il contatore
    public void populateReviewList(String user) throws IOException {
        PlayerBean player = new PlayerBean();
        int numVal = 0;
        int mediaVal = 0;
        int stars = 0;
        player.setUsername(user);
        ArrayList<Valutazione> list = UserProfileAppController.getReviewList(player);
        for(int i = 0; i < list.size(); i++) {
            ValutazionePlayer val = new ValutazionePlayer(list.get(i).getFk_UsernameP1(), list.get(i).getDescrizione()); //passo chi fa la segnalazione e la descrizione
            numVal++;
            mediaVal += list.get(i).getStelle();
            reviews.getItems().add(val.getDescrizione());
        }
        if(numVal > 0) {
            stars = mediaVal / numVal;
            if (stars > 0) {
                //coloro le stelle in base alla valutazione
                this.setStars(stars);
            }
        }
        nVal.setText(Integer.toString(numVal));
    }

    //popolo la tabella con lo storico delle partite del player
    public void populateGamesTable(String user){
        UserBean player = new UserBean();
        player.setUsername(user);
        date.setCellValueFactory(new PropertyValueFactory<>("dataString"));
        playground.setCellValueFactory(new PropertyValueFactory<>("nomeC"));
        ArrayList<Giornata> list = UserProfileAppController.getRecentPlayList(player);
        Giornata g = null;
        for(int i = 0;i < list.size(); i++){
            g = list.get(i);
            partite.getItems().add(g);
        }
    }

    public void facebook() throws IOException {
        if(!urlFacebook.getText().isEmpty()) {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + this.urlFacebook.getText()});
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Nessun profilo facebook inserito");
        }
    }

    public void instagram() throws IOException {
        if(!urlInstagram.getText().isEmpty()) {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + this.urlInstagram.getText()});
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Nessun profilo instagram inserito");
        }
    }

    private void setStars(int stars){
        switch (stars){
            case 1: star1.setVisible(true);
                    break;
            case 2: star1.setVisible(true);
                    star2.setVisible(true);
                    break;
            case 3: star1.setVisible(true);
                    star2.setVisible(true);
                    star3.setVisible(true);
                    break;
            case 4: star1.setVisible(true);
                    star2.setVisible(true);
                    star3.setVisible(true);
                    star4.setVisible(true);
                    break;
            case 5: star1.setVisible(true);
                    star2.setVisible(true);
                    star3.setVisible(true);
                    star4.setVisible(true);
                    star5.setVisible(true);
                    break;
            default:
        }
    }

    public void modify(){
        //Rendo visibile il tasto aggiorna e modificabile la grid di connessione ai social e la description
        description.setEditable(true);
        description.setStyle("-fx-border-color: rgb(229,190,51)");
        urlFacebook.setEditable(true);
        urlInstagram.setEditable(true);
        modifica.setVisible(true);
        salva.setVisible(true);
    }

    public void save(){
        Alert err = new Alert(Alert.AlertType.ERROR);
        String desc = description.getText();
        String fb = urlFacebook.getText();
        String ig = urlInstagram.getText();
        boolean res = true;
        /*if(fb.getText() != null) {
            if(!(fb.getText().isEmpty())) {
                if (!(fb.getText().contains("https://www.facebook.com"))) {
        */

        if(desc.length() > 200){
            res = false;
            err.setContentText("La descrizione supera il limite massimo di 200 caratteri: "+desc.length());
            err.show();
        }if(urlFacebook.getText() != null) {
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
        if(res){
            PlayerBean p = new PlayerBean();
            p.setUsername(user.getText());
            p.setDescrizione(desc);
            p.setFb(fb);
            p.setIg(ig);
            UserProfileAppController.setInfo(p);

            description.setEditable(false);
            description.setStyle("");
            urlFacebook.setEditable(false);
            urlInstagram.setEditable(false);
            modifica.setVisible(false);
            salva.setVisible(false);
        }
    }

    public void toHome(ActionEvent e) throws Exception {
        this.toView("views/userHomeMap.fxml",user.getText());
    }
}