package com.example.whatsviral

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taboola.android.Taboola
import com.taboola.android.TBLClassicPage
import com.taboola.android.TBLClassicUnit

class ArticleAdapter(
    private val articles: List<Article>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ARTICLE = 0
        private const val VIEW_TYPE_TABOOLA = 1
        private const val TABOOLA_POSITION = 2
    }

    private var classicPage: TBLClassicPage? = null

    override fun getItemViewType(position: Int): Int {
        return if (position == TABOOLA_POSITION) {
            VIEW_TYPE_TABOOLA
        } else {
            VIEW_TYPE_ARTICLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ARTICLE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
                ArticleViewHolder(view)
            }
            VIEW_TYPE_TABOOLA -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_taboola, parent, false)
                TaboolaViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = articles.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArticleViewHolder -> {
                val articlePosition = if (position > TABOOLA_POSITION) position - 1 else position
                val article = articles[articlePosition]
                holder.bind(article)
            }
            is TaboolaViewHolder -> {
                if (classicPage == null) {
                    classicPage = Taboola.getClassicPage(
                        "https://blog.taboola.com/",
                        "article"
                    )
                }
                holder.bind(classicPage!!)
            }
        }
    }

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.articleTitle)
        private val description: TextView = view.findViewById(R.id.articleDescription)
        private val image: ImageView = view.findViewById(R.id.articleImage)

        fun bind(article: Article) {
            title.text = article.name
            description.text = article.description
            Glide.with(image.context).load(article.thumbnail).into(image)
        }
    }

    class TaboolaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val container: View = view.findViewById(R.id.taboola_container)

        fun bind(classicPage: TBLClassicPage) {
            try {
                val taboolaUnit = container.findViewById<TBLClassicUnit>(R.id.taboola_unit)

                if (taboolaUnit != null) {
                    // Use the addUnitToPage method with correct values
                    classicPage.addUnitToPage(
                        taboolaUnit,
                        "Mid_Article",
                        "alternating-widget-without-video",
                        0, // Try 0, 1, or 2 for different placement types
                        null // No listener for now
                    )
                    taboolaUnit.fetchContent()
                }
            } catch (e: Exception) {
                println("Error setting up Taboola: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}