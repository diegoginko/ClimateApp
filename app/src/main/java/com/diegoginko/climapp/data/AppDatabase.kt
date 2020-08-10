package com.diegoginko.climapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.diegoginko.climapp.utilities.DATABASE_NAME
import com.diegoginko.climapp.workers.WeatherDatabaseWorker

@Database(entities = [Ciudad::class] ,version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    //Dao
    abstract fun ciudadDao(): CiudadDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Crea y agrega la configuracion inicial a la base para que no este vacia
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<WeatherDatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                })
                //.fallbackToDestructiveMigration()
                .build()
        }
    }
}