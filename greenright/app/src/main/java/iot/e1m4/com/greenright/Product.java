package iot.e1m4.com.greenright;

import android.graphics.drawable.Drawable;

public class Product {

    private Drawable drawable1;
    private String pName1;
    private String company;
    private String price1;


    public Product() {
    }

    public Product(Drawable drawable1, String pName1, String company, String price1) {
        this.drawable1 = drawable1;
        this.pName1 = pName1;
        this.company = company;
        this.price1 = price1;
    }

    public Drawable getDrawable1() {
        return drawable1;
    }

    public void setDrawable1(Drawable drawable1) {
        this.drawable1 = drawable1;
    }

    public String getpName1() {
        return pName1;
    }

    public void setpName1(String pName1) {
        this.pName1 = pName1;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }
}
