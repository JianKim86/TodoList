package com.jian86_android.todolist;


import android.app.Application;

public class G extends Application {
    //키보드 상태
    public static boolean g_Keybord = true;

    public static boolean isG_Keybord() {
        return g_Keybord;
    }

    public static void setG_Keybord(boolean g_Keybord) {
        G.g_Keybord = g_Keybord;
    }
}//G
