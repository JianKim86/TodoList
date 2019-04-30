package com.jian86_android.todolist;

import android.content.Context;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
  //  DrawerLayout drawerLayout;
 //  NavigationView nav;
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
//        drawerLayout =findViewById(R.id.drawer_layout);
//        nav= findViewById(R.id.nav);
//
//        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.item1:break;
//                    case R.id.item2:break;
//                    case R.id.item3:break;
//                    case R.id.item4:break;
//                }//switch
//                drawerLayout.closeDrawer(nav,true);
//                return false;
//            }//onNavigationItemSelected
//        });//listener
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
//                    Fragment fragment = (Fragment) adapter.instantiateItem(pager,0);
//                    if(fragment != null && fragment instanceof Page1Frag_Todo){
//                        ((Page1Frag_Todo)fragment).hideKeyboard();
//                        ((Page1Frag_Todo)fragment).showEmptymsg();
//                    }
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

//    Handler handlerEmptyList = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            Bundle data = msg.getData();
//            boolean changeList = data.getBoolean("EmptyList");
//            int listPosition = data.getInt("ListPosition");
//
//            if(changeList) {
//                Toast.makeText(MainActivity.this, "change", Toast.LENGTH_SHORT).show();
//                Fragment fragment = (Fragment) adapter.instantiateItem(pager,listPosition);
//                if(fragment != null && fragment instanceof Page1Frag_Todo){
//                    //플레그먼트 접근 ->노티피
//                   // ((Page1Frag_Todo)fragment).notifyList();
//                   // ((Page1Frag_Todo)fragment).showEmptymsg();
//                }
//                else if(fragment != null && fragment instanceof Page2Frag_Shopping){
//                    //플레그먼트 접근 ->노티피
//                    ((Page2Frag_Shopping)fragment).notifyList();
//                   // ((Page2Frag_Shopping)fragment).showEmptymsg();
//                }
//            }
//        }//handleMessage
//    };//handler
//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//           Bundle data = msg.getData();
//           boolean changeList = data.getBoolean("ChangeList");
//           int listPosition = data.getInt("ListPosition");
//           if(changeList) {
//               //Toast.makeText(MainActivity.this, "change", Toast.LENGTH_SHORT).show();
//               Fragment fragment = (Fragment) adapter.instantiateItem(pager,listPosition);
//                   if(fragment != null && fragment instanceof Page1Frag_Todo){
//                      //플레그먼트 접근 ->노티피
//                       ((Page1Frag_Todo)fragment).notifyList();
//                   }
//                   else if(fragment != null && fragment instanceof Page2Frag_Shopping){
//                       //플레그먼트 접근 ->노티피
//                       ((Page2Frag_Shopping)fragment).notifyList();
//                   }
//               }
//        }//handleMessage
//    };//handler

//    Handler handlerTop = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            //TODO: top 설정
//            Bundle data = msg.getData();
//            boolean isfocuce = data.getBoolean("IsFocuce");
//            if(isfocuce) hideToolbar();
//            else showToolbar();
//        }//handleMessage
//    };//handlerTop

    @Override
    protected void onResume() {
        super.onResume();

    }

//    private void hideKeyboard() {
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
//        ((G)(this.getApplicationContext())).setG_Keybord(false);
//        isKeybord =  G_SET.isG_Keybord();
//
//
//    }
//    private void showKeyboard(){
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//        ((G)(this.getApplicationContext())).setG_Keybord(true);
//        isKeybord =  G_SET.isG_Keybord();
//
//    }
}//MainActivity
