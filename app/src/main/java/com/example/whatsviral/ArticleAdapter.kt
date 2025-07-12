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
        private const val VIEW_TYPE_TABOOLA_WIDGET = 1
        private const val VIEW_TYPE_TABOOLA_FEED = 2
        private const val TABOOLA_WIDGET_POSITION = 2  // Position 3
        private const val TABOOLA_FEED_POSITION = 9    // Position 10
    }

    private var classicPage: TBLClassicPage? = null

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            TABOOLA_WIDGET_POSITION -> VIEW_TYPE_TABOOLA_WIDGET
            TABOOLA_FEED_POSITION -> VIEW_TYPE_TABOOLA_FEED
            else -> VIEW_TYPE_ARTICLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ARTICLE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
                ArticleViewHolder(view)
            }
            VIEW_TYPE_TABOOLA_WIDGET -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_taboola, parent, false)
                TaboolaWidgetViewHolder(view)
            }
            VIEW_TYPE_TABOOLA_FEED -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_taboola_feed, parent, false)
                TaboolaFeedViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        // Add 2 for the Taboola widget and feed
        return articles.size + 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArticleViewHolder -> {
                // Adjust article position because we inserted Taboola items
                val articlePosition = when {
                    position < TABOOLA_WIDGET_POSITION -> position
                    position < TABOOLA_FEED_POSITION -> position - 1
                    else -> position - 2
                }
                val article = articles[articlePosition]
                holder.bind(article)
            }
            is TaboolaWidgetViewHolder -> {
                if (classicPage == null) {
                    classicPage = Taboola.getClassicPage(
                        "https://blog.taboola.com/",
                        "article"
                    )
                }
                holder.bind(classicPage!!)
            }
            is TaboolaFeedViewHolder -> {
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

    class TaboolaWidgetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val container: View = view.findViewById(R.id.taboola_container)

        fun bind(classicPage: TBLClassicPage) {
            try {
                val taboolaUnit = container.findViewById<TBLClassicUnit>(R.id.taboola_unit)

                if (taboolaUnit != null) {
                    // Widget configuration
                    classicPage.addUnitToPage(
                        taboolaUnit,
                        "Mid Article",
                        "alternating-widget-without-video",
                        0,
                        null
                    )
                    taboolaUnit.fetchContent()
                }
            } catch (e: Exception) {
                println("Error setting up Taboola Widget: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    class TaboolaFeedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val container: View = view.findViewById(R.id.taboola_feed_container)

        fun bind(classicPage: TBLClassicPage) {
            try {
                val taboolaFeedUnit = container.findViewById<TBLClassicUnit>(R.id.taboola_feed_unit)

                if (taboolaFeedUnit != null) {
                    // Feed configuration
                    classicPage.addUnitToPage(
                        taboolaFeedUnit,
                        "Feed_without_video",
                        "thumbs-feed-01",
                        2,
                        null
                    )
                    taboolaFeedUnit.fetchContent()
                }
            } catch (e: Exception) {
                println("Error setting up Taboola Feed: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}