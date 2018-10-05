package app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.sql.*;
import java.util.Collections;

public class editDialogController {

    @FXML

    private JFXButton UpdateBtn = new JFXButton();

    @FXML
    private JFXTextField barcode =  new JFXTextField();

    @FXML
    private JFXTextField modelname = new JFXTextField();

    @FXML
    private JFXTextField warranty = new JFXTextField();

    @FXML
    private JFXTextField hsn =  new JFXTextField();

    @FXML
    private JFXTextField gst = new JFXTextField();

    @FXML
    private JFXTextField costprice = new JFXTextField();

    @FXML
    private JFXComboBox modelno = new JFXComboBox();
    String sql ="";
    Connection conn = Connections.b2csDBConncetion();
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ObservableList option = FXCollections.observableArrayList();
    int custId;

    @FXML
    void updateDetails(ActionEvent event) throws SQLException {

        sql = "UPDATE item SET barcode = ?, model_no = ?, model_name = ?, warranty = ? , hsn = ? , gst = ? , cost_price = ? WHERE id = " + custId;
        System.out.println(sql);
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,  barcode.getText().toString() );
        pstmt.setString(2,  modelno.getSelectionModel().getSelectedItem().toString());
        pstmt.setString(3,  modelname.getText().toString());
        pstmt.setString(4,  warranty.getText().toString());
        pstmt.setInt(5,  Integer.parseInt(hsn.getText().toString()));
        pstmt.setFloat(6,Float.parseFloat(gst.getText().toString()));
        pstmt.setFloat(7,Float.parseFloat(costprice.getText().toString()));

        pstmt.executeUpdate();

        //closing
        pstmt.close();
        Stage stage = (Stage)  barcode.getScene().getWindow();
        stage.close();

    }

    public void getDataFrom(int id) throws SQLException {
        custId = id;

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

        /*`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`barcode`	varchar(300) NOT NULL,
	`model_no`	varchar(200) NOT NULL,
	`model_name`	varchar(200) NOT NULL,
	`warranty`	varchar(200) DEFAULT NULL,
	`hsn`	int(100) NOT NULL,
	`gst`	float NOT NULL,
	`cost_price`	float NOT NULL*/

        sql = "SELECT * FROM item  WHERE id = " + id + " LIMIT 1";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        rs.next();
        barcode.setText(rs.getString("barcode"));
        modelno.setValue(rs.getString("model_no"));
        modelname.setText(rs.getString("model_name"));
        warranty.setText(rs.getString("warranty"));
        hsn.setText(Integer.toString(rs.getInt("hsn")));
        gst.setText(Float.toString(rs.getFloat("gst")));
        costprice.setText(Float.toString( rs.getFloat("cost_price")));
        //name.setText(rs.getString("dealer_company_name"));
        //contact.setText(rs.getString("dealer_company_addr"));
        //gstin.setText(rs.getString("dealer_company_gstn"));
        // address.setText( rs.getString("dealer_company_contact") );
        rs.close();
    }


}
