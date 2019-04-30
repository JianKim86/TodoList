package com.jian86_android.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CustomDialog {
    final Context context;
    Page2Frag_Shopping fragment;

    Dialog dlg;
    EditText check_add_goods_et;
    EditText check_add_price_et;
    EditText check_add_amount_et;
    TextView check_add_sum_tv;
    Button check_add_btn_send;
    Button check_add_btn_back;
    String name;
    String price;
    String amount;
    String total;
    int coutPosition ;
    public CustomDialog(Context context, Page2Frag_Shopping fragment) {
        this.context = context;
        this.fragment =fragment;
        dlg = new Dialog(context);

    }

    public CustomDialog(Context context, Page2Frag_Shopping fragment,int position) {
        this.context = context;
        this.fragment = fragment;
        coutPosition =position;
        dlg = new Dialog(context);
    }

    public void mkdlg(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.page2_frag_shopping_add);
        dlg.show();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        Window window = dlg.getWindow();
        window.setLayout((int)(size.x* 0.99f),(int)(size.y*0.52f));
        // 커스텀 다이얼로그의 각 위젯들을 정의.
        check_add_goods_et = (EditText) dlg.findViewById(R.id.check_add_goods_et);
        check_add_price_et = (EditText) dlg.findViewById(R.id.check_add_price_et);
        check_add_amount_et = (EditText) dlg.findViewById(R.id.check_add_amount_et);
        check_add_sum_tv = (TextView) dlg.findViewById(R.id.check_add_sum_tv);
        check_add_btn_send = (Button) dlg.findViewById(R.id.check_add_btn_send);
        check_add_btn_back = (Button) dlg.findViewById(R.id.check_add_btn_back);
    }

    //수정
    public void callFunction(String hname, String hprice, String hamount, String htotal){

        mkdlg();
        check_add_goods_et.setText(hname);
        check_add_price_et.setText(hprice);
        check_add_amount_et.setText(hamount);
        check_add_sum_tv.setText(htotal);

        check_add_btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = check_add_goods_et.getText().toString();
                price = check_add_price_et.getText().toString();
                amount = check_add_amount_et.getText().toString();
                total = check_add_sum_tv.getText().toString();

                //TODO : 확인시 항목이 없으면 저장 x 얼럿창 띄우기
                if(name.equals("")) {dlg.dismiss(); fragment.notAdd(); return;}
                modifyItem();
            }
        });
        check_add_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
//자동 입력 리스너
        check_add_amount_et.addTextChangedListener(textWatcher);
        check_add_price_et.addTextChangedListener(textWatcher);

    }

    public void callFunction(){
        mkdlg();
        check_add_btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = check_add_goods_et.getText().toString();
                price = check_add_price_et.getText().toString();
                amount = check_add_amount_et.getText().toString();
                total = check_add_sum_tv.getText().toString();

                //TODO : 확인시 항목이 없으면 저장 x 얼럿창 띄우기
                if(name.equals("")) {dlg.dismiss(); fragment.notAdd(); return;}
                    fragment.addlist();
//
//                 /*  //메인으로 헨들러를 보내서  에드 리스트 호출
//                    Message msg = new Message();
//                    Bundle data= new Bundle();
//                    data.putBoolean("AddList_page2",true);
//                    msg.setData(data);
//                    ((MainActivity)context).handler_AddList_page2.sendMessage(msg);*/


            }
        });
        check_add_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그를 종료한다.
                fragment.notAdd();
                dlg.dismiss();
            }
        });
//자동 입력 리스너
        check_add_amount_et.addTextChangedListener(textWatcher);
        check_add_price_et.addTextChangedListener(textWatcher);
    }//callFunction


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            upDate(true);
        }
    };

    boolean updating =false;
    void upDate( boolean multiply){
        if(updating){return;}
        if(check_add_price_et==null && check_add_amount_et ==null)return;
        String price_et = check_add_price_et.getText().toString();
        String amount_et = check_add_amount_et.getText().toString();
        updating = true;
        int price;
        int amount;
        if(price_et.equals("")) price = 0;
        else price= Integer.parseInt(price_et);
        if(amount_et.equals("")) amount =0;
        else amount = Integer.parseInt(amount_et);

        if(price !=0 && amount !=0) {
            check_add_sum_tv.setText((price*amount)+"");
        } else if (price !=0 ) {
            price= Integer.parseInt(check_add_price_et.getText().toString());
            check_add_sum_tv.setText((price)+"");
        }
        else check_add_sum_tv.setText(0+"");
        updating = false;
    }//sumprice



    void modifyItem(){
        Page2_listItem item = fragment.adapter.listItems.get(coutPosition);
        if(price.equals("")||amount.equals("")||total.equals("")||price.equals("0")||amount.equals("0")||total.equals("0")) item.setGiveIn(false);
      //  if(name.equals("")){dlg.dismiss();}
//        else {
            item.setItemName(name);
            item.setItemPrice(price);
            item.setItemAmount(amount);
            item.setItemTotal(total);
            fragment.adapter.notifyDataSetChanged();
            dlg.dismiss();
//        }
    }
    Page2_listItem  settingItem(){
        Page2_listItem item = new Page2_listItem();
       // if(name.equals("")){dlg.dismiss(); return null;}
        item.setItemName(name);
        if(price.equals("")||amount.equals("")||total.equals("")||price.equals("0")||amount.equals("0")||total.equals("0")) item.setGiveIn(false);
        item.setItemPrice(price);
        item.setItemAmount(amount);
        item.setItemTotal(total);
        dlg.dismiss();

        return item;
    }//settingItem
}//CustomDialog
