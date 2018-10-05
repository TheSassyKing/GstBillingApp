package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainController {

    @FXML
    AnchorPane anchorPanel1 = new AnchorPane();

    public void exit(ActionEvent actionEvent){

        System.exit(0);

    }

    public void ButtonBilling(ActionEvent event) {
        //Main.stage.close();
        anchorPanel1.getChildren().clear();


        try {
            anchorPanel1.getChildren().add(FXMLLoader.load(getClass().getResource("Billing.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Billing page");
    }

    public void ButtonItem(ActionEvent event) {

        anchorPanel1.getChildren().clear();

        try {
            anchorPanel1.getChildren().add(FXMLLoader.load(getClass().getResource("Item.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Item page");
    }

    public void ButtonReport(ActionEvent event) {

        anchorPanel1.getChildren().clear();
        try {
            anchorPanel1.getChildren().add(FXMLLoader.load(getClass().getResource("Report.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Report page");
    }

    public void ButtonStock(ActionEvent event) {

        anchorPanel1.getChildren().clear();
        try {
            anchorPanel1.getChildren().add(FXMLLoader.load(getClass().getResource("Stock.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Stockc page");
    }

    public void ButtonCustomer(ActionEvent event) {

        anchorPanel1.getChildren().clear();

        try {
            anchorPanel1.getChildren().add(FXMLLoader.load(getClass().getResource("Customer.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("customer page");
    }

    public void ButtonModel(ActionEvent event) {

        anchorPanel1.getChildren().clear();

        try {
            anchorPanel1.getChildren().add(FXMLLoader.load(getClass().getResource("AddModel.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("customer page");
    }

}
