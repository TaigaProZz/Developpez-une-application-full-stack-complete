import { Component, OnInit } from '@angular/core';
import {feedTextsConstants} from "../../const/FEED_TEXTS";
import {ArticleInterface} from "../../interfaces/article/article.interface";
import {ArticleService} from "../../services/article.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {
  protected readonly feedTextsConstants = feedTextsConstants;
  public feedSortingLatest: boolean = false;
  public articles: ArticleInterface[] = [];

  constructor(
    private articleService: ArticleService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.articleService.getAllArticles().subscribe((articles: ArticleInterface[]) => {
      this.articles = articles;
    });
  }

  onArticleClick(article: ArticleInterface) {
    this.router.navigate(['/article-detail', article.id]);
  }

  onCreateArticleClick() {
    this.router.navigate(['/create-article']);
  }

  onSortClick() {
    this.feedSortingLatest = !this.feedSortingLatest;
    this.articles.sort((a, b) => {
      if (this.feedSortingLatest) {
        return new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime();
      } else {
        return new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime();
      }
    });
  }
}
