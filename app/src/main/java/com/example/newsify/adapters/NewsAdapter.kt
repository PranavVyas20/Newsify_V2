package com.example.newsify.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsify.Article
import com.example.newsify.R
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(private val fragmentType:String):RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    private var oldNewsList:List<Article> = emptyList()

    inner class ArticleViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutToInflate: Int?
        if(fragmentType == "snf"){
            layoutToInflate = R.layout.new_item_article_preview_saved_article
        }
        else{
            layoutToInflate = R.layout.new_item_article_preview
        }
       return ArticleViewHolder(LayoutInflater.from(parent.context).inflate(
            layoutToInflate,parent,false
        ))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentArticle = oldNewsList[position]

        holder.itemView.apply {
            // get and attatch the fkin title
            findViewById<TextView>(R.id.newsTitleTv).text = currentArticle.title
            // COnvert the fucking date!!
            val m_date = currentArticle.publishedAt?.let { convertDate(it) }
            findViewById<TextView>(R.id.publishedAtTv).text = m_date
            // Load image with glide
            if(currentArticle.urlToImage!=null){
                Glide.with(context).load(currentArticle.urlToImage).into(findViewById(R.id.ivArticleImage))
            }else{
                findViewById<ImageView>(R.id.ivArticleImage).apply {
                    setImageResource(R.drawable.image_not_found)
                    adjustViewBounds = false
                }

            }

            // Save article to db
            if (fragmentType !="snf"){
                findViewById<Button>(R.id.saveArticleBtn).setOnClickListener {
                    //Toast.makeText(context,currentArticle.title,Toast.LENGTH_SHORT).show()
                    onItemClickListener_saveArticle.let {
                        if (it != null) {
                            it(currentArticle)
                        }
                    }
                }
            }
            // Open article in web view
            findViewById<Button>(R.id.openArticleBtn).setOnClickListener {
                //Toast.makeText(context,currentArticle.title,Toast.LENGTH_SHORT).show()
                onItemClickListener_openArticle.let {
                    if (it != null) {
                        it(currentArticle)
                    }
                }
            }
            // Share article
            findViewById<Button>(R.id.shareArticleBtn).setOnClickListener {
                //Toast.makeText(context,currentArticle.title,Toast.LENGTH_SHORT).show()
                onItemClickListener_shareArticle.let {
                    if (it != null) {
                        it(currentArticle)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return oldNewsList.size
    }

    fun setData(newNewsList:List<Article>){
        val m_diffUtil = DiffUtil(oldNewsList,newNewsList)
        val diffResults = DiffUtil.calculateDiff(m_diffUtil)
        oldNewsList = newNewsList
        diffResults.dispatchUpdatesTo(this)
    }
    // Related to open article(web view) btn clicked
    private var onItemClickListener_openArticle:((Article) -> Unit)? = null
    fun setOnItemClickListener_openArticle(listener:(Article) -> Unit){
        onItemClickListener_openArticle = listener
    }

    //Related to save article to db clicked
    private var onItemClickListener_saveArticle:((Article)->Unit)? = null
     fun setOnItemClikListener_saveArticle(m_listener:(Article)->Unit){
         onItemClickListener_saveArticle = m_listener
    }

    // Realted to share article btn clicked
    private var onItemClickListener_shareArticle:((Article)->Unit)? = null
    fun setOnItemClikListener_shareArticle(m_listener:(Article)->Unit){
        onItemClickListener_shareArticle = m_listener
    }

    // Converting the times\zones!!
    fun String.toDate(dateFormat: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
        val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
        parser.timeZone = timeZone
        return parser.parse(this)
    }
    fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        formatter.timeZone = timeZone
        return formatter.format(this)
    }
    fun convertDate(fkedUpDate:String):String{
        val convertedDate1:String = fkedUpDate.subSequence(0,10) as String
        val convertedDate2:String = fkedUpDate.subSequence(12,19) as String
        var finalConvertedDate:String = "$convertedDate1 $convertedDate2"
        finalConvertedDate = finalConvertedDate.toDate().formatTo("dd MMM yyyy")
        return finalConvertedDate
    }


}