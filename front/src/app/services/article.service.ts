import { Observable } from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {CreateArticleRequestInterface} from "../interfaces/article/create-article-request.interface";
import {CreateArticleResponseInterface} from "../interfaces/article/create-article-response.interface";
import {ArticleInterface} from "../interfaces/article/article.interface";
import {ArticleCardInterface} from "../interfaces/article/article-card.interface";

@Injectable( {
  providedIn: 'root'
})
export class ArticleService {
  constructor(private httpClient: HttpClient) { }

  public createArticle(createArticleRequestInterface: CreateArticleRequestInterface): Observable<CreateArticleResponseInterface> {
    return this.httpClient.post<CreateArticleResponseInterface>('api/article', createArticleRequestInterface);
  }

  public getAllArticles(): Observable<ArticleCardInterface[]> {
    return this.httpClient.get<ArticleCardInterface[]>('api/article');
  }
}
