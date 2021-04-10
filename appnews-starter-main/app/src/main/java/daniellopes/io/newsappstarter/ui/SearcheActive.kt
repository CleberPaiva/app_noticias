package daniellopes.io.newsappstarter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.util.Util
import daniellopes.io.newsappstarter.R
import daniellopes.io.newsappstarter.R.layout.activity_searche
import daniellopes.io.newsappstarter.adapter.MainAdapter
import daniellopes.io.newsappstarter.model.Article
import daniellopes.io.newsappstarter.model.data.NewsDataSource
import daniellopes.io.newsappstarter.presenter.ViewHome
import daniellopes.io.newsappstarter.presenter.search.SearchPresenter
import daniellopes.io.newsappstarter.util.UtilQueryTextListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_searche.*

class SearcheActive : AbstractActivity(), ViewHome.View {

    private val mainAdapter by lazy {
        MainAdapter()
    }

    private lateinit var presenter: SearchPresenter

    override fun getLayout(): Int = activity_searche

    override fun onInject() {

        val dataSource = NewsDataSource()
        presenter = SearchPresenter(this, dataSource)
        configRecycle()
        search()
    }

    private fun search(){
        searchNews.setOnQueryTextListener(
            UtilQueryTextListener(
                this.lifecycle
            ){
                newText ->
                newText?.let {query ->
                    if (query.isNotEmpty()){
                        presenter.search(query)
                        rvProgressBarSearch.visibility = View.VISIBLE
                    }
                }
            }
        )
    }

    private fun configRecycle(){
        with(rvSearch) {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@SearcheActive)
            addItemDecoration(
                DividerItemDecoration(
                    this@SearcheActive, DividerItemDecoration.VERTICAL
                )
            )
        }
    }
    override fun showProgressBar() {
        rvProgressBarSearch.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        rvProgressBarSearch.visibility = View.INVISIBLE
    }

    override fun showArticles(articles: List<Article>) {
        mainAdapter.differ.submitList(articles.toList())
    }
}
