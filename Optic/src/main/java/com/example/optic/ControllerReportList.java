package com.example.optic;

import com.example.optic.AppControllers.UserProfileAppController;
import com.example.optic.bean.ReportBean;
import com.example.optic.entities.Event;
import com.example.optic.entities.Report;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerReportList extends GraphicController {
    @FXML
    private Pane id2;
    @FXML
    private ListView reports;

    @Override
    public void setUserVariables(String user) throws IOException {
        ArrayList<ReportBean> list = new ArrayList<ReportBean>();
        list = UserProfileAppController.getReportList(user);
        this.setList(list);
    }


    public void setList(ArrayList<ReportBean> list) throws IOException {
        this.reports.getItems().clear();
        for(int i = 0; i < list.size();i++){
            reports.getItems().add(list.get(i).getFormattedText());
        }
        reports.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                ListCell<String> cell = new ListCell<String>() {

                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            Text text = new Text(item);
                            text.wrappingWidthProperty().bind(reports.widthProperty().subtract(40));
                            setGraphic(text);
                        }
                    }
                };
                return cell;
            }
        });
    }

    public void exitListButton(ActionEvent e){
        Stage list = (Stage) id2.getScene().getWindow();
        list.close();
    }
}
