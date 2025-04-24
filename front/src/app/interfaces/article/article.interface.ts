import {CommentInterface} from "../comment/comment.interface";

export interface ArticleInterface {
  id: number;
  title: string;
  content: string;
  authorName: string;
  createdAt: string;
  comments: Array<CommentInterface>;
}
