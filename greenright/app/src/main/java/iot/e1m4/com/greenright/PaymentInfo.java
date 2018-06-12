package iot.e1m4.com.greenright;

import android.os.Parcel;
import android.os.Parcelable;

public class PaymentInfo implements Parcelable{
    private String productId;
    private String productName;
    private String userId;
    private String purchaseType;
    private String purchaseAmount;
    private String compId;
    private String deliveryName;
    private String deliveryAddress;
    private String deliveryPhone;
    private String productValue;

    public PaymentInfo(Parcel in) {
        productId = in.readString();
        productName = in.readString();
        productValue = in.readString();
        userId = in.readString();
        purchaseType = in.readString();
        purchaseAmount = in.readString();
        compId = in.readString();
        deliveryName = in.readString();
        deliveryAddress = in.readString();
        deliveryPhone = in.readString();
    }

    public static final Creator<PaymentInfo> CREATOR = new Creator<PaymentInfo>() {
        @Override
        public PaymentInfo createFromParcel(Parcel in) {
            return new PaymentInfo(in);
        }

        @Override
        public PaymentInfo[] newArray(int size) {
            return new PaymentInfo[size];
        }
    };

    public PaymentInfo() {

    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(String purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeString(productName);
        dest.writeString(productValue);
        dest.writeString(userId);
        dest.writeString(purchaseType);
        dest.writeString(purchaseAmount);
        dest.writeString(compId);
        dest.writeString(deliveryName);
        dest.writeString(deliveryAddress);
        dest.writeString(deliveryPhone);
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public String getProductValue() {
        return productValue;
    }

    public void setProductValue(String productValue) {
        this.productValue = productValue;
    }

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", userId='" + userId + '\'' +
                ", purchaseType='" + purchaseType + '\'' +
                ", purchaseAmount='" + purchaseAmount + '\'' +
                ", compId='" + compId + '\'' +
                ", deliveryName='" + deliveryName + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", deliveryPhone='" + deliveryPhone + '\'' +
                ", productValue='" + productValue + '\'' +
                '}';
    }
}
