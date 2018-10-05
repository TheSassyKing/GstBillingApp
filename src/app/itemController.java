package app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.dbutils.DbUtils;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.sql.*;
import java.util.Collections;

import static app.Main.primaryStage;

public class itemController {

    ObservableList option = FXCollections.observableArrayList();

    public class item {

        private SimpleIntegerProperty itemid;
        private SimpleStringProperty itembarcode;
        private SimpleStringProperty modelno;
        private SimpleStringProperty  modelname;
        private SimpleStringProperty  warranty;
        private SimpleIntegerProperty hsn;
        private SimpleFloatProperty gst;
        private SimpleFloatProperty costprice;
        //private SimpleFloatProperty sellingprice;

        public item(int itemid, String itembarcode, String modelno,
                         String modelname, String warranty,int hsn,
                             float gst, float costprice) {

            super();
            this.itemid = new SimpleIntegerProperty(itemid) ;
            this.itembarcode = new SimpleStringProperty(itembarcode) ;
            this.modelno = new SimpleStringProperty(modelno) ;
            this.modelname = new SimpleStringProperty(modelname);
            this.warranty = new SimpleStringProperty(warranty);
            this.hsn = new SimpleIntegerProperty(hsn);
            this.gst = new SimpleFloatProperty(gst);
            this.costprice = new SimpleFloatProperty(costprice);
            //this.sellingprice = new SimpleFloatProperty(sellingprice);
        }

        public int getItemid() {
            return itemid.get();
        }

        public SimpleIntegerProperty itemidProperty() {
            return itemid;
        }

        public void setItemid(int itemid) {
            this.itemid.set(itemid);
        }

        public String getItembarcode() {
            return itembarcode.get();
        }

        public SimpleStringProperty itembarcodeProperty() {
            return itembarcode;
        }

        public void setItembarcode(String itembarcode) {
            this.itembarcode.set(itembarcode);
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

        public float getCostprice() {
            return costprice.get();
        }

        public SimpleFloatProperty costpriceProperty() {
            return costprice;
        }

        public void setCostprice(float costprice) {
            this.costprice.set(costprice);
        }

        /*public float getSellingprice() {
            return sellingprice.get();


        public SimpleFloatProperty sellingpriceProperty() {
            return sellingprice;
        }

        public void setSellingprice(float sellingprice) {
            this.sellingprice.set(sellingprice);
        }
        }*/
    }

    Connection conn = Connections.b2csDBConncetion();

    //Button

    //@FXML
    //Button addbtn = new Button();
    @FXML
    JFXButton addbtn = new JFXButton();

    //@FXML
    //Button refreshbtn = new Button();
    @FXML
    JFXButton refreshbtn = new JFXButton();

    //@FXML
    //Button editbtn = new Button();

    //@FXML
    //Button deletebtn = new Button();
    @FXML
    JFXButton deletebtn = new JFXButton();

    @FXML
    private JFXButton editBtn = new JFXButton();


    //TAble view

    @FXML
    TableView<item> table = new TableView();

    @FXML
    TableColumn<item,Integer> colid = new TableColumn();

    @FXML
    TableColumn<item,String> colbarcode = new TableColumn<>();

    @FXML
    TableColumn<item,String> colmodelno =  new TableColumn<>();

    @FXML
    TableColumn<item,String> colmodelname =  new TableColumn<>();

    @FXML
    TableColumn<item,String> colwarranty = new TableColumn<>();

    @FXML
    TableColumn<item,Integer> colhsn = new TableColumn<>();

    @FXML
    TableColumn<item,Float> colgst = new TableColumn<>();

    @FXML
    TableColumn<item,Float> colcostprc = new TableColumn<>();

    //@FXML
    //TableColumn<item,Float> colsellingprc = new TableColumn<>();

    ObservableList<item> data = FXCollections.observableArrayList();


   // @FXML
    //TextField barcode = new javafx.scene.control.TextField();
    @FXML
    JFXTextField barcode = new JFXTextField();
    String mbarcode = "";

    //@FXML
    //ComboBox modelno = new ComboBox(option);
    @FXML
    JFXComboBox modelno = new JFXComboBox();
    String mmodelno = "";

    //@FXML
    //TextField modelname = new TextField();
    @FXML
    JFXTextField modelname  = new JFXTextField();
    String mmodelname = "";

    //@FXML
    //TextField warranty = new TextField();
    @FXML
    JFXTextField warranty  = new JFXTextField();
    String mwarranty = "";

    //@FXML
    //TextField hsn = new TextField();
    @FXML
    JFXTextField hsn  = new JFXTextField();
    int mhsn = 0;

    //@FXML
    //TextField gst = new TextField();
    @FXML
    JFXTextField gst  = new JFXTextField();
    float mgst = 0;

    //@FXML
    //TextField sellingprice = new TextField();
   // float msellingprice = 0;

    //@FXML
    //TextField costprice = new TextField();
    @FXML
    JFXTextField costprice  = new JFXTextField();
    float mcostprice = 0;

    @FXML
    public void initialize()throws SQLException{


        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Model Name Empty");
        modelname.getValidators().add(requiredFieldValidator);

        RequiredFieldValidator requiredFieldValidator1 = new RequiredFieldValidator();
        requiredFieldValidator1.setMessage("Barcode  Empty");
        barcode.getValidators().add(requiredFieldValidator1);


        RequiredFieldValidator requiredFieldValidator2 = new RequiredFieldValidator();
        requiredFieldValidator2.setMessage("Warranty Empty");
        warranty.getValidators().add(requiredFieldValidator2);

        RequiredFieldValidator requiredFieldValidator3 = new RequiredFieldValidator();
        requiredFieldValidator3.setMessage("HSN  Empty");
        hsn.getValidators().add(requiredFieldValidator3);


        RequiredFieldValidator requiredFieldValidator4 = new RequiredFieldValidator();
        requiredFieldValidator4.setMessage("GST Empty");
        gst.getValidators().add(requiredFieldValidator4);

        RequiredFieldValidator requiredFieldValidator5 = new RequiredFieldValidator();
        requiredFieldValidator5.setMessage("Cost Price Empty");
        costprice.getValidators().add(requiredFieldValidator5);



        try {
            String sql1 = "SELECT * FROM model_data";
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(sql1);

            while (rs1.next()){

                option.add(rs1.getString("model_no"));
                //System.out.println(rs1.getString("model_no"));

            }
            stmt1.close();
            rs1.close();
        }catch (SQLException e){
            System.out.println(e);
        }

        Collections.sort(option);

        modelno.setVisibleRowCount(10);

        modelno.getItems().addAll(option);


        modelno.valueProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //System.out.println(newValue);

                try {
                    String sql1 = "SELECT * FROM model_data WHERE model_no = '"+newValue+"'";
                    Statement stmt1 = conn.createStatement();
                    ResultSet rs1 = stmt1.executeQuery(sql1);

                    while (rs1.next()){
                       // System.out.println(rs1.getString("model_name"));
                        modelname.setText(rs1.getString("model_name"));
                       // System.out.println(rs1.getString("warranty"));
                        warranty.setText(rs1.getString("warranty"));
                       // System.out.println(rs1.getString("hsn"));
                        hsn.setText(rs1.getString("hsn"));
                       // System.out.println(rs1.getString("gst"));
                        gst.setText(rs1.getString("gst"));

                    }
                    stmt1.close();
                    rs1.close();
                }catch (SQLException e){
                    System.out.println(e);
                }
            }
        });

        modelno.setEditable(true);
        TextFields.bindAutoCompletion(modelno.getEditor(), modelno.getItems());

        Refresh();

    }


    public void addItem(ActionEvent actionEvent) throws SQLException{


      /*  mmodelno = modelno.getText().toString();
        mmodelname = modelname.getText().toString();
        mwarranty = warranty.getSelectionModel().getSelectedItem().toString();
        mhsn=Integer.parseInt(hsn.getSelectionModel().getSelectedItem().toString());
        mgst= Float.parseFloat(gst.getText().toString()) ;
        */

      if(!modelname.getText().equals("")&&!barcode.getText().equals("")&&!warranty.getText().equals("")&&!hsn.getText().equals("")&&!gst.getText().equals("")&&!costprice.getText().equals("")){


          mbarcode = barcode.getText().toString();
          mmodelno = modelno.getSelectionModel().getSelectedItem().toString();
          mmodelname = modelname.getText().toString();
          mwarranty = warranty.getText().toString();
          mhsn = Integer.parseInt(hsn.getText().toString()) ;
          mgst = Float.parseFloat(gst.getText().toString());
          mcostprice = Float.parseFloat(costprice.getText().toString());
          //msellingprice = Float.parseFloat(sellingprice.getText().toString());

          PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(id) FROM `item`");
          ResultSet resultSet = stmt2.executeQuery();

          int n = resultSet.getInt(1)+1;


          //System.out.println(mmodelno+" "+mmodelname+" "+mwarranty+" "+mgst+" "+mhsn);

          String sql = "  INSERT INTO `item` (`id`,`barcode`, `model_no`, `model_name`, `warranty`, `hsn`, `gst`, `cost_price`) VALUES (\'"
                  // + mItemsID + "','"
                  + n + "','"+ mbarcode + "','"+ mmodelno + "','" + mmodelname + "','" + mwarranty + "','" + mhsn + "','" + mgst+ "','" + mcostprice
                  + "\')";

          Statement stmt = conn.createStatement();
          stmt.executeUpdate(sql);

          String sql1 = "update model_data set quantity = quantity + 1 where model_no = ?";
          //String sql1 = "UPDATE model_data SET model_data.quantity = model_data.quantity + 1 WHERE model_data.model_no = '" + modelno+"'";
          PreparedStatement stmt1 = conn.prepareStatement(sql1);
          stmt1.setString(1,mmodelno);
          stmt1.executeUpdate();

          stmt.close();
          stmt1.close();
          resultSet.close();

          Refresh();
      }else{
          modelname.validate();
          barcode.validate();
          warranty.validate();
          hsn.validate();
          gst.validate();
          costprice.validate();
      }

    }

    public void editItem(ActionEvent actionEvent) throws SQLException, IOException{

        int itemId = table.getSelectionModel().getSelectedItem().getItemid() ;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editItemDialogBox.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        ((editDialogController) fxmlLoader.getController()).getDataFrom(itemId);
        stage.initModality(Modality.WINDOW_MODAL);

        stage.initOwner(primaryStage);
        stage.show();
        Refresh();


    }
    public void deleteItem(ActionEvent actionEvent)throws SQLException{

        String sql;
        Statement stmt;
        ResultSet  rs;

        if(table.getSelectionModel().getSelectedItem().getItemid()==0){
            Refresh();
        }else{

            int id = table.getSelectionModel().getSelectedItem().getItemid() ;
            sql = "DELETE FROM item WHERE id = " + id;
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();

            DbUtils.closeQuietly(stmt);
            //DbUtils.closeQuietly(ps);
           // DbUtils.closeQuietly(conn);

        }

        Refresh();


    }

    public void refreshItem(ActionEvent actionEvent) throws SQLException {
        Refresh();

    }

    @FXML
    void editItems(ActionEvent event) {

    }

     public void Refresh() throws SQLException {

        //System.out.println("tabel data");

        data.clear();
        String sql = "SELECT * FROM item ";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

            //System.out.println("tabel data");

            while (resultSet.next())
            {
                //System.out.println(resultSet.getFloat("cost_price"));
                data.add(new item(
                        resultSet.getInt("id"),resultSet.getString("barcode"),resultSet.getString("model_no"),resultSet.getString("model_name"),
                        resultSet.getString("warranty"),resultSet.getInt("hsn"),resultSet.getFloat("gst"),
                        resultSet.getFloat("cost_price")
                ));
            }


        modelno.getSelectionModel().clearSelection();
        barcode.clear();
        modelname.clear();
        warranty.clear();
        hsn.clear();
        gst.clear();
        costprice.clear();
        table.setItems(data);
        //collmItemsID.setCellValueFactory(new PropertyValueFactory<items, Integer>("itemId"));
        colid.setCellValueFactory(new PropertyValueFactory<item,Integer>("itemid"));
        colid.setSortType(TableColumn.SortType.ASCENDING);
        colbarcode.setCellValueFactory(new PropertyValueFactory<item,String >("itembarcode"));
        colmodelno.setCellValueFactory(new PropertyValueFactory<item,String >("modelno"));
        colmodelname.setCellValueFactory(new PropertyValueFactory<item,String>("modelname"));
        colwarranty.setCellValueFactory(new PropertyValueFactory<item,String>("warranty"));
        colhsn.setCellValueFactory(new PropertyValueFactory<item,Integer>("hsn"));
        colgst.setCellValueFactory(new PropertyValueFactory<item,Float>("gst"));
        colcostprc.setCellValueFactory(new PropertyValueFactory<item,Float>("costprice"));
        //colsellingprc.setCellValueFactory(new PropertyValueFactory<item,Float>("sellingprice"));
        statement.close();
        resultSet.close();

        //System.out.println("tabel data");

        //DbUtils.closeQuietly(conn);
        //Connection conn = Connections.b2csDBConncetion();
    }


}



