package com.jian86_android.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Page1Frag_Todo extends Fragment implements TextView.OnEditorActionListener {
    private Context context;
    LinearLayout ll;
    EditText et;
    ImageView iv;
    ImageView action_addbtn;
    RelativeLayout action_addbg;
    RecyclerView recyclerView;
    TextView emptyView, tv_sum;
    InputMethodManager imm;
    G G_SET;
    boolean isKeybord ;
    ArrayList<Page1_listItem> checklists = new ArrayList<>();
    CheckListAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       //view 생성
        View view = inflater.inflate(R.layout.page1_frag_todo, container, false);
        context = container.getContext();
        setHasOptionsMenu(true);
        G_SET = ((G)(context.getApplicationContext()));
        isKeybord = G_SET.isG_Keybord();
        imm=(InputMethodManager)(context.getSystemService(Context.INPUT_METHOD_SERVICE));

        return view;
    }//onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //첫번째 매게변수가 이프레그먼트가 보여줄 뷰
        ll = view.findViewById(R.id.ll);
        tv_sum =view.findViewById(R.id.sum);
        et = view.findViewById(R.id.check_addList_et);
        iv = view.findViewById(R.id.check_addList_iv);
        action_addbg = view.findViewById(R.id.action_addbg);
        emptyView = view.findViewById(R.id.empty_view);
        recyclerView = view.findViewById(R.id.check_addList_recyclerview);
        action_addbtn= view.findViewById(R.id.action_addbtn);
        showEmptymsg();
        tvSetSum();
        adapter = new CheckListAdapter(checklists, context, this);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new Page1_ItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        //클릭
        ll.setOnClickListener(ClickListener);
        action_addbtn.setOnClickListener(ClickListener);
//et리스너
        et.setOnEditorActionListener(this);


//btn 리스너

//iv 리스너
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et.getText().length()>0) {
                    addlist();
                  //초기화
                   init();
                } else { notAdd(); }
            }//onClick
        });

//recyclerView 리스너

    }//onViewCreated



//클릭시 키보드 어팬다운
    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.ll :
                    break;
                case R.id.action_addbtn :
                    clickAddBtn();
                    break;
            }
        /*    Message msg = new Message();
            Bundle data= new Bundle();
            data.putBoolean("IsFocuce",true);
            msg.setData(data);
            ((MainActivity)getActivity()).handlerTop.sendMessage(msg);*/

        }//onClick
    };//ClickListener

//    //메소드
//    void setSum (int getCountCheck){
//        int totalCount =adapter.getItemCount()-1;
//        int clickItemCount = getCountCheck;
//        if (totalCount<=0){totalCount = 0;}
//        if (clickItemCount<=0){clickItemCount = 0;}
//        tv_sum.setText(clickItemCount+" / "+totalCount);
//    }
//
    String saveStringtv_sum;
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

        if (totalCount<=0){totalCount = 0;}
        if (clickItemCount<=0){clickItemCount = 0;}
        saveStringtv_sum = clickItemCount+" / "+totalCount;
        tv_sum.setText(saveStringtv_sum);

    }

    void init(){ et.setText(""); }
    void notAdd(){Toast.makeText(context, "할 일을 입력하세요", Toast.LENGTH_SHORT).show(); hideKeyboard(); }
    void addlist(){
        //리사이클 뷰에 추가하기
        Page1_listItem item = new Page1_listItem(et.getText().toString());
        checklists.add(item);
        adapter.notifyDataSetChanged();
        ((MainActivity)getActivity()).showToolbar();
        hideKeyboard();
        finishAddEt();
        tvSetSum();

    }

    public void moveFocuse(){
        ll.requestFocus();
      //  btn_layout.setVisibility(View.GONE);

    }
    public void setFocuseET(){
        et.requestFocus();
      //  btn_layout.setVisibility(View.VISIBLE);
    }
    public void clickAddBtn(){
        setFocuseET();
        showKeyboard();
        action_addbtn.setVisibility(View.GONE);
        action_addbg.setVisibility(View.GONE);
        ll.setVisibility(View.VISIBLE);
        ((MainActivity)getActivity()).hideToolbar();

    }

    public void finishAddEt(){
        moveFocuse();
        action_addbtn.setVisibility(View.VISIBLE);
        action_addbg.setVisibility(View.VISIBLE);
        ll.setVisibility(View.GONE);
        ((MainActivity)getActivity()).showToolbar();
    }


    public void hideKeyboard() {
        moveFocuse();
      //  imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
        imm.hideSoftInputFromWindow(et.getWindowToken(),0);

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

    void notifyList(){ adapter.notifyDataSetChanged(); }
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

        if(v.getId() == R.id.check_addList_et && actionId == EditorInfo.IME_ACTION_DONE) {
            if (et.getText().length() > 0) {
                addlist();
                init();
            } else notAdd();
        }
        return false;

    }//onEditorAction

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(getActivity()!=null){
            if(isVisibleToUser)
            {
                tv_sum.setText(saveStringtv_sum);
                finishAddEt();
                hideKeyboard();
                //두번째 페이지 사용자 눈에 보임
                init();
            }
            else
            {
                tv_sum.setText(saveStringtv_sum);
                hideKeyboard();
                //두번째 페이지 사용자 눈에 안보임
            }
        }
        super.setUserVisibleHint(isVisibleToUser);


    }//setUserVisibleHint

    @Override
    public void onPause() {
        init();
        finishAddEt();
        hideKeyboard();
        tv_sum.setText(saveStringtv_sum);
        super.onPause();
    }



    //new list
    void mkNewList(){

        if(checklists.size()>0){
            // 다시작성할지 묻는 다이알로그 필요
            new AlertDialog.Builder(context,R.style.MyCustomDialogStyle).setMessage("기존의 리스트들은 삭제됩니다.\n새로운 리스트를 만드시겠습니까?").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    checklists.clear();
                    adapter.getCountCheck = 0;
                    tvSetSum();
                    clickAddBtn();
                    adapter.notifyDataSetChanged();
                }
            }).setNegativeButton("cencel",null).create().show();

        }//if
        else{clickAddBtn();}
    }
    //add list
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.new_list).setTitle("새로운 체크리스트 만들기");
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
