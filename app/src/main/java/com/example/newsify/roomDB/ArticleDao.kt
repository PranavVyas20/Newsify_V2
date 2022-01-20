package com.example.newsify.roomDB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsify.Article

@Dao
interface ArticleDao {
    // onConflict - if the article that we are inserting the db already exists it just replaces it !
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article):Long // returns an id of the article that was inserted

    @Query("SELECT * FROM article_table")
    fun getAllArticles():LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}