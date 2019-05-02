package com.jian86_android.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class CheckListAdapter extends RecyclerView.Adapter implements Page1_ItemTouchHelperCallback.ItemTuchHelpListener{

    private static int TYPE_SAVEBTN = 0;
    ArrayList<Page1_listItem> listItems;
    Context context;
    Page1Frag_Todo fragment;

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if(fromPosition<0 || fromPosition> listItems.size() || toPosition<0 || toPosition> listItems.size()){return false;}
        Page1_listItem fromItem = listItems.get(fromPosition-1);
            listItems.remove(fromPosition-1);
            listItems.add(toPosition-1,fromItem);
            notifyItemMoved(fromPosition,toPosition);
        return true;

    }//onItemMove
    @Override
    public void onItemRemove(int position) {
        listItems.remove(position-1);
        notifyItemRemoved(position);
        getCountCheck-=1;
        getCountCheck();
        if(listItems.size()<=0) {
            reflashRList();
            fragment.showEmptymsg();
        }
    }//onItemRemove
    public  interface OnStartDragListener{
        void onStartDrag(VH holder);
    }

    public CheckListAdapter(ArrayList<Page1_listItem> listItems, Context context, Page1Frag_Todo fragment) {
        this.listItems = listItems;
        this.context = context;
        this.fragment =fragment;
        setDataforG(listItems);

    }//constructor
   // public SimpleDateFormat date= new SimpleDateFormat("yyyyMMdd");
    //리스트를 G에 저장하기위한
    private void setDataforG(ArrayList<Page1_listItem> listItems){
        if(listItems==null || listItems.size()==0){
            G.setTodoDatas(null);
            return;
        }
         G.setTodoDatas(listItems);
    }//setStatics
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        checkCnt();
        View view;
        int layout = 0;

        if(viewType == TYPE_SAVEBTN){
            layout = R.layout.page_list_save;
            view = LayoutInflater.from(context).inflate(layout,viewGroup,false);
            VHfooter vhHolder = new VHfooter(view,viewType);
            return vhHolder;
        }else{
            layout = R.layout.page1_recycler_view;
            view = LayoutInflater.from(context).inflate(layout,viewGroup,false);
            VH vhHolder = new VH(view,viewType);
            return vhHolder;
        }

    }//onCreateViewHolder

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int pos) {
        if(viewHolder instanceof VH) {
            VH holder = (VH) viewHolder;
            Page1_listItem item = listItems.get(pos-1);
            holder.tv_desc.setText(item.getDesc());
            holder.iv_ischeck.setChecked(item.isCheck());
            if (item.isCheck()) {
                holder.btn_modify.setVisibility(View.GONE);
                (holder.tv_desc).setPaintFlags( (holder.tv_desc).getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
                (holder.tv_desc).setTypeface(null,Typeface.ITALIC);
                (holder.tv_desc).setTextColor(ContextCompat.getColor(context,R.color.colorGray));

            } else {
                holder.btn_modify.setVisibility(View.VISIBLE);
                (holder.tv_desc).setPaintFlags(0);
                (holder.tv_desc).setTypeface(null,Typeface.BOLD);
                (holder.tv_desc).setTextColor(ContextCompat.getColor(context,R.color.colorDarkGray));
            }
        }//VH
        if(viewHolder instanceof VHfooter) {
            VHfooter holder = (VHfooter) viewHolder;
            if( saveList()&& listItems.size()!=0 ) {
                holder.btn_save.setVisibility(View.VISIBLE);
            }else {
                holder.btn_save.setVisibility(View.GONE);
            }

        }//VHfooter

    }//onBindViewHolder
    //바인드뷰에서 해당 위치값이 맨 마지막일 경우 푸터 true
    private boolean isPositionFooter(int position) {
        return position == TYPE_SAVEBTN;
    }

    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return TYPE_SAVEBTN;
        } else {
            int type = 10000;//listItems.get(position).getItemType();
            return type;
        }//else
    }//getItemViewType

        @Override
    public int getItemCount() {
        return listItems.size()+1;
    }//getItemCount
    int vhfooteritem;

    //////////////////////////////////////////InnerClass//////////////////////////////////////////
    class VHfooter extends RecyclerView.ViewHolder{

        CardView cl_footer;
        Button btn_save;
        public VHfooter(@NonNull View itemView, int viewType ) {
            super(itemView);
            vhfooteritem = getLayoutPosition();
            cl_footer = itemView.findViewById(R.id.cl);
            btn_save = itemView.findViewById(R.id.list_btn_save);

        }//constructor

    }//VHfooter

    class VH extends RecyclerView.ViewHolder{

        InputMethodManager imm;
        CardView cl;
        TextView tv_desc;
        ToggleButton iv_ischeck;
        Button btn_modify;
        public VH(@NonNull View itemView, int viewType) {
            super(itemView);
            imm = (InputMethodManager) (context.getSystemService(Context.INPUT_METHOD_SERVICE));
            cl = itemView.findViewById(R.id.cl);
            tv_desc = itemView.findViewById(R.id.list_tv);
            iv_ischeck = itemView.findViewById(R.id.list_iv_check);
            btn_modify = itemView.findViewById(R.id.list_btn_modify);
            cl.setOnClickListener(clickitem);
            btn_modify.setOnClickListener(clickitem);
            iv_ischeck.setOnClickListener(clickitem);
            iv_ischeck.setOnCheckedChangeListener(changeitem);

        }//constructor
 //리스너
        CompoundButton.OnCheckedChangeListener changeitem = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()){
                    case R.id.list_iv_check:
                        break;
                }//switch
            }//onCheckedChanged
        };//changeitem

        View.OnClickListener clickitem = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.cl:

                        if(iv_ischeck.isChecked()) {
                            iv_ischeck.setChecked(false);
                            listItems.get(getLayoutPosition()-1).setCheck(false);
                            returnTVStyle(tv_desc,btn_modify);
                            reflashRList();
                        } else {
                            iv_ischeck.setChecked(true);
                            listItems.get(getLayoutPosition()-1).setCheck(true);
                            lineStyle(tv_desc,btn_modify);
                            reflashRList();
                        }
                        break;
                    case R.id.list_iv_check:
                        if(iv_ischeck.isChecked()) ischeckBTN(true);
                        else ischeckBTN(false);
                        break;
                    case R.id.list_btn_modify:
                        ismodifyBTN();
                        break;
                }//switch
            }//onClick
        };//clickitem

//메소드
        void ischeckBTN (boolean stateBtn){
            if(stateBtn){
                listItems.get(getLayoutPosition()-1).setCheck(true);
                lineStyle(tv_desc,btn_modify);
                reflashRList();
                if(saveList()){

                    //TODO 저장하기 만들기 / 버튼 커스텀
                    Toast.makeText(context, "save all", Toast.LENGTH_SHORT).show();
                    reflashRList();
                }else{ }

            }else{
                listItems.get(getLayoutPosition()-1).setCheck(false);
                returnTVStyle(tv_desc,btn_modify);
                reflashRList();

            }
        }//ischeckBTN

        void ismodifyBTN(){
            final EditText et = new EditText(context);
                et.setText(tv_desc.getText());
                et.setPadding(toDp(7),toDp(7),toDp(7),toDp(7));
                et.setInputType(InputType.TYPE_CLASS_TEXT);

                et.setBackgroundColor(ContextCompat.getColor(context,R.color.colorWhite));
            new AlertDialog.Builder(context,R.style.MyCustomDialogStyle).setMessage("클릭한 항록을 수정하시겠습니까?").setView(et).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(imm != null) { hideKeybord(); }
                        if(et.getText().length()>0){
                            listItems.get(getLayoutPosition()-1).setDesc(et.getText().toString());

                            reflashRList();
                        }
                    }
                }).setNegativeButton("cencel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(imm != null) { hideKeybord(); }
                }
            }).create().show();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                et.setShowSoftInputOnFocus(true);
            }
              if(imm != null) {
                  et.requestFocus();
                  showKeybord();
              }



        }//ismodifyBTN

        //키보드 쇼
        void showKeybord(){
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
        //키보드 하이드
        void  hideKeybord(){
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
        }
        //dp이용
        public int toDp(int dp) {
            int value = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
            return value;
        }//toDp

    }//VH
    //////////////////////////////////////////InnerClass//////////////////////////////////////////

    Boolean saveList(){
        //전부 체크인지 확인
        for(int i = 0 ; i<listItems.size(); i++){
            if( !(listItems.get(i).isCheck()) ){ return false;}
        }
        return true;
    }//saveList
    int getCountCheck = 0 ;
    void checkCnt(){
        if(listItems.size()>0){
            getCountCheck = 0 ;
            for(int i = 0 ; i<listItems.size(); i++){
                if( (listItems.get(i).isCheck())) getCountCheck++;
            }
        }else getCountCheck = 0;
    }

    int requestCountCheck(){
        return getCountCheck;
    }
    void getCountCheck(){
       fragment.tvSetSum();
    }
    void reflashRList(){
        notifyDataSetChanged();
        setDataforG(listItems);

    /*    Message msg = new Message();
        Bundle data= new Bundle();
        data.putBoolean("ChangeList",true);
        data.putInt("ListPosition",0);
        msg.setData(data);
        ((MainActivity)context).handler.sendMessage(msg);*/

    }//reflashRList()
    //절취선

    void lineStyle(View v, Button b){
        b.setVisibility(View.GONE);
        ((TextView) v).setPaintFlags( ((TextView) v).getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
        ((TextView) v).setTypeface(null,Typeface.ITALIC);
        ((TextView) v).setTextColor(ContextCompat.getColor(context,R.color.colorGray));
        getCountCheck++;
        getCountCheck();

    }//lineStyle
    //원래 스타일
    void returnTVStyle(View v, Button b){
        b.setVisibility(View.VISIBLE);
        ((TextView) v).setPaintFlags(0);
        ((TextView) v).setTypeface(null,Typeface.BOLD);
        ((TextView) v).setTextColor(ContextCompat.getColor(context,R.color.colorDarkGray));
        getCountCheck--;
        getCountCheck();
    }//returnTVStyle


}//CheckListAdapter
