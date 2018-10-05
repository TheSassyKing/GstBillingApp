package app;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;
import javafx.scene.control.TextField;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Predicate;

public class stockController {

    public class stock{

        private SimpleStringProperty modelno;
        private SimpleStringProperty modelname;
        private SimpleIntegerProperty qty;

        public stock(String modelno, String modelname, int qty) {
            this.modelno = new SimpleStringProperty(modelno);
            this.modelname = new SimpleStringProperty(modelname);
            this.qty = new SimpleIntegerProperty(qty);
        }

        public stock(){
            super();
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

        public int getQty() {
            return qty.get();
        }

        public SimpleIntegerProperty qtyProperty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty.set(qty);
        }
    }

    Connection conn = Connections.b2csDBConncetion();


    //@FXML
    //TextField serchfield = new TextField();
    @FXML
    JFXTextField serchfield = new JFXTextField();


    @FXML
    TableView<stock> tableView = new TableView<>();

    @FXML
    TableColumn<stock,String> colmodelno = new TableColumn<>();

    @FXML
    TableColumn<stock,String> colmodelname =  new TableColumn<>();

    @FXML
    TableColumn<stock,Integer> colqty =  new TableColumn<>();

    ObservableList<stock> tableData = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {

       Refresh();

        FilteredList<stock> filteredList = new FilteredList<>(tableData,e->true);
        serchfield.setOnKeyReleased(e->{

            serchfield.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super stock>) data->{

                    String lower = newValue.toLowerCase();
                    if(newValue == null||newValue.isEmpty()){
                        return true;
                    }

                    if (data.getModelno().toLowerCase().contains(lower)){
                        return true;
                    }else if (data.getModelname().toLowerCase().contains(lower)){

                        return true;
                    }
                    return false;
                });
            });

            SortedList<stock> stockSortedList = new SortedList<>(filteredList);
            stockSortedList.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(stockSortedList);


        });

    }

    void Refresh(){

        tableData.clear();
        try{
            String sql = "SELECT * FROM model_data ";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next())
            {
                tableData.add(new stock(
                        resultSet.getString("model_no"),resultSet.getString("model_name"),
                       resultSet.getInt("quantity")
                ));
            }
            statement.close();
            resultSet.close();

        } catch (SQLException e){
            System.out.println(e);
        }

        tableView.setItems(tableData);
        //collmItemsID.setCellValueFactory(new PropertyValueFactory<items, Integer>("itemId"));
        colmodelno.setCellValueFactory(new PropertyValueFactory<stock,String>("modelno"));
        colmodelno.setSortType(TableColumn.SortType.ASCENDING);
        colmodelname.setCellValueFactory(new PropertyValueFactory<stock,String>("modelname"));
        colqty.setCellValueFactory(new PropertyValueFactory<stock,Integer>("qty"));


    }
}
