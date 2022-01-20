package com.example.newsify.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsify.Article

@Database(entities = [Article::class],version = 14)
abstract class ArticleDatabase:RoomDatabase() {

    abstract fun getDao():ArticleDao

    companion object{
        @Volatile
        private var INSTANCE:ArticleDatabase? = null

        fun getDatabase(context: Context):ArticleDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!= null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java,
                    "article_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}