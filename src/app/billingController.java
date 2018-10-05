package app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.property.*;
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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.commons.dbutils.DbUtils;
import org.controlsfx.control.textfield.TextFields;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class billingController {


    public class billitem{

        private SimpleIntegerProperty no;
        //private SimpleStringProperty itembarcode;
        private SimpleStringProperty modelno;
        private SimpleStringProperty  modelname;
        private SimpleStringProperty  warranty;
        private SimpleIntegerProperty hsn;
        // private SimpleFloatProperty gst;
        private SimpleIntegerProperty quantity;
        private FloatProperty price;
        private FloatProperty sgstRate;
        private FloatProperty sgstAmount;
        private FloatProperty cgstRate;
        private FloatProperty cgstAmount;
       // private SimpleFloatProperty sellingprice;
        private FloatProperty total;

        public billitem(int no, String modelno, String modelname,String warranty, int hsn, int quantity, float price, float sgstRate, float sgstAmount,
                                float cgstRate, float cgstAmount, float total) {
            this.no = new SimpleIntegerProperty(no);
            this.modelno = new SimpleStringProperty(modelno);
            this.modelname = new SimpleStringProperty(modelname);
            this.warranty = new SimpleStringProperty(warranty);
            this.hsn = new SimpleIntegerProperty(hsn);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.price = new SimpleFloatProperty(price);
            this.sgstRate = new SimpleFloatProperty(sgstRate);
            this.sgstAmount = new SimpleFloatProperty(sgstAmount);
            this.cgstRate = new SimpleFloatProperty(cgstRate);
            this.cgstAmount = new SimpleFloatProperty(cgstAmount);
            this.total = new SimpleFloatProperty(total);
        }

        public billitem(){
            super();
        }

        public int getNo() {
            return no.get();
        }

        public SimpleIntegerProperty noProperty() {
            return no;
        }

        public void setNo(int no) {
            this.no.set(no);
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

        public int getHsn() {
            return hsn.get();
        }

        public SimpleIntegerProperty hsnProperty() {
            return hsn;
        }

        public void setHsn(int hsn) {
            this.hsn.set(hsn);
        }

        public int getQuantity() {
            return quantity.get();
        }

        public SimpleIntegerProperty quantityProperty() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity.set(quantity);
        }

        public float getPrice() {
            return price.get();
        }

        public FloatProperty priceProperty() {
            return price;
        }

        public void setPrice(float price) {
            this.price.set(price);
        }

        public float getSgstRate() {
            return sgstRate.get();
        }

        public FloatProperty sgstRateProperty() {
            return sgstRate;
        }

        public void setSgstRate(float sgstRate) {
            this.sgstRate.set(sgstRate);
        }

        public float getSgstAmount() {
            return sgstAmount.get();
        }

        public FloatProperty sgstAmountProperty() {
            return sgstAmount;
        }

        public void setSgstAmount(float sgstAmount) {
            this.sgstAmount.set(sgstAmount);
        }

        public float getCgstRate() {
            return cgstRate.get();
        }

        public FloatProperty cgstRateProperty() {
            return cgstRate;
        }

        public void setCgstRate(float cgstRate) {
            this.cgstRate.set(cgstRate);
        }

        public float getCgstAmount() {
            return cgstAmount.get();
        }

        public FloatProperty cgstAmountProperty() {
            return cgstAmount;
        }

        public void setCgstAmount(float cgstAmount) {
            this.cgstAmount.set(cgstAmount);
        }

        public float getTotal() {
            return total.get();
        }

        public FloatProperty totalProperty() {
            return total;
        }

        public void setTotal(float total) {
            this.total.set(total);
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
    }

    Connection conn = Connections.b2csDBConncetion();





    float totalBillableAmount = 0.0f;
    float sgstRate = 0f;
    float cgstRate = 0f;
    String custname = "";
    String custMob = "";
    String custGstin = "";
    String custAddr = "";
    float cess = 0.0f;



    //items field for making bill

    //@FXML
    //TextField CustomerNo = new TextField();
    @FXML
    JFXTextField CustomerNo = new JFXTextField();

    //@FXML
    //TextField CustomerName = new TextField();
    @FXML
    JFXTextField CustomerName = new JFXTextField();

    //@FXML
    //Button SaveandPrint = new Button();
    @FXML
    JFXButton SaveandPrint = new JFXButton();

    @FXML
    Label billinGrandTotal = new Label();

    //@FXML
    //TextField barcode = new TextField();

    @FXML
    JFXTextField barcode = new JFXTextField();
    String mbarcode = "";

    //@FXML
    //TextField itemno = new TextField();
    @FXML
    JFXTextField itemno = new JFXTextField();
    String mitemmo = "";

    //@FXML
   // TextField itemname = new TextField();
    @FXML
    JFXTextField itemname = new JFXTextField();
    String mitemname = "";

    //@FXML
    //TextField gst = new TextField();
    @FXML
    JFXTextField gst = new JFXTextField();
    float mgst = 0;

    //@FXML
    //TextField hsn = new TextField();
    @FXML
    JFXTextField hsn = new JFXTextField();
    int mhsn = 0;

    //@FXML
    //TextField warranty = new TextField();
    @FXML
    JFXTextField warranty = new JFXTextField();
    String mwarranty = "";

    //@FXML
    //TextField price = new TextField();
    @FXML
    JFXTextField price = new JFXTextField();
    float mprice = 0;

    //@FXML
    //TextField invnumber = new TextField();

    @FXML
    JFXTextField invnumber = new JFXTextField();



    //Table View

    @FXML
    TableView<billitem> tableView = new TableView<>();

    @FXML
    TableColumn<billitem,Integer> colno =  new TableColumn<>();

    @FXML
    TableColumn<billitem,String> colitemno =  new TableColumn<>();

    @FXML
    TableColumn<billitem,String> colitemname =  new TableColumn<>();

    @FXML
    TableColumn<billitem,Integer> colhsn =  new TableColumn<>();

    @FXML
    TableColumn<billitem,Integer> colqty =  new TableColumn<>();

    @FXML
    TableColumn<billitem,Float> colprice =  new TableColumn<>();

    @FXML
    TableColumn<billitem,Float> colsrate =  new TableColumn<>();

    @FXML
    TableColumn<billitem,Float> colsamt =  new TableColumn<>();

    @FXML
    TableColumn<billitem,Float> colcrate =  new TableColumn<>();

    @FXML
    TableColumn<billitem,Float> colcamt =  new TableColumn<>();

    @FXML
    TableColumn<billitem,Float> coltotal =  new TableColumn<>();


    ObservableList<billitem> tableData = FXCollections.observableArrayList();
    String sql;
    Statement stmt;
    PreparedStatement pstmt;
    ResultSet rs;
    ArrayList<String> barcodelist = new ArrayList<>();
    int no =0;
    int qty=1;
    float msamt=0f;
    float mcamt = 0f;
    float mtotal = 0f;
    float mtax=0f;
    float tax=0f;
    float cost_price = 0f;
    float cp=0.0f;

    ObservableList<String> dbarcode = FXCollections.observableArrayList();

    //Button
    //@FXML
    //Button addBtn =  new Button();
    @FXML
    JFXButton addBtn = new JFXButton();

    @FXML
    public void initialize() throws SQLException {

        invnumber.setText(invGenerator());

        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Price Empty");
        price.getValidators().add(requiredFieldValidator);

        RequiredFieldValidator requiredFieldValidator1 = new RequiredFieldValidator();
        requiredFieldValidator1.setMessage("Barcode  Empty");
        barcode.getValidators().add(requiredFieldValidator1);

        //addoption();

        //System.out.println(System.getProperty("user.home"));

    }

    @FXML
    void handleScanner(KeyEvent event) throws SQLException {
        String itno;
        String itname;
        String hsnno;
        String gstnno;
        String warty;


        ResultSet rs3;
        dbarcode.removeAll();
        //sql = "SELECT * FROM item";
       // stmt3 = conn.createStatement();

        String sql1 = "SELECT * FROM item WHERE barcode = ?";
        //String sql1 = "UPDATE model_data SET model_data.quantity = model_data.quantity + 1 WHERE model_data.model_no = '" + modelno+"'";
        PreparedStatement stmt3 = conn.prepareStatement(sql1);
        stmt3.setString(1,barcode.getText().toString());
        rs3 = stmt3.executeQuery();

        if(rs3 != null){
        if( rs3.next()){
          itemno.setText(rs3.getString("model_no"));
            itemname.setText(rs3.getString("model_name"));
            hsn.setText(rs3.getString("hsn"));
            gst.setText(rs3.getString("gst"));
           warranty.setText(rs3.getString("warranty"));
        cost_price = rs3.getFloat("cost_price");
        }}







        rs3.close();


        //stmt1.executeUpdate();
        //stmt1.close();
       // rs = stmt.executeQuery(sql);
       // String subSQL = "SELECT * FROM item WHERE barcode = '" + barcode.getText().toString()+"'";

      /*  try {

            rs3 = stmt3.executeQuery(subSQL);
            rs3.next();

            itemno.setText(rs3.getString("model_no"));
            itemname.setText(rs3.getString("model_name"));
            hsn.setText(rs3.getString("hsn"));
            gst.setText(rs3.getString("gst"));
            warranty.setText(rs3.getString("warranty"));
            cost_price = rs3.getFloat("cost_price");
            System.out.println(cost_price);


            stmt3.close();
            rs3.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
*/
    }

    /* void addoption()throws SQLException{

       // dbarcode.clear();
        dbarcode.removeAll();
        sql = "SELECT * FROM item";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            dbarcode.add(rs.getString("barcode"));
        }
        TextFields.bindAutoCompletion(barcode, dbarcode);
        barcode.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldVal, Boolean newVal) {
                if (!newVal && !barcode.getText().toString().equals("")) {
                    String subSQL = "SELECT * FROM item WHERE barcode = '" + barcode.getText().toString()+"'";
                    System.out.println("sds" + barcode.getText().toString());
                    try {

                        rs = stmt.executeQuery(subSQL);
                        rs.next();

                        itemno.setText(rs.getString("model_no"));
                        itemname.setText(rs.getString("model_name"));
                        hsn.setText(rs.getString("hsn"));
                        gst.setText(rs.getString("gst"));
                        warranty.setText(rs.getString("warranty"));
                        cost_price = rs.getFloat("cost_price");
                        System.out.println(cost_price);


                        stmt.close();
                        rs.close();

                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

    }*/




    public void addItem(ActionEvent actionEvent)throws SQLException {
        if(!price.getText().equals("")&&!barcode.getText().equals("")){

            barcodelist.add(barcode.getText());
            System.out.println(barcodelist);

            mitemmo = itemno.getText().toString();
            mitemname = itemname.getText().toString();
            mhsn = Integer.parseInt(hsn.getText().toString());
            mprice = Float.parseFloat(price.getText().toString());
            mgst = Float.parseFloat(gst.getText().toString());
            mwarranty=warranty.getText().toString();
            float temp = mgst;
            cgstRate = temp / 2;
            sgstRate = temp / 2;
            msamt= mprice*(sgstRate/100);
            mcamt= mprice*(cgstRate/100);
            mtotal = mprice;
            tax=msamt+mcamt;
            mprice = mtotal-(msamt+mcamt);
            cp=cp+cost_price;
            System.out.println(cp);

            // mtotal = mprice+msamt+mcamt;

            //public billitem(int no, String modelno, String modelname, int hsn, int quantity, float price, float sgstRate, float sgstAmount,
            // float cgstRate, float cgstAmount, float total)

            //checking mutiple qty
            if(qty!=0){

                for (billitem item:tableData){
                    if(item.getModelno().equals(mitemmo)){
                        System.out.println("ture");
                        int table_qty = item.getQuantity()+1;
                        float table_price = item.getPrice()+mprice;
                        float table_msamt = item.getSgstAmount()+msamt;
                        float table_mcamt = item.getCgstAmount()+mcamt;
                        float table_total= item.getTotal()+mtotal;
                        BigDecimal bd1 = new BigDecimal(Float.toString(msamt));
                        bd1 = bd1.setScale(2, BigDecimal.ROUND_HALF_UP);
                        msamt = bd1.floatValue();

                        BigDecimal bd2 = new BigDecimal(Float.toString(mcamt));
                        bd2 = bd2.setScale(2, BigDecimal.ROUND_HALF_UP);
                        mcamt = bd2.floatValue();
                        item.setQuantity(table_qty);
                        item.setPrice(table_price);
                        item.setSgstAmount(table_msamt);
                        item.setCgstAmount(table_mcamt);
                        item.setTotal(table_total);
                        totalBillableAmount = totalBillableAmount + mtotal;
                        mtax = mtax + msamt+mcamt;
                        BigDecimal bd = new BigDecimal(Float.toString(totalBillableAmount));
                        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                        totalBillableAmount = bd.floatValue();
                        //subtractItemFromStock(mitemmo);
                        billinGrandTotal.setText("Rs. " + String.valueOf(totalBillableAmount));
                        tableView.getItems().set(tableView.getItems().indexOf(item),item);
                        System.out.println(mtax);
                        clear();

                        return;
                    }

                }
                //subtractItemFromStock(mitemmo);
                totalBillableAmount = totalBillableAmount + mtotal;
                mtax = mtax + tax;
                BigDecimal bd = new BigDecimal(Float.toString(totalBillableAmount));
                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                totalBillableAmount = bd.floatValue();

                BigDecimal bd1 = new BigDecimal(Float.toString(msamt));
                bd1 = bd1.setScale(2, BigDecimal.ROUND_HALF_UP);
                msamt = bd1.floatValue();

                BigDecimal bd2 = new BigDecimal(Float.toString(mcamt));
                bd2 = bd2.setScale(2, BigDecimal.ROUND_HALF_UP);
                mcamt = bd2.floatValue();

                billinGrandTotal.setText("Rs. " + String.valueOf(totalBillableAmount));

                tableData.add(new billitem(++no,mitemmo,mitemname,mwarranty,mhsn,qty,mprice,sgstRate,msamt,cgstRate,mcamt,mtotal));
                tableView.setItems(tableData);
                System.out.println(mtax);


            }






            colno.setCellValueFactory(new PropertyValueFactory<billitem,Integer>("no"));
            colno.setSortType(TableColumn.SortType.ASCENDING);
            colitemno.setCellValueFactory(new PropertyValueFactory<billitem,String>("modelno"));
            colitemname.setCellValueFactory(new PropertyValueFactory<billitem,String>("modelname"));
            colhsn.setCellValueFactory(new PropertyValueFactory<billitem,Integer>("hsn"));
            colqty.setCellValueFactory(new PropertyValueFactory<billitem,Integer>("quantity"));
            colprice.setCellValueFactory(new PropertyValueFactory<billitem,Float>("price"));
            colsrate.setCellValueFactory(new PropertyValueFactory<billitem,Float>("sgstRate"));
            colsamt.setCellValueFactory(new PropertyValueFactory<billitem,Float>("sgstAmount"));
            colcrate.setCellValueFactory(new PropertyValueFactory<billitem,Float>("cgstRate"));
            colcamt.setCellValueFactory(new PropertyValueFactory<billitem,Float>("cgstAmount"));
            coltotal.setCellValueFactory(new PropertyValueFactory<billitem,Float>("total"));



            clear();





        }else {
            price.validate();
            barcode.validate();
        }



    }

    void clear(){
        barcode.clear();
        itemno.clear();
        itemname.clear();
        hsn.clear();
        gst.clear();
        warranty.clear();
        price.clear();
    }

    public void subtractItemFromStock(String itemno) throws SQLException{
        System.out.println(itemno);
        String sql1 = "update model_data set quantity = quantity - 1 where model_no = ?";
        //String sql1 = "UPDATE model_data SET model_data.quantity = model_data.quantity + 1 WHERE model_data.model_no = '" + modelno+"'";
        PreparedStatement stmt1 = conn.prepareStatement(sql1);
        stmt1.setString(1,itemno);
        stmt1.executeUpdate();
        stmt1.close();


    }

    private String invGenerator() throws SQLException{
        String invnpo = "inv00001";

        PreparedStatement stmt1 = conn.prepareStatement("SELECT MAX(inv_id) FROM `invoice`");
        ResultSet resultSet = stmt1.executeQuery();
        if(resultSet.next()){
            System.out.println(resultSet.getString(1));
            int n = Integer.parseInt(invnpo.substring(3))+resultSet.getInt(1);
            int x = String.valueOf(n).length();
            invnpo= invnpo.substring(0,8-x)+String.valueOf(n);

        }

        stmt1.close();
        resultSet.close();

        return invnpo;
    }


    public void SaveandPrint(ActionEvent actionEvent)throws SQLException{

        String innno = invnumber.getText().toString();
        String cusno = CustomerNo.getText().toString();
        String cusname = CustomerName.getText().toString();
        float total = totalBillableAmount;
        float tax = mtax;
        float cp1= cp;


        String sql1 = "update model_data set quantity = quantity - ? where model_no = ?";
        String sql2 = "INSERT INTO `invoice` ( `inv_no`, `inv_date`, `customer_no`, `customer_name`,  `tot_amt`,`tot_tax`,`cost_price`) VALUES ( ?, ?, ?, ?, ?, ?,?)";
        String sql3 = "  INSERT INTO `stockout` (`barcode`, `model_no`, `model_name`, `warranty`, `hsn`, `gst`, `cost_price`) VALUES (?,?,?,?,?,?,?)";
        String sql4 = "  SELECT * FROM item WHERE barcode = ?";
        String sql5 = "DELETE FROM item WHERE barcode = ?";


        PreparedStatement stmt1 = conn.prepareStatement(sql1);
        PreparedStatement stmt2 = conn.prepareStatement(sql2);
        PreparedStatement stmt6 = conn.prepareStatement(sql3);
        PreparedStatement stmt4 = conn.prepareStatement(sql4);
        PreparedStatement stmt5 = conn.prepareStatement(sql5);

        for (billitem item:tableData){
            //for upadating Quantity
            int table_qty = item.getQuantity();
            String table_modelno =  item.getModelno();
            stmt1.setInt(1,table_qty);
            stmt1.setString(2,table_modelno);
            stmt1.executeUpdate();

        }

        stmt1.close();

        //Inserting into Invoice Table

        stmt2.setString(1,innno);
        stmt2.setString(2,LocalDate.now().toString());
        stmt2.setString(3,cusno);
        stmt2.setString(4,cusname);
        stmt2.setFloat(5,total);
        stmt2.setFloat(6,tax);
        stmt2.setFloat(7,cp1);
        stmt2.executeUpdate();
        stmt2.close();

       /* rs3 = stmt3.executeQuery();

        if(rs3 != null){
            if( rs3.next()){
                itemno.setText(rs3.getString("model_no"));
                itemname.setText(rs3.getString("model_name"));
                hsn.setText(rs3.getString("hsn"));
                gst.setText(rs3.getString("gst"));
                warranty.setText(rs3.getString("warranty"));
                cost_price = rs3.getFloat("cost_price");
            }}*/

        for(String s : barcodelist){
            stmt4.setString(1,s);
            ResultSet resultSet ;
            resultSet =stmt4.executeQuery();
            if(resultSet != null){
                if( resultSet.next()) {
                    stmt6.setString(1, resultSet.getString("barcode"));
                    stmt6.setString(2, resultSet.getString("model_no"));
                    stmt6.setString(3, resultSet.getString("model_name"));
                    stmt6.setString(4, resultSet.getString("warranty"));
                    stmt6.setString(5, resultSet.getString("hsn"));
                    stmt6.setString(6, resultSet.getString("gst"));
                    stmt6.setString(7, resultSet.getString("cost_price"));
                    stmt6.executeUpdate();
                    stmt5.setString(1,s);
                    stmt5.executeUpdate();
                }}

            //resultSet.close();

        }

        stmt6.close();
        stmt4.close();
        stmt5.close();



        // itemno.setText(rs.getString("model_no"));
        //                        itemname.setText(rs.getString("model_name"));
        //                        hsn.setText(rs.getString("hsn"));
        //                        gst.setText(rs.getString("gst"));
        //                        warranty.setText(rs.getString("warranty"));
        //                        cost_price = rs.getFloat("cost_price");


        try {
            Testprint tp = new Testprint();
            //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("print.fxml"));
           // Parent root1 = (Parent) fxmlLoader.load();
           // Stage stage = new Stage();
            //stage.setScene(new Scene(root1));
            //((printController) fxmlLoader.getController()).setDataFromBillingController(tableData,
                    //String.valueOf(totalBillableAmount),LocalDate.now().toString(),invnumber.getText(),CustomerName.getText(),CustomerNo.getText());
               tp.setDataFromBillingController(tableData,
                    totalBillableAmount,LocalDate.now().toString(),invnumber.getText(),CustomerName.getText(),CustomerNo.getText());
            //stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


        tableData.clear();
        invnumber.clear();
        invGenerator();
        invnumber.setText(invGenerator());
        CustomerName.clear();
        CustomerNo.clear();
        billinGrandTotal.setText("Rs.0000");
        totalBillableAmount = 0.0f;
        no=0;
        mtax=0.0f;
        cp=0.0f;
        clear();


        //addoption();
    }




}


