package com.example.newsify

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "article_table",indices = [Index(value = ["imgUrl"], unique = true)]
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
//    val author: String,
//    val content: String,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    @ColumnInfo(name = "imgUrl")
    val urlToImage: String?
)