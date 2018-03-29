package com.ap88.yg.fruittole.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by duanlei on 2018/2/1.
 * 测试fragment
 */
class TestFragment: Fragment() {

    companion object {
        private const val TODO_ID_KEY: String = "todo_id_key"
        fun newInstance(id: String): TestFragment {
            var args: Bundle = Bundle()
            args.putString(TODO_ID_KEY, id)
            var testFragment = TestFragment()
            testFragment.arguments = args
            return testFragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments!!.containsKey(TODO_ID_KEY)) {
            val todoId = arguments!!.getString(TODO_ID_KEY)
        }
    }



}