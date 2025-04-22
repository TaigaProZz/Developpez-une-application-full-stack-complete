import { Component, OnInit } from '@angular/core';
import {createArticleTextsConstants} from "../../const/CREATE_ARTICLE_TEXTS";
import {FormBuilder, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {CreateArticleRequestInterface} from "../../interfaces/article/create-article-request.interface";
import {ArticleService} from "../../services/article.service";
import {formControlTextsConstants} from "../../const/FORM_CONTROL_TEXTS";
import {ThemeService} from "../../services/theme.service";

@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.css']
})
export class CreateArticleComponent implements OnInit {
  public themes = this.themeService.getAllThemes()

  public form = this.formBuilder.group({
    themeSelected: ['', [Validators.required]],
    title: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
    content: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(255)]]
  });

  constructor(
    private articleService: ArticleService,
    private themeService: ThemeService,
    private formBuilder: FormBuilder,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
  }

  onSubmit() {
    if (this.form.valid) {
      const request = this.form.value as CreateArticleRequestInterface;
      this.articleService.createArticle(request).subscribe(
        {
          next: (response) => {
            this.snackBar.open(createArticleTextsConstants.SUCCESS_CREATE_ARTICLE, createArticleTextsConstants.BUTTON_SNACKBAR, {});
          },
          error: (error) => {
            this.snackBar.open(createArticleTextsConstants.ERROR_CREATE_ARTICLE_FAILED, createArticleTextsConstants.BUTTON_SNACKBAR, {})
          }
        }
      );
    } else {
      this.snackBar.open(createArticleTextsConstants.ERROR_CREATE_ARTICLE_FAILED, createArticleTextsConstants.BUTTON_SNACKBAR, {});
    }
  }

  protected readonly createArticleTextsConstants = createArticleTextsConstants;
  protected readonly formControlTextsConstants = formControlTextsConstants;
}
