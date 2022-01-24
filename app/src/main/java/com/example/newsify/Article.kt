package com.example.newsify

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(
    tableName = "article_table",indices = [Index(value = ["imgUrl"], unique = true)]
)
@Parcelize
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
): Parcelable