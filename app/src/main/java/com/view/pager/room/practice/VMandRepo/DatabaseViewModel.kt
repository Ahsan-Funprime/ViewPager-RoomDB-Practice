package com.view.pager.room.practice.VMandRepo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.view.pager.room.practice.RoomDBClasses.DBEntity
import com.view.pager.room.practice.RoomDBClasses.PracticeDatabse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseViewModel(private val context: Context): ViewModel() {

    private val _dbData = MutableLiveData<List<DBEntity>>()
    val dbData: LiveData<List<DBEntity>> get() = _dbData
    private var repositry: DatabaseRepositry? = null

    init {
        repositry = DatabaseRepositry(PracticeDatabse.getDatabase(context).stringUrlDAO)
    }

    fun insertData(name: String, size: Int){
        repositry?.insertData(name, size)
    }

    fun getAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            _dbData.postValue(repositry?.getAllData())
        }
    }
    fun delete_byId(id: Int){
        repositry?.delete_byID(id)
    }

}