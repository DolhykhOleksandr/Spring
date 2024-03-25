package com.hillel.proxy.services;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.Source;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.request.SourcesRequest;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import com.kwabenaberko.newsapilib.models.response.SourcesResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.lang.System.out;


@Service
public class NewsService {
    private final NewsApiClient newsApiClient;
    private static final String GLORY_MESSAGE = ", 'Glory to Ukraine!!!'";

    public NewsService(@Value("${news.api.key}") String apiKey) {
        this.newsApiClient = new NewsApiClient(apiKey);
    }

    @SneakyThrows
    public List<Article> getEverything() {
        CompletableFuture<List<Article>> future = new CompletableFuture<>();
        final List<Article> articles = new ArrayList<>();
        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q("Ukraine")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        articles.addAll(response.getArticles());
                        for (Article article : articles) {
                            article.setTitle(article.getTitle() + GLORY_MESSAGE);
                        }
                        future.complete(articles);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        out.println(throwable.getMessage());
                        future.completeExceptionally(throwable);
                    }
                }
        );
        return future.get();
    }

    @SneakyThrows
    public List<String> getTopHeadlines() {
        CompletableFuture<List<String>> future = new CompletableFuture<>();
        final List<String> headlines = new ArrayList<>();
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .q("Ukraine")
                        .language("en")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        for (Article article : response.getArticles()) {
                            headlines.add(article.getTitle() + GLORY_MESSAGE);
                        }
                        future.complete(headlines);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        out.println(throwable.getMessage());
                        future.completeExceptionally(throwable);
                    }
                }
        );
        return future.get();
    }


    @SneakyThrows
    public List<Source> getSources() {
        CompletableFuture<List<Source>> future = new CompletableFuture<>();
        final List<Source> sources = new ArrayList<>();
        newsApiClient.getSources(
                new SourcesRequest.Builder()
                        .language("en")
                        .country("us")
                        .build(),
                new NewsApiClient.SourcesCallback() {
                    @Override
                    public void onSuccess(SourcesResponse response) {
                        for (Source source : response.getSources()) {
                            source.setName(source.getName() + GLORY_MESSAGE);
                        }
                        sources.addAll(response.getSources());
                        future.complete(sources);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        out.println(throwable.getMessage());
                        future.completeExceptionally(throwable);
                    }
                }
        );
        return future.get();
    }
}