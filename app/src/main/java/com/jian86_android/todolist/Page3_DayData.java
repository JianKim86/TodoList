package com.jian86_android.todolist;

public class Page3_DayData {
    //컨스트럭터로 데이를 받아서 저장 시작
    //다이알로그로 일정 받기
    private int day;
    private int hour;
    private int min;
    private String text_schedule;
    public Page3_DayData(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getText_schedule() {
        return text_schedule;
    }

    public void setText_schedule(String text_schedule) {
        this.text_schedule = text_schedule;
    }
}//Page3_DayData

