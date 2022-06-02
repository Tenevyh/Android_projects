package com.project.android.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.project.android.criminalintent.Crime

@Database(entities = [Crime::class], version = 3)
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDataBase : RoomDatabase() {

    abstract fun crimeDao(): CrimeDao
}

    val migration_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE Crime ADD COLUMN phone TEXT NOT NULL DEFAULT ''"

            )
        }
}