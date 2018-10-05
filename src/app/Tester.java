package app;

public class Tester {
    String  modelname;
    String modelnumber;
    String hsn;
    String quantity;
    String price;
     String sgstRate;
     String sgstAmount;
     String cgstRate;
     String cgstAmount;
     String total;
     String wrt;

    public Tester(String modelname, String modelnumber, String hsn, String quantity, String price, String sgstRate, String sgstAmount, String cgstRate, String cgstAmount, String total, String wrt) {
        this.modelname = modelname;
        this.modelnumber = modelnumber;
        this.hsn = hsn;
        this.quantity = quantity;
        this.price = price;
        this.sgstRate = sgstRate;
        this.sgstAmount = sgstAmount;
        this.cgstRate = cgstRate;
        this.cgstAmount = cgstAmount;
        this.total = total;
        this.wrt = wrt;
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    public String getModelnumber() {
        return modelnumber;
    }

    public void setModelnumber(String modelnumber) {
        this.modelnumber = modelnumber;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSgstRate() {
        return sgstRate;
    }

    public void setSgstRate(String sgstRate) {
        this.sgstRate = sgstRate;
    }

    public String getSgstAmount() {
        return sgstAmount;
    }

    public void setSgstAmount(String sgstAmount) {
        this.sgstAmount = sgstAmount;
    }

    public String getCgstRate() {
        return cgstRate;
    }

    public void setCgstRate(String cgstRate) {
        this.cgstRate = cgstRate;
    }

    public String getCgstAmount() {
        return cgstAmount;
    }

    public void setCgstAmount(String cgstAmount) {
        this.cgstAmount = cgstAmount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getWrt() {
        return wrt;
    }

    public void setWrt(String wrt) {
        this.wrt = wrt;
    }
}
