package com.ap88.yg.fruittole.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.ap88.yg.fruittole.R;
import com.ap88.yg.fruittole.ui.fragments.TestWebDelegate;
import com.ap88.yg.fruittole.ui.fragments.web.WebDelegateImpl;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by duanlei on 2018/3/1.
 */

public class TestFragmentActivity extends SupportActivity {

  private int showPosition = 0;
  private FragmentManager fm;
  private FragmentTransaction ft;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_fragment);

    fm = getSupportFragmentManager();
    ft = fm.beginTransaction();

    TestWebDelegate testWebDelegate = new TestWebDelegate();
    final WebDelegateImpl webDelegate = WebDelegateImpl.Companion.create("http://192.168.7.100/#/test");


    ft.add(R.id.fl_contains, testWebDelegate, "1");
    ft.add(R.id.fl_contains, webDelegate, "2");
//    ft.hide(webDelegate);
    ft.commit();

    ft = fm.beginTransaction();
    ft.hide(webDelegate);
    ft.commit();

    Button btn1 = findViewById(R.id.btn_1);
    Button btn2 = findViewById(R.id.btn_2);

    btn1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ft = fm.beginTransaction();
        ft.show(fm.findFragmentByTag("1"));
        ft.hide(fm.findFragmentByTag("2"));
        ft.commit();
      }
    });

    btn2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ft = fm.beginTransaction();
        ft.show(fm.findFragmentByTag("2"));
        ft.hide(fm.findFragmentByTag("1"));
        ft.commit();
      }
    });
  }
}
