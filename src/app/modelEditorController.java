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
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.apache.commons.beanutils.converters.SqlDateConverter;

import java.sql.*;

public class modelEditorController {


    @FXML
    private JFXButton UpdateBtn = new JFXButton();

    @FXML
    private ChoiceBox warranty = new ChoiceBox();

    @FXML
    private JFXTextField modelno = new JFXTextField();

    @FXML
    private JFXTextField modelname = new JFXTextField();

    @FXML
    private JFXTextField gst = new JFXTextField() ;

    @FXML
    private JFXComboBox hsn = new JFXComboBox();

    String sql ="";
    Connection conn = Connections.b2csDBConncetion();
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ObservableList option = FXCollections.observableArrayList();
    int custId;

    @FXML
    void updateDetails(ActionEvent event) throws SQLException{

        sql = "UPDATE model_data SET  model_no = ?, model_name = ?, warranty = ? , hsn = ? , gst = ?  WHERE id = " + custId;
        System.out.println(sql);
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,  modelno.getText().toString() );
        pstmt.setString(2,  modelname.getText().toString());
        pstmt.setString(3,  warranty.getSelectionModel().getSelectedItem().toString());
        pstmt.setInt(4,  Integer.parseInt(hsn.getSelectionModel().getSelectedItem().toString()));
        pstmt.setFloat(5,  Float.parseFloat(gst.getText().toString()));


        pstmt.executeUpdate();
        pstmt.close();

        //closing

        Stage stage = (Stage)  modelno.getScene().getWindow();
        stage.close();

    }

    public void getDataFrom(int id) throws SQLException {

        custId = id;

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

        sql = "SELECT * FROM model_data  WHERE id = " + id + " LIMIT 1";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        rs.next();
        /**
         * resultSet.getInt("id"),resultSet.getString("model_no"),resultSet.getString("model_name"),
         resultSet.getString("warranty"),resultSet.getInt("hsn"),resultSet.getFloat("gst")
         */
        modelno.setText(rs.getString("model_no"));
        modelname.setText(rs.getString("model_name"));
        warranty.setValue(rs.getString("warranty"));
        hsn.setValue(Integer.toString(rs.getInt("hsn")));
        gst.setText(Float.toString(rs.getFloat("gst")));

        rs.close();
    }
}
