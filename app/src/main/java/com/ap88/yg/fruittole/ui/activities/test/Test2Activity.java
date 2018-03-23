package com.ap88.yg.fruittole.ui.activities.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ap88.yg.fruittole.R;
import com.ap88.yg.fruittole.domain.model.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by duanlei on 2018/3/22.
 */

public class Test2Activity extends Activity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_2);
  }

  public void test(View view) {
    EventBus.getDefault().post(new MessageEvent(1));
  }
}
