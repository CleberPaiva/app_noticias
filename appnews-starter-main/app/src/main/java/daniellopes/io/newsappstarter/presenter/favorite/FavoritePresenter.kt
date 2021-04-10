package daniellopes.io.newsappstarter.presenter.favorite

import daniellopes.io.newsappstarter.model.Article
import daniellopes.io.newsappstarter.model.data.NewsDataSource
import daniellopes.io.newsappstarter.presenter.ViewHome

class FavoritePresenter(
    val view: ViewHome.Favorite,
    private val dataSource: NewsDataSource): FavoriteHome.Presenter {

    fun getAll(){
        this.dataSource.getAllArticle(this)
    }


    fun saveArticle(article: Article) {
        this.dataSource.saveArticle(article)
    }

    override fun onSuccess(articles: List<Article>) {
        this.view.showArticles(articles)
    }
}