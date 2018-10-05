package app;
import app.billingController.billitem;

import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Testprint {

    Float totSgst = 0.0f;
    Float totCgst = 0.0f;
    Float totGst = 0.0f;
    int totQty = 0;
    Float mcamt= 0.f;
    int gTotword = 0;
    public void setDataFromBillingController(ObservableList<billitem> list, Float grandTotal, String invdate, String invno, String cusname, String cusno){

        //tableData = list;
        InputStream arq = Main.class.getResourceAsStream("/app/testingBill.jrxml");
        String img1_path = "app/images/img.jpg";
        String img2_path = "app/images/qu.jpg";
        String img3_path = "app/images/rupees.png";

        try {
            JasperReport jr = JasperCompileManager.compileReport(arq);
            ArrayList<Tester> dlist = new ArrayList<>();
            Map<String,Object> data = new HashMap<String, Object>();

            for (billitem item:list){
                //for upadating Quantity

                totSgst=+item.getSgstAmount();
                totCgst=+item.getCgstAmount();
                totQty=+item.getQuantity();

                String modelname =  item.getModelname();
                String modelnumber = item.getModelno();
                String hsn = Integer.toString(item.getHsn());
                String quantity = Integer.toString(item.getQuantity());
                BigDecimal bd2 = new BigDecimal(Float.toString(item.getPrice()));
                bd2 = bd2.setScale(2, BigDecimal.ROUND_HALF_UP);
                mcamt = bd2.floatValue();
                String price = Float.toString(mcamt);
                String sgstRate  = Float.toString(item.getSgstRate())+"%";
                String sgstAmount = Float.toString(item.getSgstAmount());
                String cgstRate = Float.toString(item.getCgstRate())+"%";
                System.out.println(cgstRate);
                String cgstAmount = Float.toString(item.getCgstAmount());
                String total = Float.toString(item.getTotal());
                String wrt = item.getWarranty();
                dlist.add(new Tester(modelname,modelnumber,hsn,quantity,price,sgstRate,sgstAmount,cgstRate,cgstAmount,total,wrt));
            }

            totGst = totCgst+totSgst;
            gTotword = Math.round(grandTotal);
            data.put("cusname",cusname.toUpperCase());
            data.put("cusno",cusno);
            data.put("invdate",invdate);
            data.put("invno",invno);
            data.put("totSgst",totSgst);
            data.put("totCgst",totCgst);
            data.put("totGst",totGst);
            data.put("grandTotal",grandTotal);
            data.put("img1",img1_path);
            data.put("img2",img2_path);
            data.put("gTotword",gTotword);
            data.put("img3",img3_path);

            String path = "";
            String directoryName = path.concat(System.getProperty("user.home")+"/Desktop/bill/");
            File directory = new File(directoryName);
            if (! directory.exists()){
                directory.mkdir();

            }


            JRBeanCollectionDataSource jrc = new JRBeanCollectionDataSource(dlist);
            JasperPrint jp = JasperFillManager.fillReport(jr,data,jrc);
            totQty=0;
            totCgst=0.0f;
            totSgst=0.f;
            JasperViewer.viewReport(jp,false);
            JasperExportManager.exportReportToPdfFile(jp,directoryName+invno+".pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }







    }
}
