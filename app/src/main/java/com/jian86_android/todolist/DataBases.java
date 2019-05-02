package com.jian86_android.todolist;

import android.provider.BaseColumns;

public class DataBases {
    public static final class CreateDB implements BaseColumns {
        public static final String TODODesc = "TODODesc";
        public static final String TODOstatu = "TODOstatu";
        public static final String TODOday = "TODOday";
        public static final String _TABLENAME0 = "todotable";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement, "
                +TODODesc+" text not null , "
                +TODOstatu+" text not null , "
                +TODOday+" text not null );";
    }
}
