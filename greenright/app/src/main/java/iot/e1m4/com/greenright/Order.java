package iot.e1m4.com.greenright;

public class Order {

    private String orderDate;
    private String orderNum;
    private String orderItem;
    private String orderCnt;
    private String orderPrice;
    private String orderState;

    public Order() {
    }

    public Order(String orderDate, String orderNum, String orderItem, String orderCnt, String orderPrice, String orderState) {
        this.orderDate = orderDate;
        this.orderNum = orderNum;
        this.orderItem = orderItem;
        this.orderCnt = orderCnt;
        this.orderPrice = orderPrice;
        this.orderState = orderState;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public String getOrderCnt() {
        return orderCnt;
    }

    public void setOrderCnt(String orderCnt) {
        this.orderCnt = orderCnt;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
}
