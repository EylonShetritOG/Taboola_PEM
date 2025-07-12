package com.example.whatsviral

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        thread {
            val articles = loadArticlesFromJson()
            runOnUiThread {
                recyclerView.adapter = ArticleAdapter(articles)
            }
        }
    }

    private fun loadArticlesFromJson(): List<Article> {
        val url = URL("https://s3-us-west-2.amazonaws.com/taboola-mobile-sdk/public/home_assignment/data.json")
        val connection = url.openConnection() as HttpURLConnection
        connection.connect()

        val inputStream = connection.inputStream
        val json = inputStream.bufferedReader().readText()
        val jsonArray = JSONArray(json)

        val articles = mutableListOf<Article>()
        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            val name = item.getString("name")
            val thumbnail = item.getString("thumbnail")
            val description = item.getString("description")

            articles.add(Article(name, thumbnail, description))
        }

        return articles
    }
}