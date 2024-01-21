package com.view.pager.room.practice

import androidx.lifecycle.MutableLiveData

object CompanionClass {

    var selected_items_list = mutableListOf<Int>()
    var unSelected_items_list = MutableLiveData<Int>()

    var longPressValue = MutableLiveData<Boolean>()

    var notify_ = MutableLiveData<Boolean>()

    var selectAll = false
}