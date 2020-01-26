package com.example.movieinfo.data.ui.popular_movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.movieinfo.R
import com.example.movieinfo.data.api.MovieDBInterface
import com.example.movieinfo.data.api.TheMovieClient
import com.example.movieinfo.data.repository.NetworkState
import kotlinx.android.synthetic.main.activity_movie_view.*

class MovieView : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    lateinit var movieRepository: MoviePageListRepository

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    lateinit var movieAdapter: PopularMoviePagedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_view)

        swipeRefreshLayout = findViewById(R.id.swipe)

        swipeRefreshLayout.setOnRefreshListener {

            showMovieDetails()

            loadMovieDetails()
        }

        val apiService : MovieDBInterface = TheMovieClient.getClient()

        movieRepository = MoviePageListRepository(apiService)

        viewModel = getViewModel()

        movieAdapter = PopularMoviePagedListAdapter()

        showMovieDetails()

        loadMovieDetails()
    }

    private fun getViewModel(): MainActivityViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepository) as T
            }

        })[MainActivityViewModel::class.java]
    }

    private fun loadMovieDetails(){
        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progressBar.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            textViewError.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE
            swipeRefreshLayout.isRefreshing = viewModel.listIsEmpty() && it == NetworkState.LOADING

            if (!viewModel.listIsEmpty()){
                movieAdapter.setNetworkState(it)
            }
        })
    }

    private fun showMovieDetails(){
        val gridLayoutManager = GridLayoutManager(this, 3)

        movieList.layoutManager = gridLayoutManager
        movieList.setHasFixedSize(true)
        movieList.adapter = movieAdapter


        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType: Int = movieAdapter.getItemViewType(position)
                return if (viewType == movieAdapter.MOVIE_VIEW_TYPE) 1
                else 3
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.logout_menu, menu)

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        return if (item.itemId == R.id.logoutItem){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            true
        } else
            false

    }
}

