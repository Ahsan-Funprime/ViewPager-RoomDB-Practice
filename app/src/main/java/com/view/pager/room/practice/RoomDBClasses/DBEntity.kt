package com.view.pager.room.practice.RoomDBClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "practice_db")
data class DBEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val size: Int
)
