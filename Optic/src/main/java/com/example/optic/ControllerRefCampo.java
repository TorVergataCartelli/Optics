package com.example.optic;

import com.example.optic.AppControllers.RefCampoController;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.ReportBean;
import com.example.optic.bean.UserBean;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Giornata;
import com.example.optic.entities.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ControllerRefCampo extends GraphicController{
    @FXML
    private Pane id;
    @FXML
    private Label user;
    @FXML
    private Label userType;
    @FXML
    private TextArea desc;
    @FXML
    private Label address;
    @FXML
    private Label prov;
    @FXML
    private Label admin;
    @FXML
    private Label title;
    @FXML
    private Label activity;
    @FXML
    private Label idPlay;
    @FXML
    private Label date;
    @FXML
    private TableView players;
    @FXML
    private Label numGiocatori;
    @FXML
    private TableColumn playerName;
    @FXML
    private TableColumn playerVal;

    @FXML
    private Label selectedPlayer;
    @FXML
    private TextArea reportDesc;

    @Override
    public void setUserVariables(String user) throws Exception {
        this.user.setText(user);
        Admin a = null;
        UserBean bean = new UserBean();
        bean.setUsername(user);
        a = RefCampoController.getAdminFromRef(bean);
        if(a == null){
            Alert err = new Alert(Alert.AlertType.WARNING);
            err.setContentText("Non sei collegato a nessun campo!");
            err.show();
        }else{
            this.title.setText(a.getNomeC());
            this.desc.setText(a.getDescrizioneC());
            this.address.setText(a.getVia());
            this.prov.setText(a.getProvincia());
            this.admin.setText(a.getUsername());
            this.setFirstPlay(admin.getText());
        }
    }

    //setto la prima giornata di gioco disponibile
    public void setFirstPlay(String user) throws Exception {
        Giornata play = null;
        UserBean bean = new UserBean();
        //setto la bean con info dell'admin del campo attualmente visualizzato
        bean.setUsername(user);
        try {
            play = RefCampoController.getFirstPlay(bean);
            //controllo se esiste una giornata da poter mostrare
            if (play != null) {
                //mostro informazioni della giornata
                SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
                idPlay.setText(Integer.toString(play.getIdGiornata()));
                date.setText(date_format.format(play.getData().getTime()));//converto il calendar in un formato di data
                activity.setText(play.getFk_Nome());
                //controllo se la data è disponibile per la prenotazione
                System.out.println("ok");
                this.populatePlayersTable();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //recupero la prossima giornata di gioco disponibile
    public void getNextPlay() throws ParseException {
        GiornataBean playBean = new GiornataBean();
        Giornata play = null;
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        if(!(idPlay.getText().isEmpty())) {
            try {
                Date data = date_format.parse(date.getText());
                Calendar cal = Calendar.getInstance();
                cal.setTime(data);
                playBean.setData(cal);
                playBean.setAdmin(admin.getText());
                play = RefCampoController.getNextPlay(playBean);
                if (play != null) {
                    idPlay.setText(Integer.toString(play.getIdGiornata()));
                    date.setText(date_format.format(play.getData().getTime()));//converto il calendar in un formato di data
                    activity.setText(play.getFk_Nome());
                    this.populatePlayersTable();
                }
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    //recupero la giornata di gioco precedente a quella mostrata
    public void getLastPlay(){
        GiornataBean playBean = new GiornataBean();
        Giornata play = null;
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        if(!(idPlay.getText().isEmpty())) {
            try {
                Date data = date_format.parse(date.getText());
                Calendar cal = Calendar.getInstance();
                cal.setTime(data);
                playBean.setData(cal);
                playBean.setAdmin(admin.getText());
                play = RefCampoController.getLastPlay(playBean);
                if (play != null) {
                    idPlay.setText(Integer.toString(play.getIdGiornata()));
                    date.setText(date_format.format(play.getData().getTime()));//converto il calendar in un formato di data
                    activity.setText(play.getFk_Nome());
                    this.populatePlayersTable();
                }
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void populatePlayersTable() throws IOException {
        GiornataBean playBean = new GiornataBean();
        players.getItems().clear();
        playerName.setCellValueFactory(new PropertyValueFactory<>("username"));
        playerVal.setCellValueFactory(new PropertyValueFactory<>("stelle"));
        Player p = null;
        playBean.setIdPlay(Integer.parseInt(idPlay.getText()));
        ArrayList<Player> list = RefCampoController.getPlayersList(playBean);
        for(int i = 0; i < list.size(); i++) {
            p = list.get(i);
            players.getItems().add(p);
        }
        numGiocatori.setText(Integer.toString(list.size()));
    }

    public void tableview2(MouseEvent event) throws IOException {
        Node node=((Node)event.getTarget()).getParent();
        Player player = (Player) players.getSelectionModel().getSelectedItem();

        //Setto username report
        String username=player.getUsername();
        selectedPlayer.setText("Giocatore : "+username);
    }

    public void report(ActionEvent e) {
        ReportBean rep=new ReportBean();
        rep.setMotivazione(reportDesc.getText());
        String[] giocatore = selectedPlayer.getText().split(" ");
        rep.setPlayer(giocatore[2]);
        rep.setReferee(user.getText());
        try {
            RefCampoController.saveReport(rep);
        } catch (IOException ex) {
            ex.printStackTrace();
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

    public void toLogin(ActionEvent e) throws IOException {
        RefCampoController.closeConn();
        this.toView("views/login.fxml");
    }

    public void toInstagram(){
       // Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome "+urlInstagram.getText()});
    }

    public void toFacebook(){
       // Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome "+urlFacebook.getText()});
    }

    public void toWhatsapp(){

    }
}
