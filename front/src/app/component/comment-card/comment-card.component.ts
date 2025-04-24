import {Component, Input, OnInit} from '@angular/core';
import {CommentInterface} from "../../interfaces/comment/comment.interface";

@Component({
  selector: 'app-comment-card',
  templateUrl: './comment-card.component.html',
  styleUrls: ['./comment-card.component.css']
})
export class CommentCardComponent implements OnInit {
  @Input() comment!: CommentInterface;

  constructor() { }

  ngOnInit(): void {
  }

}
