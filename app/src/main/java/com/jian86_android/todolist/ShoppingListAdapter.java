package com.jian86_android.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class ShoppingListAdapter extends RecyclerView.Adapter implements Page1_ItemTouchHelperCallback.ItemTuchHelpListener{

    private static int TYPE_FOOTER = 0;
    ArrayList<Page2_listItem> listItems;
    Context context;
    boolean SaveisVisible= false;
    Page2Frag_Shopping fragment;
    RecyclerView.Adapter adapter;
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if(fromPosition<0 || fromPosition> listItems.size() || toPosition<0 || toPosition> listItems.size()){return false;}
        Page2_listItem fromItem = listItems.get(fromPosition-1);
            listItems.remove(fromPosition-1);
            listItems.add(toPosition-1,fromItem);
            notifyItemMoved(fromPosition,toPosition);
        return true;

    }//onItemMove

    @Override
    public void onItemRemove(int position) {
        listItems.remove(position-1);
        notifyItemRemoved(position);
        if(listItems.size()<=0) {
            SaveisVisible = false;
            fragment.showEmptymsg();
            getCountCheck--;
            getCountCheck();
            //Toast.makeText(context, "no have", Toast.LENGTH_SHORT).show();
        }


    }

    public  interface OnStartDragListener{
        void onStartDrag(VH holder);
    }

    public ShoppingListAdapter(ArrayList<Page2_listItem> listItems, Context context, Page2Frag_Shopping fragment) {
        this.listItems = listItems;
        this.context = context;
        this.fragment = fragment;
        this.adapter = this;

    }//constructor

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        checkCnt();
        View view;
        int layout = 0;

        if(viewType == TYPE_FOOTER){
            layout = R.layout.page_list_save;
            view = LayoutInflater.from(context).inflate(layout,viewGroup,false);
            VHfooter vhHolder = new VHfooter(view,viewType);
            return vhHolder;
        }else{
            layout = R.layout.page2_recycler_view;
            view = LayoutInflater.from(context).inflate(layout,viewGroup,false);
            VH vhHolder = new VH(view,viewType);
            return vhHolder;
        }

    }//onCreateViewHolder

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int pos) {

        if(viewHolder instanceof VH) {
            SaveisVisible =false;
            VH holder = (VH) viewHolder;
            Page2_listItem item = listItems.get(pos-1);
            holder.list_tv_name.setText(item.getItemName());
            holder.list_tv_price.setText(item.getItemPrice());
            holder.list_tv_amount.setText(item.getItemAmount());
            holder.list_tv_total.setText(item.getItemTotal());
            holder.iv_ischeck.setChecked(item.isCheck());
            if (item.isCheck()) {

                lineStyle(holder.list_tv_name, holder.btn_modify);
                lineStyle(holder.list_tv_price, holder.btn_modify);
                lineStyle(holder.list_tv_amount, holder.btn_modify);
                lineStyle(holder.list_tv_total, holder.btn_modify);
            } else {
                returnTVStyle(holder.list_tv_name, holder.btn_modify);
                returnTVStyle(holder.list_tv_price, holder.btn_modify);
                returnTVStyle(holder.list_tv_amount, holder.btn_modify);
                returnTVStyle(holder.list_tv_total, holder.btn_modify);
            }
        }//VH

        if(viewHolder instanceof VHfooter) {
            VHfooter holder = (VHfooter) viewHolder;
            if( saveList() && listItems.size()!=0 ) {
                holder.btn_save.setVisibility(View.VISIBLE);
            }else {
                holder.btn_save.setVisibility(View.GONE);
            }

        }//VHfooter

    }//onBindViewHolder
    //바인드뷰에서 해당 위치값이 맨 마지막일 경우 푸터 true
    private boolean isPositionFooter(int position) {
        return position == TYPE_FOOTER;
    }

    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return TYPE_FOOTER;
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
            vhfooteritem= getLayoutPosition();
            cl_footer = itemView.findViewById(R.id.cl);
            btn_save = itemView.findViewById(R.id.list_btn_save);
        }//constructor

    }//VHfooter

    class VH extends RecyclerView.ViewHolder{

        InputMethodManager imm;
        CardView cl;
        TextView list_tv_name, list_tv_total, list_tv_price, list_tv_amount;
        ToggleButton iv_ischeck;
        Button btn_modify;

        public VH(@NonNull View itemView, int viewType) {
            super(itemView);
            imm = (InputMethodManager) (context.getSystemService(Context.INPUT_METHOD_SERVICE));
            cl = itemView.findViewById(R.id.cl);
            list_tv_name = itemView.findViewById(R.id.list_tv_name);
            list_tv_total = itemView.findViewById(R.id.list_tv_total);
            list_tv_price = itemView.findViewById(R.id.list_tv_price);
            list_tv_amount = itemView.findViewById(R.id.list_tv_amount);
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
                            returnTVStyle(list_tv_name,btn_modify);
                            returnTVStyle(list_tv_total,btn_modify);
                            returnTVStyle(list_tv_price,btn_modify);
                            returnTVStyle(list_tv_amount,btn_modify);
                            reflashRList();
                            getCountCheck--;
                            getCountCheck();
                        } else {
                            iv_ischeck.setChecked(true);
                            listItems.get(getLayoutPosition()-1).setCheck(true);
                            lineStyle(list_tv_name,btn_modify);
                            lineStyle(list_tv_total,btn_modify);
                            lineStyle(list_tv_price,btn_modify);
                            lineStyle(list_tv_amount,btn_modify);
                            getCountCheck++;
                            getCountCheck();
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

 //체크시 세부내용 str

 //체크버튼
        void ischeckBTN (boolean stateBtn){
            if(stateBtn){
                listItems.get(getLayoutPosition()-1).setCheck(true);
                lineStyle(list_tv_name,btn_modify);
                lineStyle(list_tv_total,btn_modify);
                lineStyle(list_tv_price,btn_modify);
                lineStyle(list_tv_amount,btn_modify);
                reflashRList();
                getCountCheck++;
                getCountCheck();
                if(saveList()){
                    //TODO 저장하기 만들기 / 버튼 커스텀
                    Toast.makeText(context, "save all", Toast.LENGTH_SHORT).show();
                    reflashRList();
                }else{}
            }else{
                listItems.get(getLayoutPosition()-1).setCheck(false);
                returnTVStyle(list_tv_name,btn_modify);
                returnTVStyle(list_tv_total,btn_modify);
                returnTVStyle(list_tv_price,btn_modify);
                returnTVStyle(list_tv_amount,btn_modify);
                getCountCheck--;
                getCountCheck();
                SaveisVisible = false;
                reflashRList();
            }
        }//ischeckBTN
//수정버튼 //TODO:수정하기버튼
        CustomDialog customDialog;
        void ismodifyBTN(){

            int cItemPosition = getLayoutPosition()-1;
            String hname = listItems.get(cItemPosition).getItemName();
            String hprice = listItems.get(cItemPosition).getItemPrice();
            String hamount = listItems.get(cItemPosition).getItemAmount();
            String htotal = listItems.get(cItemPosition).getItemTotal();

            customDialog = new CustomDialog(context, fragment, cItemPosition);
            // 커스텀 다이얼로그를 호출한다.
            // 커스텀 다이얼로그의 결과를 출력할 TextView를 매개변수로 같이 넘겨준다.
            customDialog.callFunction(hname, hprice, hamount, htotal);

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
    }
    void lineStyle(View v, Button b){
        if(b.getVisibility()==View.VISIBLE){b.setVisibility(View.GONE);}

        ((TextView) v).setPaintFlags( ((TextView) v).getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
        ((TextView) v).setTypeface(null,Typeface.ITALIC);
        ((TextView) v).setTextColor(ContextCompat.getColor(context,R.color.colorGray));

    }//lineStyle
    void returnTVStyle(View v, Button b){
        if(b.getVisibility()==View.GONE){b.setVisibility(View.VISIBLE);}
        ((TextView) v).setPaintFlags(0);
        ((TextView) v).setTypeface(null,Typeface.BOLD);
        ((TextView) v).setTextColor(ContextCompat.getColor(context,R.color.colorDarkGray));

    }//returnTVStyle

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
//        Message msg = new Message();
//        Bundle data= new Bundle();
//        data.putBoolean("ChangeList",true);
//        data.putInt("ListPosition",1);
//        msg.setData(data);
//        ((MainActivity)context).handler.sendMessage(msg);
    }//reflashRList()


}//CheckListAdapter
