package com.example.optic;

import com.example.optic.bean.AdminBean;
import com.example.optic.entities.Campo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class GraphicController {
    @FXML
    private Pane id;
    @FXML
    private Label user;

    private double xOffset = 0;
    private double yOffset = 0;

    public void exitButton(ActionEvent e){
        Platform.exit();
    }
    public void reduceButton(ActionEvent e){
        Stage obj = (Stage) id.getScene().getWindow();
        obj.setIconified(true);
    }
    public void drag(MouseEvent e){
        Stage obj = (Stage) id.getScene().getWindow();
        obj.setX(e.getScreenX() + xOffset);
        obj.setY(e.getScreenY() + yOffset);
    }

    public void setUserVariables(String username) throws Exception {}

    public void toView(String view) throws IOException {
        Stage obj = (Stage) id.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource(view));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 720);
        scene.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                obj.setX(event.getScreenX() - xOffset);
                obj.setY(event.getScreenY() - yOffset);
            }
        });
        scene.setFill(Color.TRANSPARENT);
        obj.setScene(scene);
        obj.show();
    }

    public void toView(String view,String user, String viewer) throws Exception {
        Stage obj = (Stage) id.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource(view));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 720);
        GraphicController controller = fxmlLoader.getController();
        String usr=user+" "+viewer;
        controller.setUserVariables(usr);
        scene.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                obj.setX(event.getScreenX() - xOffset);
                obj.setY(event.getScreenY() - yOffset);
            }
        });
        scene.setFill(Color.TRANSPARENT);
        obj.setScene(scene);
        obj.show();
    }

    public void toView(String view, String usr) throws Exception {
        Stage obj = (Stage) id.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource(view));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 720);
        GraphicController controller = fxmlLoader.getController();
        controller.setUserVariables(usr);
        scene.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                obj.setX(event.getScreenX() - xOffset);
                obj.setY(event.getScreenY() - yOffset);
            }
        });
        scene.setFill(Color.TRANSPARENT);
        obj.setScene(scene);
        obj.show();
    }

    public void toView(String view, String usr, AdminBean campo)throws Exception{
        Stage obj = (Stage) id.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource(view));

        Scene scene = new Scene(fxmlLoader.load(), 1200, 720);
        GraphicController controller = fxmlLoader.getController();

        usr = usr+"/"+campo.getNomeCampo();
        controller.setUserVariables(usr);

        scene.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                obj.setX(event.getScreenX() - xOffset);
                obj.setY(event.getScreenY() - yOffset);
            }
        });
        scene.setFill(Color.TRANSPARENT);
        obj.setScene(scene);
        obj.show();
    }
}
