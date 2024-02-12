package com.rugvedinamdar.sampleweather.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rugvedinamdar.sampleweather.data.model.City
import com.rugvedinamdar.sampleweather.data.repository.local.CityDao

@Database(entities = [City::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchCityDao(): CityDao

    companion object {
        /**
         * As we need only one instance of db in our app will use to store
         * This is to avoid memory leaks in android when there exist multiple instances of db
         */
        @Volatile
        private var newInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = newInstance

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        Constants.DB_NAME
                    ).build()

                    newInstance = instance
                }
                return instance
            }
        }
    }
}