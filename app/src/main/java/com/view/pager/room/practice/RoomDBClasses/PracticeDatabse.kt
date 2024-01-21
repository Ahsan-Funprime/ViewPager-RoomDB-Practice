package com.view.pager.room.practice.RoomDBClasses

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DBEntity::class], version = 1)
abstract class PracticeDatabse : RoomDatabase(){

    abstract val stringUrlDAO: DBDao

    companion object {
        @Volatile
        private var INSTANCE: PracticeDatabse? = null

        fun getDatabase(context: Context): PracticeDatabse {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PracticeDatabse::class.java,
                    "practice_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}