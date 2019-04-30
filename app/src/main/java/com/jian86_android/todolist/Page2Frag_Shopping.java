package com.jian86_android.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Page2Frag_Shopping extends Fragment implements TextView.OnEditorActionListener{

    Context context;
    RecyclerView recyclerView;
    ImageView action_addbtn;
    RelativeLayout action_addbg;
    TextView emptyView,tv_sum, tv_totalSum;
    InputMethodManager imm;
    G G_SET;
    boolean isKeybord ;
    ArrayList<Page2_listItem> checklists = new ArrayList<>();
    ShoppingListAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page2_frag_shopping, container, false);
        context = container.getContext();
        setHasOptionsMenu(true);
        G G_SET =((G)(context.getApplicationContext())) ;
        isKeybord = G_SET.isG_Keybord();
        imm=(InputMethodManager)(context.getSystemService(Context.INPUT_METHOD_SERVICE));
        return view;
    }//onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //첫번째 매게변수가 이프레그먼트가 보여줄 뷰

        tv_sum =view.findViewById(R.id.sum);
        tv_totalSum =view.findViewById(R.id.tv_totalSum);
        action_addbtn = view.findViewById(R.id.action_addbtn);
        action_addbg = view.findViewById(R.id.action_addbg);
        emptyView = view.findViewById(R.id.empty_view);
        recyclerView = view.findViewById(R.id.check_addList_recyclerview);
        showEmptymsg();
        tvSetSum();
        adapter = new ShoppingListAdapter(checklists, context,this);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new Page1_ItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        //dialog 창 띄우기
        action_addbtn.setOnClickListener(ClickListener);

//et리스너

//btn 리스너

//        btn_new.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //저장할지 물어보기 물어보기
//                //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
//                //String fileName = "IMG_"+sdf.format(new Date())+".jpg";
//                //새로시작
//                if(checklists.size()>0){
//                    // 다시작성할지 묻는 다이알로그 필요
//                    new AlertDialog.Builder(context,R.style.MyCustomDialogStyle).setMessage("기존의 리스트들은 삭제됩니다.\n새로운 리스트를 만드시겠습니까?").setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            checklists.clear();
//                            adapter.notifyDataSetChanged();
//                        }
//                    }).setNegativeButton("cencel",null).create().show();
//
//                }//if
//            }
//        });// btn_new
//        btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(et.getText().length()>0) {
//                    addlist();
//                    //초기화
//                    init();
//                    hideKeyboard();
//                } else {notAdd();}
//            }//onClick
//        });//btn_add
//
////iv 리스너
//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(checklists.size()==0){
//                }
//                if(et.getText().length()>0) {
//                    addlist();
//                    //초기화
//                    init();
//                    hideKeyboard();
//                } else { notAdd(); }
//            }//onClick
//        });

    }//onViewCreated

    //클릭시 키보드 어팬다운
    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.action_addbtn :
                    //Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                    boolIsShowAddDial(true);
                    break;
            }//onClick
        }
        //ClickListener
    };

    void notifyList(){ adapter.notifyDataSetChanged(); }
    void notAdd(){ ((MainActivity)getActivity()).hideToolbar();}
    //상단 카운터
    String saveStringtv_sum, saveStringtv_sum_total;
    void tvSetSum(){
        int totalCount =0;
        int clickItemCount=0;
        if(checklists.size()<=0){
            totalCount=0;
            clickItemCount=0;
        }else{
            totalCount =adapter.getItemCount()-1;
            clickItemCount= adapter.requestCountCheck();
        }
        int priceSum =0;
        if (totalCount<=0){totalCount = 0;}
        if (clickItemCount<=0){clickItemCount = 0;}

        if (totalCount<=0){totalCount = 0;}
        if (clickItemCount<=0){clickItemCount = 0;}
        for(Page2_listItem i : checklists){
            if(i.isCheck())  priceSum += Integer.parseInt(i.getItemTotal());
        }
//        clickItemCount = totalCount-clickItemCount;
        saveStringtv_sum =clickItemCount+" / "+totalCount;
        saveStringtv_sum_total="￦ "+priceSum;
        tv_sum.setText(saveStringtv_sum);
        tv_totalSum.setText(saveStringtv_sum_total);
    }
    //새로운 리스트만들기

    void mkNewList(){

        if(checklists.size()>0){
            // 다시작성할지 묻는 다이알로그 필요
            new AlertDialog.Builder(context,R.style.MyCustomDialogStyle).setMessage("기존의 리스트들은 삭제됩니다.\n새로운 리스트를 만드시겠습니까?").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    checklists.clear();
                    adapter.getCountCheck = 0;
                    tvSetSum();
                    adapter.notifyDataSetChanged();
                    boolIsShowAddDial(true);
                }
            }).setNegativeButton("cencel",null).create().show();

        }//if
        else{ boolIsShowAddDial(true);}
    }


    void addlist(){
        //리사이클 뷰에 추가하기
        Page2_listItem item = customDialog.settingItem();
        if(item !=null) {
            checklists.add(item);
            showEmptymsg();
            notifyList();
            tvSetSum();
        }
        ((MainActivity)getActivity()).showToolbar();

    }

    void moveFocuse(){
       // ll.requestFocus();
       // btn_layout.setVisibility(View.GONE);
    }
    void setFocuseET(){
        //et.requestFocus();
     //   btn_layout.setVisibility(View.VISIBLE);
    }

    public void showEmptymsg(){
        if (checklists.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }//showEmptymsg

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
//        if(v.getId() == R.id.check_addList_et && actionId == EditorInfo.IME_ACTION_DONE) {
//            if (et.getText().length() > 0) {
//                addlist();
//                init();
//                hideKeyboard();
//            } else notAdd();
//
//        }
        return false;

    }//onEditorAction
    CustomDialog customDialog;
    void showAddDiallog(){
        // 커스텀 다이얼로그를 생성한다. 사용자가 만든 클래스이다.
        customDialog = new CustomDialog(context, this);
        // 커스텀 다이얼로그를 호출한다.
        // 커스텀 다이얼로그의 결과를 출력할 TextView를 매개변수로 같이 넘겨준다.
        customDialog.callFunction();

    }//show AddDiallog
    boolean isShowAddDial;
    void boolIsShowAddDial(boolean dial){
        showAddDiallog();
        ((MainActivity)getActivity()).hideToolbar();
//        Message msg = new Message();
//        Bundle data= new Bundle();
//        isShowAddDial=dial;
//        data.putBoolean("IsFocuce",dial);
//        msg.setData(data);
//        ((MainActivity)getActivity()).handlerTop.sendMessage(msg);

    }
    public void hideKeyboard() {
        moveFocuse();
        //  imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
      //  imm.hideSoftInputFromWindow(et.getWindowToken(),0);

        ((G)(context.getApplicationContext())).setG_Keybord(false);
        isKeybord = G_SET.isG_Keybord();
    }
    public void showKeyboard(){
        setFocuseET();
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        ((G)(context.getApplicationContext())).setG_Keybord(true);
        isKeybord = G_SET.isG_Keybord();
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(getActivity()!=null){
            if(isVisibleToUser)
            {
                tv_sum.setText(saveStringtv_sum);
                tv_totalSum.setText(saveStringtv_sum_total);
                // 세번째 페이지 사용자 눈에 보임
            }
            else
            {
                tv_sum.setText(saveStringtv_sum);
                tv_totalSum.setText(saveStringtv_sum_total);
                //현재페이지
            }
        }
        super.setUserVisibleHint(isVisibleToUser);


    }//setUserVisibleHint

    @Override
    public void onResume() {

        tv_sum.setText(saveStringtv_sum);
        tv_totalSum.setText(saveStringtv_sum_total);
        super.onResume();

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.new_list).setTitle("새로운 쇼핑리스트 만들기");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_list:
                mkNewList();
                break;
                default:  return super.onOptionsItemSelected(item);
        }
       return true;
    }
}//Page1Frag_Todo
