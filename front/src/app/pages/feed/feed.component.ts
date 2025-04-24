import { Component, OnInit } from '@angular/core';
import {feedTextsConstants} from "../../const/FEED_TEXTS";
import {ArticleService} from "../../services/article.service";
import {Router} from "@angular/router";
import {ArticleCardInterface} from "../../interfaces/article/article-card.interface";

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {
  protected readonly feedTextsConstants = feedTextsConstants;
  public feedSortingLatest: boolean = false;
  public articles: ArticleCardInterface[] = [];

  constructor(
    private articleService: ArticleService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.articleService.getAllArticles().subscribe((articles: ArticleCardInterface[]) => {
      this.articles = articles;
    });
  }

  onArticleClick(article: ArticleCardInterface) {
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
