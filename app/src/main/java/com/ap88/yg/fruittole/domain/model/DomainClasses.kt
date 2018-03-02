package com.ap88.yg.fruittole.domain.model

/**
 * Created by duanlei on 2018/1/31.
 * 实体类
 */
data class BottomTabBean(val icon: Int, val title: String)

//tags
data class ProductTag(val tagName: String,
                      val tagTextColor: String,
                      val tagBgRes: Int)