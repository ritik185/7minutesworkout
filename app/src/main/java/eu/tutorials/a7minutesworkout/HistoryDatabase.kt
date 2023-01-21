package eu.tutorials.a7minutesworkout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [HistoryEntity::class], version = 1)


abstract class HistoryDatabase:RoomDatabase() {

    abstract fun historyDao():HistoryDao

    companion object{
        @Volatile
        private var INSTANCE:HistoryDatabase?=null


        @InternalCoroutinesApi
        fun getInstance(context: Context):HistoryDatabase{
            kotlinx.coroutines.internal.synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "history_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }


        }


}

    }