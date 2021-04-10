package daniellopes.io.newsappstarter.presenter

import daniellopes.io.newsappstarter.model.Article

interface ViewHome {

    interface View {
        fun showProgressBar()
        fun showFailure(message: String)
        fun hideProgressBar()
        fun showArticles(articles: List<Article>)
    }
}