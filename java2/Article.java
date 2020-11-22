public class Article {

    String headline;
    String article;

    public Article(String headline, String article) {
        this.headline = headline;
        this.article = article;
    }

    public String getHeadline() {
        return headline;
    }

    public String getArticle() {
        return article;
    }

    @Override
    public String toString() {
        return headline + " - " + article;
    }
    
}
