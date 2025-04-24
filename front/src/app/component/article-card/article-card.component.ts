import {Component, Input, OnInit} from '@angular/core';
import {ArticleCardInterface} from "../../interfaces/article/article-card.interface";

@Component({
  selector: 'app-article-card',
  templateUrl: './article-card.component.html',
  styleUrls: ['./article-card.component.css']
})
export class ArticleCardComponent implements OnInit {
  @Input () article!: ArticleCardInterface;

  constructor() { }

  ngOnInit(): void {
  }

}
