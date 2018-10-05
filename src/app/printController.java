package app;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import app.billingController.billitem;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;

public class printController {


    public class bill{
        private SimpleStringProperty  modelname;
        private SimpleIntegerProperty hsn;
        private SimpleIntegerProperty quantity;
        private FloatProperty price;
        private FloatProperty sgstRate;
        private FloatProperty sgstAmount;
        private FloatProperty cgstRate;
        private FloatProperty cgstAmount;
        private FloatProperty total;
        private SimpleStringProperty wrt;




        public bill(String modelname, String wrt,int hsn, int quantity,float price, float sgstRate,
                    float sgstAmount, float cgstRate, float cgstAmount, float total) {
            this.modelname = new SimpleStringProperty(modelname) ;
            this.wrt = new SimpleStringProperty(wrt) ;
            this.hsn = new SimpleIntegerProperty(hsn);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.price = new SimpleFloatProperty(price);
            this.sgstRate = new SimpleFloatProperty(sgstRate);
            this.sgstAmount = new SimpleFloatProperty(sgstAmount);
            this.cgstRate = new SimpleFloatProperty(cgstRate);
            this.cgstAmount = new SimpleFloatProperty(cgstAmount);
            this.total = new SimpleFloatProperty(total);
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

        public String getWrt() {
            return wrt.get();
        }

        public SimpleStringProperty wrtProperty() {
            return wrt;
        }

        public void setWrt(String wrt) {
            this.wrt.set(wrt);
        }

    }

    @FXML
    private AnchorPane printPane;

    @FXML
    private Label name;
    @FXML
    private Label no;
    @FXML
    private Label date;
    @FXML
    private Label invno;
    @FXML
    private Label total;

    @FXML
     TableView<bill> tableView = new TableView<>();

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

    @FXML
    private TableColumn<billitem, String> colwrt = new TableColumn<>();



    ObservableList<bill> tableData = FXCollections.observableArrayList();

    Connection conn = Connections.b2csDBConncetion();
    static String inv ="";

    @FXML
    public void a(ActionEvent event) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {



        printNode(printPane);
        //System.exit(0);
    }
    public static void printNode(final AnchorPane node) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout
                = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
        PrinterJob job = PrinterJob.createPrinterJob();

        node.setPrefSize(pageLayout.getPrintableWidth(), pageLayout.getPrintableHeight());
        if (job != null && job.showPrintDialog(node.getScene().getWindow())) {
            boolean success = job.printPage(pageLayout, node);
            if (success) {
                job.endJob();
            }
        }

    }

    public void setDataFromBillingController(ObservableList<billitem> list,String grandTotal,String invdate,String invno,String cusname,String cusno){
        this.name.setText(cusname);
        this.no.setText(cusno);
        this.date.setText(invdate);
        this.total.setText(grandTotal);
        this.invno.setText(invno);
        inv=invno;

        //tableData = list;

        if(!tableData.isEmpty()){
            System.out.println("emoty");
        }
        System.out.println(list);

        for (billitem item:list){
            //for upadating Quantity

            String table_modelname =  item.getModelname();
            int hsn = item.getHsn();
            int table_qty = item.getQuantity();
            float price = item.getPrice();
            float sgst  = item.getSgstRate();
            float samt = item.getSgstAmount();
            float cgst = item.getCgstRate();
            float camt = item.getCgstAmount();
            float tot = item.getTotal();
            String wrt = item.getWarranty();

           // public bill(String modelname, int hsn, int quantity,float price, float sgstRate,
            //float sgstAmount, float cgstRate, float cgstAmount, float total)

            tableData.add(new bill(table_modelname,wrt,hsn,table_qty,price,sgst,samt,cgst,camt,tot));

        }

        tableView.setItems(tableData);
        colitemname.setCellValueFactory(new PropertyValueFactory<billitem,String>("modelname"));
        colhsn.setCellValueFactory(new PropertyValueFactory<billitem,Integer>("hsn"));
        colwrt.setCellValueFactory(new PropertyValueFactory<billitem,String>("wrt"));
        colqty.setCellValueFactory(new PropertyValueFactory<billitem,Integer>("quantity"));
        colprice.setCellValueFactory(new PropertyValueFactory<billitem,Float>("price"));
        colsrate.setCellValueFactory(new PropertyValueFactory<billitem,Float>("sgstRate"));
        colsamt.setCellValueFactory(new PropertyValueFactory<billitem,Float>("sgstAmount"));
        colcrate.setCellValueFactory(new PropertyValueFactory<billitem,Float>("cgstRate"));
        colcamt.setCellValueFactory(new PropertyValueFactory<billitem,Float>("cgstAmount"));
        coltotal.setCellValueFactory(new PropertyValueFactory<billitem,Float>("total"));


        WritableImage writableImage = new WritableImage(606, 770);
        printPane.snapshot(null,writableImage);
        //Desktop
        String path = "";
        String directoryName = path.concat(System.getProperty("user.home")+"/Desktop/bill/");
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdir();

        }

        File outFile = new File(directoryName+inv+".png");
        System.out.println(outFile);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }



}
