package com.jian86_android.todolist;

public class Page1_listItem {
    private String desc;
    private boolean isCheck;
    private  boolean isSave = false;
    private  int itemType = 0;
    public boolean isSave() { return isSave; }

    public int getItemType() {
        return itemType;
    }

    public void setSave(boolean save) { isSave = save; }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
    public Page1_listItem(){}
    public Page1_listItem(String desc) {
        this.desc = desc;
        this.isCheck =false;
    }

    public Page1_listItem(String desc, boolean isCheck) {
        this.desc = desc;
        this.isCheck = isCheck;
    }
}//Page1_listItem
