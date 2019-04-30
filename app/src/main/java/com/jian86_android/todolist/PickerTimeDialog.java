package com.jian86_android.todolist;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.OverScroller;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class PickerTimeDialog {

    final Context context;
    Page3Frag_Calendar fragment;

    Dialog dlg;
    TimePicker timePicker;
    int year,month,today, min,hour;

    public PickerTimeDialog(Context context, Page3Frag_Calendar fragment, Bundle bundle) {
        this.context = context;
        this.fragment =fragment;
//
//        bundle.putInt("day",2018);
        dlg = onCreateDialog(bundle);
        year = bundle.getInt("year");
        month = bundle.getInt("month");
        today = bundle.getInt("today");
        dlg.show();
    }

    public void mkdlg(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.time_picker_layout);
//        dlg.show();
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Point size = new Point();
//        windowManager.getDefaultDisplay().getSize(size);
//        Window window = dlg.getWindow();
//        window.setLayout((int)(size.x* 0.99f),(int)(size.y*0.52f));
//        // 커스텀 다이얼로그의 각 위젯들을 정의.

    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hourr = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(context, timeSetListener, hourr, minute,
                DateFormat.is24HourFormat(context));
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                    Toast.makeText(context, "selected time is "
//                                    + view.getHour() +
//                                    " / " + view.getMinute()
//                            , Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        hour = view.getHour();
                        min = view.getMinute();
                    }else {
                        hour = view.getCurrentHour();
                        min = view.getCurrentMinute();
                    }

                    fragment.setDateInfo(year,month,today,hour,min);

                }
            };

}//PickerTimeDialog
