package daniellopes.io.newsappstarter.model.data

import android.content.Context
import daniellopes.io.newsappstarter.model.Article
import daniellopes.io.newsappstarter.model.db.ArticleDatabase
import daniellopes.io.newsappstarter.network.RetrofitInstance
import daniellopes.io.newsappstarter.presenter.favorite.FavoriteHome
import daniellopes.io.newsappstarter.presenter.news.NewsHome
import daniellopes.io.newsappstarter.presenter.search.SearchHome
import kotlinx.coroutines.*

class NewsDataSource(context: Context) {

    private var db: ArticleDatabase = ArticleDatabase(context)
    private var newsRepository: NewsRepository = NewsRepository(db)

    fun getBreakingNews(callback: NewsHome.Presenter){
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.getBreakingNews("br")
            if (response.isSuccessful) {
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)
                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    fun searchNews(term: String, callback: SearchHome.Presenter){
        GlobalScope.launch (Dispatchers.Main){
            val response = RetrofitInstance.api.searchNews(term)
            if (response.isSuccessful){
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)
                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    fun saveArticle(article: Article){
        GlobalScope.launch (Dispatchers.Main){
            newsRepository.updateInsert(article)
        }
    }

    fun getAllArticle(callback: FavoriteHome.Presenter){
        var allArticles: List<Article>
        CoroutineScope(Dispatchers.IO).launch {
            allArticles = newsRepository.getAll()

            withContext(Dispatchers.Main){
                callback.onSuccess(allArticles)
            }
        }
    }

    fun deleteArticles(article: Article?){
        GlobalScope.launch(Dispatchers.Main) {
            article?.let {articleSafe ->
                newsRepository.delete(articleSafe)
            }
        }
    }
}
