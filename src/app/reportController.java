package app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

public class reportController {

   public class report{
       private SimpleStringProperty inv_number;
       private SimpleStringProperty inv_date;
       private SimpleStringProperty Cus_name;
       private SimpleStringProperty Cus_no;
       private SimpleFloatProperty tot_amt;
       private SimpleFloatProperty tot_tax;
       private SimpleFloatProperty cost_price;

       public report(String inv_number, String inv_date, String cus_name, String cus_no,
                               float tot_amt, float tot_tax, float cost_price) {
           this.inv_number = new SimpleStringProperty(inv_number);
           this.inv_date = new SimpleStringProperty(inv_date);
           this.Cus_name = new SimpleStringProperty(cus_name);
           this.Cus_no = new SimpleStringProperty(cus_no);
           this.tot_amt = new SimpleFloatProperty(tot_amt);
           this.tot_tax = new SimpleFloatProperty(tot_tax);
           this.cost_price = new SimpleFloatProperty(cost_price);
       }

       public String getInv_number() {
           return inv_number.get();
       }

       public SimpleStringProperty inv_numberProperty() {
           return inv_number;
       }

       public void setInv_number(String inv_number) {
           this.inv_number.set(inv_number);
       }

       public String getInv_date() {
           return inv_date.get();
       }

       public SimpleStringProperty inv_dateProperty() {
           return inv_date;
       }

       public void setInv_date(String inv_date) {
           this.inv_date.set(inv_date);
       }

       public String getCus_name() {
           return Cus_name.get();
       }

       public SimpleStringProperty cus_nameProperty() {
           return Cus_name;
       }

       public void setCus_name(String cus_name) {
           this.Cus_name.set(cus_name);
       }

       public String getCus_no() {
           return Cus_no.get();
       }

       public SimpleStringProperty cus_noProperty() {
           return Cus_no;
       }

       public void setCus_no(String cus_no) {
           this.Cus_no.set(cus_no);
       }

       public float getTot_amt() {
           return tot_amt.get();
       }

       public SimpleFloatProperty tot_amtProperty() {
           return tot_amt;
       }

       public void setTot_amt(float tot_amt) {
           this.tot_amt.set(tot_amt);
       }

       public float getTot_tax() {
           return tot_tax.get();
       }

       public SimpleFloatProperty tot_taxProperty() {
           return tot_tax;
       }

       public void setTot_tax(float tot_tax) {
           this.tot_tax.set(tot_tax);
       }

       public float getCost_price() {
           return cost_price.get();
       }

       public SimpleFloatProperty cost_priceProperty() {
           return cost_price;
       }

       public void setCost_price(float cost_price) {
           this.cost_price.set(cost_price);
       }
   }

    @FXML
    private JFXTextField inv_bar;

    @FXML
    private JFXButton inv_schBtn;

    @FXML
    private JFXDatePicker inv_from_date;

    @FXML
    private JFXDatePicker inv_to_date;

    @FXML
    private JFXButton date_sch_btn;

    @FXML
    private TableView<report> tableview;

    @FXML
    private TableColumn<report, String> col_no;

    @FXML
    private TableColumn<report, String> col_date;

    @FXML
    private TableColumn<report, String> col_name;

    @FXML
    private TableColumn<report, Float> col_amt;

    @FXML
    private TableColumn<report, Float> col_tax;

    @FXML
    private TableColumn<report, Float> col_prc;

    @FXML
    private TableColumn<report,String> col_invno;

    ObservableList<report> tableData = FXCollections.observableArrayList();

    @FXML
    private JFXDatePicker tax_from_date;

    @FXML
    private JFXDatePicker tax_to_date;

    @FXML
    private JFXButton date_searchBtn;

    @FXML
    private JFXDatePicker pol_from_date;

    @FXML
    private JFXDatePicker pol_to_Date;

    @FXML
    private JFXButton pol_searchBtn;

    @FXML
            private Label totTax = new Label();
    float mtax = 0.0f;

    @FXML
    private Label sellsamt = new Label();

    float msamt = 0.0f;

    @FXML
    private Label polabel = new Label();

    @FXML
    private ImageView profit_img = new ImageView();

    @FXML
    private ImageView loss_img = new ImageView();

    @FXML
    private Label tot_amt = new Label();

    @FXML
    private Label mop_label = new Label();

    float mtotpol = 0.0f;
    float mtotamt = 0.0f;
    float mtotcp = 0.0f;

    Connection conn = Connections.b2csDBConncetion();

    String sql;
    Statement stmt;
    PreparedStatement pstmt;
    ResultSet rs;

    @FXML
    public void initialize() throws SQLException {

        Refresh();

        polabel.setVisible(false);
        profit_img.setVisible(false);
        loss_img.setVisible(false);
        mop_label.setVisible(false);

        FilteredList<report> filteredList = new FilteredList<>(tableData, e->true);
        inv_bar.setOnKeyReleased(e->{

            inv_bar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super report>) data->{

                    String lower = newValue.toLowerCase();
                    if(newValue == null||newValue.isEmpty()){
                        return true;
                    }

                    if (data.getInv_number().toLowerCase().contains(lower)){
                        return true;
                    }else if (data.getCus_no().toLowerCase().contains(lower)){

                        return true;
                    }else if (data.getCus_name().toLowerCase().contains(lower)){

                        return true;
                    }
                    return false;
                });
            });

            SortedList<report> reportSortedList = new SortedList<>(filteredList);
            reportSortedList.comparatorProperty().bind(tableview.comparatorProperty());
            tableview.setItems(reportSortedList);


        });

        //=======for setting date format consistent with mysql database which is yyyy-mm-dd
        inv_from_date.setConverter(new StringConverter<LocalDate>()
        {
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });
        inv_to_date.setConverter(new StringConverter<LocalDate>()
        {
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });

        //=======for setting date format consistent with mysql database which is yyyy-mm-dd
        tax_from_date.setConverter(new StringConverter<LocalDate>()
        {
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });
        tax_to_date.setConverter(new StringConverter<LocalDate>()
        {
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });

        //=======for setting date format consistent with mysql database which is yyyy-mm-dd
        pol_from_date.setConverter(new StringConverter<LocalDate>()
        {
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });
        pol_to_Date.setConverter(new StringConverter<LocalDate>()
        {
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });



    }

    public void btwDate() throws SQLException {



        String frmDate = inv_from_date.getEditor().getText();
        String tDate = inv_to_date.getEditor().getText();
        System.out.println(frmDate +" " + tDate);
        sql = "SELECT * FROM invoice WHERE inv_date <= ? AND inv_date >= ? ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(2, frmDate);
        pstmt.setString(1, tDate);
        System.out.println(pstmt.toString());
        rs = pstmt.executeQuery();

        tableData.clear();
        while (rs.next()) {
            tableData.add(new report(rs.getString("inv_no"),rs.getString("inv_date"),
                    rs.getString("customer_name"),rs.getString("customer_no"),
                    rs.getFloat("tot_amt"),rs.getFloat("tot_tax"),rs.getFloat("cost_price")));
        }

        rs.close();
        pstmt.close();


        //if(sellingData.isEmpty()){
          //  datanotfounddate.setVisible(false);
        //}
    }

    public void taxbtwDate() throws SQLException {



        String frmDate = tax_from_date.getEditor().getText();
        String tDate = tax_to_date.getEditor().getText();
        System.out.println(frmDate +" " + tDate);
        sql = "SELECT SUM(tot_tax),SUM(tot_amt) FROM invoice WHERE inv_date <= ? AND inv_date >= ? ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(2, frmDate);
        pstmt.setString(1, tDate);
        System.out.println(pstmt.toString());
        rs = pstmt.executeQuery();

        mtax = rs.getFloat(1);
        msamt = rs.getFloat(2);

        BigDecimal bd1 = new BigDecimal(Float.toString(mtax));
        bd1 = bd1.setScale(2, BigDecimal.ROUND_HALF_UP);
        mtax = bd1.floatValue();
        totTax.setText(""+mtax);

        BigDecimal bd2 = new BigDecimal(Float.toString(msamt));
        bd2 = bd2.setScale(2, BigDecimal.ROUND_HALF_UP);
        msamt = bd2.floatValue();
        sellsamt.setText(""+msamt);

        rs.close();
         pstmt.close();

    }

    public void polbtwDate() throws SQLException {


        polabel.setVisible(false);
        profit_img.setVisible(false);
        loss_img.setVisible(false);
        mop_label.setVisible(false);
        String frmDate = pol_from_date.getEditor().getText();
        String tDate = pol_to_Date.getEditor().getText();
        System.out.println(frmDate +" " + tDate);
        sql = "SELECT SUM(tot_amt),SUM(cost_price) FROM invoice WHERE inv_date <= ? AND inv_date >= ? ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(2, frmDate);
        pstmt.setString(1, tDate);
        System.out.println(pstmt.toString());
        rs = pstmt.executeQuery();

        mtotamt = rs.getFloat(1);
        mtotcp = rs.getFloat(2);

        if(mtotamt>=mtotcp){

            mtotpol = mtotamt-mtotcp;
            polabel.setText("PROFIT");
            mop_label.setText("+");
            profit_img.setVisible(true);
            mop_label.setVisible(true);
            polabel.setVisible(true);

        }else {
            mtotpol = mtotcp-mtotamt;
            polabel.setText("LOSS");
            mop_label.setText("-");
            loss_img.setVisible(true);
            mop_label.setVisible(true);
            polabel.setVisible(true);
        }

        BigDecimal bd2 = new BigDecimal(Float.toString(mtotpol));
        bd2 = bd2.setScale(2, BigDecimal.ROUND_HALF_UP);
        mtotpol = bd2.floatValue();
        tot_amt.setText(""+mtotpol);

        rs.close();
        pstmt.close();

    }

    public void Refresh() throws SQLException{


        tableData.clear();
        try{
            String sql = "SELECT * FROM invoice ";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next())
            {
                tableData.add(new report(
                        resultSet.getString("inv_no"),resultSet.getString("inv_date"),
                        resultSet.getString("customer_name"),resultSet.getString("customer_no"),
                        resultSet.getFloat("tot_amt"),resultSet.getFloat("tot_tax"),resultSet.getFloat("cost_price")
                ));
            }
            statement.close();
            resultSet.close();

        } catch (SQLException e){
            System.out.println(e);
        }

        tableview.setItems(tableData);


        col_invno.setCellValueFactory(new PropertyValueFactory<report,String>("inv_number"));

        col_date.setCellValueFactory(new PropertyValueFactory<report,String >("inv_date"));
        col_name.setCellValueFactory(new PropertyValueFactory<report,String>("Cus_name"));
        col_no.setCellValueFactory(new PropertyValueFactory<report,String>("Cus_no"));
        col_amt.setCellValueFactory(new PropertyValueFactory<report,Float>("tot_amt"));
        col_tax.setCellValueFactory(new PropertyValueFactory<report,Float>("tot_tax"));
        col_prc.setCellValueFactory(new PropertyValueFactory<report,Float>("cost_price"));

    }

}
