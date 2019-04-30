package com.jian86_android.todolist;

public class Page2_listItem {


    private String itemName;
    private String itemPrice;
    private String itemAmount;
    private String itemTotal;

    private boolean isCheck;
    private  boolean isSave = false;
    private  int itemType = 0;
    boolean giveIn=true;

    public boolean isGiveIn() {
        return giveIn;
    }

    public void setGiveIn(boolean giveIn) {
        this.giveIn = giveIn;
    }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public String getItemPrice() { return itemPrice; }
    public void setItemPrice(String itemPrice) { if(itemPrice.equals("")) itemPrice= "0"; this.itemPrice = itemPrice; }
    public String getItemAmount() { return itemAmount; }
    public void setItemAmount(String itemAmount) {

        if(itemAmount.equals("") && itemPrice.equals("0")) itemAmount= "0";
        else if(itemAmount.equals("")) itemAmount= "1";

        this.itemAmount = itemAmount;
    }
    public String getItemTotal() { return itemTotal; }
    public void setItemTotal(String itemTotal) {
        if(itemAmount.equals("0") && itemPrice.equals("0")) itemTotal = "0";
        this.itemTotal = itemTotal;
    }
    public boolean isSave() { return isSave; }
    public int getItemType() {
        return itemType;
    }
    public void setSave(boolean save) { isSave = save; }
    public boolean isCheck() {
        return isCheck;
    }
    public void setCheck(boolean check) {
        isCheck = check;
    }
    public Page2_listItem(String itemName) {
        this.itemName = itemName;
        this.isCheck =false;
    }
    public Page2_listItem() {
        this.isCheck =false;
    }

}//Page1_listItem
