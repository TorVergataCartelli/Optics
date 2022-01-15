package com.example.optic;

import com.example.optic.AppControllers.UserProfileAppController;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.UserBean;
import com.example.optic.bean.ValutazioneBean;
import com.example.optic.entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ControllerUserViewProfile extends GraphicController{
    //stelle recensione
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

    //stelle valutazione profilo
    @FXML
    private Label star11;
    @FXML
    private Label star22;
    @FXML
    private Label star33;
    @FXML
    private Label star44;
    @FXML
    private Label star55;

    @FXML
    private Label nVal;
    @FXML
    private TextArea description;
    @FXML
    private TextField urlFacebook;
    @FXML
    private TextField urlInstagram;

    @FXML
    private Label user;
    @FXML
    private  Label profile;
    @FXML
    private ListView reviews;
    @FXML
    private TableView partite;
    @FXML
    private TableColumn date;
    @FXML
    private TableColumn playground;

    //Label per acquisizione della recensione
    @FXML
    private  TextArea desc;

    @Override
    public void setUserVariables(String user) throws Exception {
        String [] app = user.split(" ");
        String profile=app[0];
        String viewer=app[1];

        this.profile.setText("Profile: "+profile);
        this.user.setText(viewer);
        Player p = null;
        try {
            PlayerBean player = new PlayerBean();
            player.setUsername(profile);
            this.populateReviewList(profile);
            this.populateGamesTable();
            p = UserProfileAppController.getPlayer(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(p != null) {
            //descrizione
            this.description.setText(p.getDescrizione());
            //se il giocatore è valutato positivamente, nome giallo
            if(p.getStato().equals("positivo")){
                //giocatore positivo
            }
            this.urlInstagram.setText(p.getIg());;
            this.urlFacebook.setText(p.getFb());;
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
    public void populateGamesTable(){
        UserBean player = new UserBean();
        String[] app = profile.getText().split(" ");
        String username=app[1];
        player.setUsername(username);
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
        }
    }

    public void instagram() throws IOException {
        if(!urlInstagram.getText().isEmpty()) {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + this.urlInstagram.getText()});
        }
    }

    private void setStars(int stars){
        switch (stars){
            case 1: star11.setVisible(true);
                break;
            case 2: star11.setVisible(true);
                star22.setVisible(true);
                break;
            case 3: star11.setVisible(true);
                star22.setVisible(true);
                star33.setVisible(true);
                break;
            case 4: star11.setVisible(true);
                star22.setVisible(true);
                star33.setVisible(true);
                star44.setVisible(true);
                break;
            case 5: star11.setVisible(true);
                star22.setVisible(true);
                star33.setVisible(true);
                star44.setVisible(true);
                star55.setVisible(true);
                break;
            default:;
        }
    }

    public void reportList(){
        try {
            String[] app = profile.getText().split(" ");
            String username=app[1];
            Stage list = new Stage();
            Stage obj = (Stage) id.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource("views/reportList.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 580);
            GraphicController controller = fxmlLoader.getController();
            controller.setUserVariables(username);
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

    public void review(ActionEvent e){

        String description=desc.getText();
        int starN;
        String givento=profile.getText();
        String giver=user.getText();

        if(star1.getTextFill().equals(Color.rgb(229,190,51)) && star2.getTextFill().equals(Color.rgb(28,28,28))){
            starN=1;
        }else if (star2.getTextFill().equals(Color.rgb(229,190,51)) && star3.getTextFill().equals(Color.rgb(28,28,28))){
            starN=2;
        } else if (star3.getTextFill().equals(Color.rgb(229,190,51)) && star4.getTextFill().equals(Color.rgb(28,28,28))) {
            starN=3;
        } else if (star4.getTextFill().equals(Color.rgb(229,190,51)) && star5.getTextFill().equals(Color.rgb(28,28,28))) {
            starN=4;
        }else{
            starN=5;
        }
        String array[];
        array=givento.split(" ");

        ValutazioneBean val= new ValutazioneBean();
        val.setUsernameP1(giver);
        val.setRecensione(description);
        val.setStelle(starN);
        val.setRiceve(array[1]);

        UserProfileAppController.saveReview(val);
        reviews.getItems().clear();
        try {
            String []ar;
            ar=profile.getText().split(" ");
            populateReviewList(ar[1]);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //forse si puo mettere su graphic controller
    public void starEnter(MouseEvent e){
        Label l = (Label)e.getSource();
        String id = l.getId();
        int starN = Integer.parseInt(id.substring(id.length() - 1));
        switch(starN){
            case 2: star1.setTextFill(Color.rgb(229,190,51));
                star2.setTextFill(Color.rgb(229,190,51));
                star3.setTextFill(Color.rgb(28,28,28));
                star4.setTextFill(Color.rgb(28,28,28));
                star5.setTextFill(Color.rgb(28,28,28));
                break;
            case 3: star1.setTextFill(Color.rgb(229,190,51));
                star2.setTextFill(Color.rgb(229,190,51));
                star3.setTextFill(Color.rgb(229,190,51));
                star4.setTextFill(Color.rgb(28,28,28));
                star5.setTextFill(Color.rgb(28,28,28));
                break;
            case 4: star1.setTextFill(Color.rgb(229,190,51));
                star2.setTextFill(Color.rgb(229,190,51));
                star3.setTextFill(Color.rgb(229,190,51));
                star4.setTextFill(Color.rgb(229,190,51));
                star5.setTextFill(Color.rgb(28,28,28));
                break;
            case 5: star1.setTextFill(Color.rgb(229,190,51));
                star2.setTextFill(Color.rgb(229,190,51));
                star3.setTextFill(Color.rgb(229,190,51));
                star4.setTextFill(Color.rgb(229,190,51));
                star5.setTextFill(Color.rgb(229,190,51));
                break;
            default:star1.setTextFill(Color.rgb(229,190,51));
                star2.setTextFill(Color.rgb(28,28,28));
                star3.setTextFill(Color.rgb(28,28,28));
                star4.setTextFill(Color.rgb(28,28,28));
                star5.setTextFill(Color.rgb(28,28,28));
        }
    }
    public void starExit(MouseEvent e){
        Label l = (Label)e.getSource();
        String id = l.getId();
        int starN = Integer.parseInt(id.substring(id.length() - 1));
        switch(starN){
            case 1: star1.setTextFill(Color.rgb(229,190,51));
                star2.setTextFill(Color.rgb(28,28,28));
                star3.setTextFill(Color.rgb(28,28,28));
                star4.setTextFill(Color.rgb(28,28,28));
                star5.setTextFill(Color.rgb(28,28,28));
                break;
            case 2: star3.setTextFill(Color.rgb(28,28,28));
                star4.setTextFill(Color.rgb(28,28,28));
                star5.setTextFill(Color.rgb(28,28,28));
                break;
            case 3: star4.setTextFill(Color.rgb(28,28,28));
                star5.setTextFill(Color.rgb(28,28,28));
                break;
            case 4: star5.setTextFill(Color.rgb(28,28,28));
                break;
            default:;
        }
    }


    public void toHome(ActionEvent e) throws Exception {
        this.toView("views/userHomeMap.fxml",user.getText());
    }
}
