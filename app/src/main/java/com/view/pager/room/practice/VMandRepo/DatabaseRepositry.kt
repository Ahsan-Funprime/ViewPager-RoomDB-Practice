package com.view.pager.room.practice.VMandRepo

import android.util.Log
import com.view.pager.room.practice.RoomDBClasses.DBDao
import com.view.pager.room.practice.RoomDBClasses.DBEntity

class DatabaseRepositry(private val dbDao: DBDao) {

    fun insertData(name: String, size: Int){
        val stringEntity = DBEntity(name = name, size = size)
        dbDao.insertData(stringEntity)
        Log.e("DownloadRepoLogs","Insert DB: "+stringEntity)
    }

    fun getAllData():MutableList<DBEntity> {
        return dbDao.getAllData()
    }

    fun delete_byID(id: Int){
        dbDao.deleteDataByID(id)
    }
}