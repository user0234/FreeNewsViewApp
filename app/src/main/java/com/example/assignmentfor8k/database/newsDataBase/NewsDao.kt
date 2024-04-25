package com.example.assignmentfor8k.database.newsDataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignmentfor8k.retrofit.newsApi.model.Article

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun saveArticle(articleItem: Article)

    @Delete
    suspend fun deleteArticle(articleItem: Article)

    @Query("Select * From article_table")
    fun getSavedNewsArticles(): LiveData<List<Article>?>

}