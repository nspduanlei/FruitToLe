package com.ap88.yg.fruittole.ui.activities.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ap88.yg.fruittole.R;
import com.ap88.yg.fruittole.domain.model.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by duanlei on 2018/3/22.
 *
 */
public class Test1Activity extends Activity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_1);
  }

  public void test(View view) {
    startActivity(new Intent(this, Test2Activity.class));
  }

  /**
   * 总线机制
   */
  @Override
  public void onStart() {
    super.onStart();
    Log.e("test004", "onStart--------");
    EventBus.getDefault().register(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    Log.e("test004", "onStop--------");
    //EventBus.getDefault().unregister(this);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(MessageEvent event) {
    Log.e("test004", "event---------" + event.getId());
    Toast.makeText(this, "test-------------", Toast.LENGTH_LONG).show();
  }
}
