package daniellopes.io.newsappstarter.ui

import android.webkit.WebViewClient
import daniellopes.io.newsappstarter.R
import daniellopes.io.newsappstarter.R.layout.activity_article
import daniellopes.io.newsappstarter.model.Article
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AbstractActivity() {

    private lateinit var article: Article

    override fun getLayout(): Int = activity_article

    override fun onInject() {
        getArticle()

        webView.apply {
            webViewClient = WebViewClient()
            article.url?.let {url ->
                loadUrl(url)
            }
        }
    }

    private fun getArticle() {
        intent.extras?.let { articleSend ->
            article = articleSend.get("article") as Article
        }
    }
}