import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ArticleService} from "../../services/article.service";
import {ArticleInterface} from "../../interfaces/article/article.interface";
import {articleDetailTextsConstants} from "../../const/ARTICLE_DETAIL_TEXTS";
import {FormBuilder, Validators} from "@angular/forms";
import {formControlTextsConstants} from "../../const/FORM_CONTROL_TEXTS";
import {AddCommentRequestInterface} from "../../interfaces/comment/add-comment-request.interface";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.css']
})
export class ArticleDetailComponent implements OnInit {
  public article!: ArticleInterface;

  public form = this.formBuilder.group({
    comment: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(255)]],
  });

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService,
    private formBuilder: FormBuilder,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.getArticleById(id);
  }

  public onSubmit() {
    console.log("submit");
    if(this.form.valid) {
      const comment = this.form.value.comment!;
      const addCommentRequest: AddCommentRequestInterface = {
        content: comment,
        articleId: this.article.id,
      }
      this.articleService.addComment(addCommentRequest).subscribe(
        {
          next: (response) => {
            this.form.reset();
            this.getArticleById(this.article.id);
            this.snackBar.open(articleDetailTextsConstants.SUCCESS_ADD_COMMENT, articleDetailTextsConstants.BUTTON_SNACKBAR, {});

          },
          error: (error) => {
            this.snackBar.open(articleDetailTextsConstants.ERROR_ADD_COMMENT, articleDetailTextsConstants.BUTTON_SNACKBAR, {});
          }
        }
      );
    }
  }

  private getArticleById(id: number) {
    this.articleService.getArticleById(id.toString()).subscribe((article) => {
      this.article = article;
    });
  }

  protected readonly articleDetailTextsConstants = articleDetailTextsConstants;
  protected readonly formControlTextsConstants = formControlTextsConstants;
}
