package com.compactit.compactit.compactisolution.model;

public class Order_Model {
    private String Txid;
    private String aName;
    private long amount;
    private String bName;
    private String bid;
    private String contact;
    private String cover;
    private int itemNum;
    private String paymentAccount;
    private String paymentMethod;
    private String shipping;
    private String status;
    private long time;
    private String uid;

    public Order_Model() {
    }

    public Order_Model(String Txid, String aName, long amount, String bName, String bid,
                       String contact, String cover, int itemNum, String paymentAccount,
                       String paymentMethod, String shipping, String status, long time, String uid) {
        this.Txid = Txid;
        this.aName = aName;
        this.amount = amount;
        this.bName = bName;
        this.bid = bid;
        this.contact = contact;
        this.cover = cover;
        this.itemNum = itemNum;
        this.paymentAccount = paymentAccount;
        this.paymentMethod = paymentMethod;
        this.shipping = shipping;
        this.status = status;
        this.time = time;
        this.uid = uid;
    }

    public String getTxid() {
        return Txid;
    }

    public void setTxid(String txid) {
        Txid = txid;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public String getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}


//
//
//Txid:
//        "a"
//        aName:
//        "Adil"
//        amount:
//        400
//        bName:
//        "Ram speed"
//        bid:
//        "2"
//        contact:
//        "5"
//        cover:
//        "https://firebasestorage.googleapis.com/v0/b/com..."
//        itemNum:
//        1
//        paymentAccount:
//        "8"
//        paymentMethod:
//        "bKash"
//        shipping:
//        "g"
//        status:
//        "pending"
//        time:
//        1612298185935
//        uid:
//        "OHObv0nL68VY4ZnpZr8cDKmYWJ13"
