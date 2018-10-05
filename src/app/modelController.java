package app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.Observable;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

//import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

import static app.Main.primaryStage;

public class modelController {

    ObservableList option = FXCollections.observableArrayList();

    public class model {
        public model(int modelid, String modelno, String modelname, String warranty, int hsn, float gst) {
            super();
            this.modelid = new SimpleIntegerProperty(modelid);
            this.modelno = new SimpleStringProperty(modelno);
            this.modelname = new SimpleStringProperty(modelname);
            this.warranty = new SimpleStringProperty(warranty);
            this.hsn = new SimpleIntegerProperty(hsn);
            this.gst = new SimpleFloatProperty(gst);
        }

        public model(){
            super();
        }

        private SimpleIntegerProperty modelid;
        private SimpleStringProperty  modelno;
        private SimpleStringProperty  modelname;
        private SimpleStringProperty  warranty;
        private SimpleIntegerProperty hsn;
        private SimpleFloatProperty   gst;



        public int getModelid() {
            return modelid.get();
        }

        public SimpleIntegerProperty modelidProperty() {
            return modelid;
        }

        public void setModelid(int modelid) {
            this.modelid.set(modelid);
        }

        public String getModelno() {
            return modelno.get();
        }

        public SimpleStringProperty modelnoProperty() {
            return modelno;
        }

        public void setModelno(String modelno) {
            this.modelno.set(modelno);
        }

        public String getModelname() {
            return modelname.get();
        }

        public SimpleStringProperty modelnameProperty() {
            return modelname;
        }

        public void setModelname(String modelname) {
            this.modelname.set(modelname);
        }

        public String getWarranty() {
            return warranty.get();
        }

        public SimpleStringProperty warrantyProperty() {
            return warranty;
        }

        public void setWarranty(String warranty) {
            this.warranty.set(warranty);
        }

        public int getHsn() {
            return hsn.get();
        }

        public SimpleIntegerProperty hsnProperty() {
            return hsn;
        }

        public void setHsn(int hsn) {
            this.hsn.set(hsn);
        }

        public float getGst() {
            return gst.get();
        }

        public SimpleFloatProperty gstProperty() {
            return gst;
        }

        public void setGst(float gst) {
            this.gst.set(gst);
        }




    }


    Connection conn = Connections.b2csDBConncetion();
    //@FXML
    //Button addBtn = new Button();
    @FXML
    JFXButton addBtn = new JFXButton();

    //@FXML
    //Button refreshBtn = new Button();
    @FXML
    JFXButton refreshBtn = new JFXButton();

    //@FXML
    //Button editBtn = new Button();

    //@FXML
    //Button deleteBtn = new Button();
    @FXML
    JFXButton deleteBtn = new JFXButton();



    //@FXML
    //TextField modelno = new javafx.scene.control.TextField();
    @FXML
    JFXTextField modelno = new JFXTextField();
    String mmodelno = "";

    //@FXML
    //TextField modelname = new TextField();
    @FXML
    JFXTextField modelname = new JFXTextField();
    String mmodelname = "";

    @FXML
    ChoiceBox<String> warranty = new ChoiceBox<>();
    String mwarranty = "";

    //@FXML
    //ComboBox hsn = new ComboBox(option);
    @FXML
    JFXComboBox hsn = new JFXComboBox(option);
    int mhsn = 0;

    //@FXML
    //TextField gst = new TextField();
    @FXML
    JFXTextField gst = new JFXTextField();
    float mgst = 0;

    @FXML
    private JFXButton editBtn = new JFXButton();



    //Table View
    @FXML
    TableView<model> table = new TableView<>();

    @FXML
    TableColumn<model,Integer> colid = new TableColumn<>();

    @FXML
    TableColumn<model,String> colmodelno = new TableColumn<>();

    @FXML
    TableColumn<model,String> colmodelname = new TableColumn<>();

    @FXML
    TableColumn<model,String> colwarranty = new TableColumn<>();

    @FXML
    TableColumn<model,Integer> colhsn = new TableColumn<>();

    @FXML
    TableColumn<model,Float> colgst = new TableColumn<>();


    ObservableList<model> data = FXCollections.observableArrayList();

    /*
    * Startup method
    * */

    @FXML
    public void initialize()throws SQLException{

        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Model Name Empty");
        modelname.getValidators().add(requiredFieldValidator);

        RequiredFieldValidator requiredFieldValidator1 = new RequiredFieldValidator();
        requiredFieldValidator1.setMessage("Serial Number  Empty");
        modelno.getValidators().add(requiredFieldValidator1);




        RequiredFieldValidator requiredFieldValidator4 = new RequiredFieldValidator();
        requiredFieldValidator4.setMessage("GST Empty");
        gst.getValidators().add(requiredFieldValidator4);



        String[] wrty = {"24+24","18+18","12+12","42+12","24+12","30+30","24+12","6+6","48","42","36","30","24","18","12"};

        warranty.getItems().addAll(wrty);

        try {
            String sql1 = "SELECT * FROM hsn";
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(sql1);

            while (rs1.next()){

                option.add(rs1.getString("hsn_code"));
                System.out.println(rs1.getString("hsn_code"));

            }
            stmt1.close();
            rs1.close();
        }catch (SQLException e){
            System.out.println(e);
        }

        hsn.getItems().addAll(option);


/*hsn and gst event handler*/

        hsn.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        System.out.println(newValue);


                try {
                    String sql1 = "SELECT * FROM hsn WHERE hsn_code = '"+newValue+"'";
                    Statement stmt1 = conn.createStatement();
                    ResultSet rs1 = stmt1.executeQuery(sql1);

                    while (rs1.next()){

                        System.out.println(rs1.getString("gst"));
                        gst.setText(rs1.getString("gst"));

                    }
                    stmt1.close();
                    rs1.close();
                }catch (SQLException e){
                    System.out.println(e);
                }


            }

        });

        //Call Refresh

        Refresh();


    }


    public void addModel(ActionEvent event)throws SQLException{
        if(!modelname.getText().equals("")&&!modelno.getText().equals("")&&!gst.getText().equals("")){

            mmodelno = modelno.getText().toString();
            mmodelname = modelname.getText().toString();
            mwarranty = warranty.getSelectionModel().getSelectedItem().toString();
            mhsn=Integer.parseInt(hsn.getSelectionModel().getSelectedItem().toString());
            mgst= Float.parseFloat(gst.getText().toString()) ;

            System.out.println(mmodelno+" "+mmodelname+" "+mwarranty+" "+mgst+" "+mhsn);

            PreparedStatement stmt1 = conn.prepareStatement("SELECT MAX(id) FROM `model_data`");
            ResultSet resultSet = stmt1.executeQuery();

            int n = resultSet.getInt(1)+1;

            String sql = "  INSERT INTO `model_data` (`id` ,`model_no`, `model_name`, `warranty`, `hsn`, `gst`) VALUES (\'"
                    // + mItemsID + "','"
                    + n + "','"+ mmodelno + "','" + mmodelname + "','" + mwarranty + "','" + mhsn + "','" + mgst
                    + "\')";

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            stmt.close();
            resultSet.close();
            modelno.clear();
            modelname.clear();
            //gst.clear();

            Refresh();

        }
        else {
            modelno.validate();
            modelname.validate();
            gst.validate();
        }







    }



    /*
    * Edit Event Handeler
    * */
    public void editItem(ActionEvent actionEvent) throws SQLException, IOException{

        /**/int itemId = table.getSelectionModel().getSelectedItem().getModelid() ;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("modelEditor.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        ((modelEditorController) fxmlLoader.getController()).getDataFrom(itemId);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.show();
        Refresh();

    }



    /*
     * Delete Event Handeler
     * */
    public void deleteModel(ActionEvent event) throws SQLException{

        String sql;
        Statement stmt;
        ResultSet  rs;

        if(table.getSelectionModel().getSelectedItem().getModelid()==0){
            Refresh();
        }else{

            int id = table.getSelectionModel().getSelectedItem().getModelid() ;
            sql = "DELETE FROM model_data WHERE id = " + id;
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

       Refresh();



    }




    /*
     * Refresh Event Handeler
     * */
    public void refreshbtn(ActionEvent event){
        Refresh();
    }


    /*
     * Display model data in Table View
     * */



    void Refresh(){

        data.clear();
        try{
            String sql = "SELECT * FROM model_data ";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next())
            {
                data.add(new model(
                        resultSet.getInt("id"),resultSet.getString("model_no"),resultSet.getString("model_name"),
                        resultSet.getString("warranty"),resultSet.getInt("hsn"),resultSet.getFloat("gst")
                ));
            }
            statement.close();
            resultSet.close();

        } catch (SQLException e){
            System.out.println(e);
        }

        table.setItems(data);
        //collmItemsID.setCellValueFactory(new PropertyValueFactory<items, Integer>("itemId"));
        colid.setCellValueFactory(new PropertyValueFactory<model,Integer>("modelid"));
        colid.setSortType(TableColumn.SortType.ASCENDING);
        colmodelno.setCellValueFactory(new PropertyValueFactory<model,String >("modelno"));
        colmodelname.setCellValueFactory(new PropertyValueFactory<model,String>("modelname"));
        colwarranty.setCellValueFactory(new PropertyValueFactory<model,String>("warranty"));
        colhsn.setCellValueFactory(new PropertyValueFactory<model,Integer>("hsn"));
        colgst.setCellValueFactory(new PropertyValueFactory<model,Float>("gst"));

    }










}
