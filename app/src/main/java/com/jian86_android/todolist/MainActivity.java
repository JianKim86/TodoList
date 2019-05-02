package com.jian86_android.todolist;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
  //  DrawerLayout drawerLayout;
 //  NavigationView nav;
    String sort = "userid";
    private DbOpenHelper mDbOpenHelper;
    Toolbar toolbar;
    TabLayout tab;
    ViewPager pager;
    DoListAdapter adapter;
    final String checkListSubtitle ="오늘의 할 일을 리스트로 만듭니다";
    final String shoppingListSubtitle ="무엇을 쇼핑할 지 리스트로 만듭니다";
    final String etcListSubtitle ="나만의 일정 달력을 만듭니다";

  //  InputMethodManager imm;
    boolean isTbar = true;
//    boolean isKeybord;
//    G G_SET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        imm=(InputMethodManager)(getSystemService(Context.INPUT_METHOD_SERVICE));
//        G_SET = ((G)(this.getApplicationContext()));
//        isKeybord =  G_SET.isG_Keybord();


//1.set up tab
        tab =findViewById(R.id.tab);
//2.set up toolbar
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//3. set up pager
        pager =findViewById(R.id.pager);
        adapter = new DoListAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);
//4.set up nav

//5.setup subtitle
        getSupportActionBar().setTitle("Todo");
        getSupportActionBar().setSubtitle(checkListSubtitle);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                getSupportActionBar().setTitle(tab.getText());
                if(!isTbar) showToolbar();
                if(tab.getText().equals("TODO")){
                    getSupportActionBar().setSubtitle(checkListSubtitle);

                }
                if(tab.getText().equals("SHOPPING")){ getSupportActionBar().setSubtitle(shoppingListSubtitle);  }
                if(tab.getText().equals("CALENDAR")){ getSupportActionBar().setSubtitle(etcListSubtitle);  }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(!isTbar) showToolbar();
                Fragment fragment = (Fragment) adapter.instantiateItem(pager,tab.getPosition());
                if(fragment != null && fragment instanceof Page1Frag_Todo){
                    ((Page1Frag_Todo)fragment).hideKeyboard();
                    ((Page1Frag_Todo)fragment).showEmptymsg();
                }
            }
        });//listener
      //DB
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();
        showDatabase(sort);
    }//onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu,menu);
      /* String save="";
        if(pager.getCurrentItem()==0){
            save ="새로운 체크리스트 만들기";
        } if(pager.getCurrentItem()==1){
            save ="새로운 쇼핑리스트 만들기";
        }if(pager.getCurrentItem()==2){
            save ="새로운 메모 만들기";
        }
        menu.add(0, 0, 0, save);*/
        return super.onCreateOptionsMenu(menu);
    }//onCreateOptionsMenu

    //TODO 메뉴 커스텀
  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.new_list:
               if(pager.getCurrentItem()==0){
                   item.setTitle("새로운 체크리스트 만들기");
               }else if(pager.getCurrentItem()==1){
               item.setTitle("새로운 쇼핑리스트 만들기");

           }

               break;
       }//switch

        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected*/


    void hideToolbar() {if(isTbar){getSupportActionBar().hide(); isTbar = false;} }
    void showToolbar() {if(!isTbar){getSupportActionBar().show(); isTbar = true;} }


    @Override
    protected void onResume() {
        super.onResume();



    }
    public SimpleDateFormat date= new SimpleDateFormat("yyyy-MM-dd");
    public void showDatabase(String sort){
        Cursor iCursor = mDbOpenHelper.sortColumn(sort);
        Log.d("showDatabase", "DB Size: " + iCursor.getCount());
       // G.getTodoDatas().clear();
        ArrayList<Page1_listItem>gSetTodo= new ArrayList<>();
        while(iCursor.moveToNext()){
            String tempIndex = iCursor.getString(iCursor.getColumnIndex("_id"));
            String TODODesc = iCursor.getString(iCursor.getColumnIndex("TODODesc"));
            String TODOstatu = iCursor.getString(iCursor.getColumnIndex("TODOstatu"));
            String TODOday = iCursor.getString(iCursor.getColumnIndex("TODOday"));
            Date today = new Date();
            String day = date.format(today);
            if(TODOday.equals(day)){
                Page1_listItem p = new Page1_listItem(TODODesc,TODOstatu.equals("Y")?true:false);
                gSetTodo.add(p);
            }
        }
        G.setTodoDatas(gSetTodo);
    }
    @Override
    protected void onPause() {

        super.onPause();
        saveDB();
        Toast.makeText(this, "종료", Toast.LENGTH_SHORT).show();

    }

    //sqlite에 저장
    private void saveDB(){

        mDbOpenHelper.open();
        mDbOpenHelper.deleteAllColumns();
        Date today = new Date();
        String day = date.format(today);
        if(G.getTodoDatas()!=null&&G.getTodoDatas().size()!=0){

            for(Page1_listItem p : G.getTodoDatas()) {
                mDbOpenHelper.insertColumn(p.getDesc(), (p.isCheck())?"Y":"N",day);
            }

        }
       if(G.getTodoDatas()!=null&&G.getTodoDatas().size()!=0) G.getTodoDatas().clear();
        G.setTodoDatas(null);
    }
}//MainActivity
