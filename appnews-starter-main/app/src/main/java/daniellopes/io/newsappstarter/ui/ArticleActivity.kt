package daniellopes.io.newsappstarter.ui

import android.webkit.WebViewClient
import com.google.android.material.snackbar.Snackbar
import daniellopes.io.newsappstarter.R
import daniellopes.io.newsappstarter.R.layout.activity_article
import daniellopes.io.newsappstarter.model.Article
import daniellopes.io.newsappstarter.model.data.NewsDataSource
import daniellopes.io.newsappstarter.presenter.ViewHome
import daniellopes.io.newsappstarter.presenter.favorite.FavoritePresenter
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AbstractActivity(), ViewHome.Favorite {

    private lateinit var article: Article
    private lateinit var presenter: FavoritePresenter
    override fun getLayout(): Int = activity_article

    override fun onInject() {
        getArticle()
        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this, dataSource)

        webView.apply {
            webViewClient = WebViewClient()
            article.url?.let {url ->
                loadUrl(url)
            }
        }

        fab.setOnClickListener {
            presenter.saveArticle(article)
            Snackbar.make(it, R.string.article_saved_successful,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun getArticle() {
        intent.extras?.let { articleSend ->
            article = articleSend.get("article") as Article
        }
    }

    override fun showArticles(articles: List<Article>) {
    }
}