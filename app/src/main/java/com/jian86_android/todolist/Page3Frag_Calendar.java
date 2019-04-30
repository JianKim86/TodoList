package com.jian86_android.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Page3Frag_Calendar extends Fragment {

    Context context;
    ImageView action_addbtn;
    RelativeLayout action_addbg;
    MaterialCalendarView materialCalendarView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page3_frag_calendar, container, false);
        context = container.getContext();
        setHasOptionsMenu(true);
        return view;
    }//onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        action_addbtn = view.findViewById(R.id.action_addbtn);
        action_addbg = view.findViewById(R.id.action_addbg);
        //dialog 창 띄우기
        action_addbtn.setOnClickListener(ClickListener);
        materialCalendarView = view.findViewById(R.id.calendarView);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(1986, 0, 1))
                .setMaximumDate(CalendarDay.from(2286, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new OneDayDecorator()
        );
        //클릭이벤트
        materialCalendarView.setOnDateChangedListener(onDateSelectedListener);

    }//onViewCreated

    OnDateSelectedListener onDateSelectedListener = new OnDateSelectedListener() {
        @Override
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
            //showTimepicDial();
            // 커스텀 다이얼로그를 호출한다.
            // 커스텀 다이얼로그의 결과를 출력할 TextView를 매개변수로 같이 넘겨준다.
            selectDate(date);
            //pickerTimeDialog.callFunction();
        }
    };
    void selectDate(CalendarDay date){
        String day =date.getYear()+ (date.getMonth()+1 )+date.getDay()+"";
        int year = date.getYear();
        int month = date.getMonth()+1;
        int today = date.getDay();
        Bundle bundle=new Bundle();
        bundle.putInt("year",year);
        bundle.putInt("month",month);
        bundle.putInt("today",today);
        PickerTimeDialog pickerTimeDialog = new PickerTimeDialog(context,Page3Frag_Calendar.this,bundle);
    }


    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_addbtn : // TODO: +버튼 눌럿을때 액션
                   // boolIsShowAddDial(true);
                    CalendarDay date = CalendarDay.today();
                    selectDate(date);
                    break;
            }//onClick
        }
        //ClickListener
    };


    void setDateInfo(int year, int month, int day,int hour,int min){
        //TODO : 날짜 시간 정보 받음 -> 일정 입력 dialog

    }//setDateInfo


    @Override
    public void onResume() {

        super.onResume();

    }//onResume





/**메뉴관련**/
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.new_list).setTitle("이벤트 모아보기");
    }//onPrepareOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_list:
               // mkNewList();
                //TODO: 액션바 매뉴 클릭시 전체 이밴트 보이기
                break;
            default:  return super.onOptionsItemSelected(item);
        }
        return true;
    }//onOptionsItemSelected

//////////////////////////////////////////////////////////////////////////////
    //오늘
public class OneDayDecorator implements DayViewDecorator {

    private CalendarDay date;

    public OneDayDecorator() {
        date = CalendarDay.today();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.calendar_todaybg));
//        view.addSpan(new RelativeSizeSpan(1.8f));
//        view.addSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorDarkGray)));
    }

    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}
    //일요일
    public class SundayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SundayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorAccent)));
        }
    }

    //토요일
    public class SaturdayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SaturdayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SATURDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorDeepBlue)));
        }
    }
//////////////////////////////////////////////////////////////////////////////

}//Page1Frag_Todo
