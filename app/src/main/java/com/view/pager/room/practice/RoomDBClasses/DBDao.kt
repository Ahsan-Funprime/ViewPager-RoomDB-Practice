package com.view.pager.room.practice.RoomDBClasses

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface DBDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertData(data: DBEntity)

    @Query("SELECT * FROM practice_db")
    fun getAllData() : MutableList<DBEntity>

    @Query("DELETE FROM practice_db WHERE id = :id")
    fun deleteDataByID(id: Int)
}