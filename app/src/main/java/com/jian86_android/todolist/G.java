package com.jian86_android.todolist;


import android.app.Application;

import java.util.ArrayList;

public class G extends Application {
    //키보드 상태
    public static boolean g_Keybord = true;

    public static boolean isG_Keybord() {
        return g_Keybord;
    }

    public static void setG_Keybord(boolean g_Keybord) {
        G.g_Keybord = g_Keybord;
    }
    //TODOLIST
//    public  static  String[] TODODescs;
//    public  static  String[] TODOstatus;
//    public  static  String TODOday;
    public static ArrayList<Page1_listItem> todoDatas ;

    public static ArrayList<Page1_listItem> getTodoDatas() {
        return todoDatas;
    }

    public static void setTodoDatas(ArrayList<Page1_listItem> todoDatas) {
        G.todoDatas = todoDatas;
    }
    //
//    public static String[] getTODOescs() {
//        return TODODescs;
//    }
//
//    public static void setTODOescs(String[] TODODescs) {
//        G.TODODescs = TODODescs;
//    }
//
//    public static String[] getTODOstatus() {
//        return TODOstatus;
//    }
//
//    public static void setTODOstatus(String[] TODOstatus) {
//        G.TODOstatus = TODOstatus;
//    }
//
//    public static String getTODOday() {
//        return TODOday;
//    }
//
//    public static void setTODOday(String TODOday) {
//        G.TODOday = TODOday;
//    }
}//G
