package com.example.exlibris.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.exlibris.R
import com.example.exlibris.data.BookResponse
import com.example.exlibris.network.NewsNetworkClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsActivity : AppCompatActivity(), NewsListener {

    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var rvNews: RecyclerView
    private val adapter: NewsAdapter by lazy { NewsAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        setupUI()
    }

    private fun setupUI() {
        coordinatorLayout = findViewById(R.id.newsLayout)
        rvNews = findViewById(R.id.rvNews)
        rvNews.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        retrieveNews()
    }

    private fun retrieveNews() {
        NewsNetworkClient.newsApi.getNewBooks()
            .enqueue(object: Callback<List<BookResponse>> {
                override fun onResponse(
                    call: Call<List<BookResponse>>,
                    response: Response<List<BookResponse>>
                ) {
                    response.body().let {
                        if (it != null) {
                            adapter.updateNews(it)
                        }
                    }
                }

                override fun onFailure(call: Call<List<BookResponse>>, t: Throwable) {
                    Log.e("NewsActivity", "Error al obtener las novedades literarias", t)
                }

            })
    }
}