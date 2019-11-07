import {BlogComment} from "./comment";

export class BlogCommentItem {
  comment: BlogComment;
  author: string;
  authorPhoto: string;
  authorId: number;
  get date() {return this.comment.time}
  get content(){ return this.comment.blogCommentContent}
  get id() {return this.comment.blogCommentId}

  constructor(comment: BlogComment, author: string, authorPhoto: string, authorId: number){
    this.comment = comment;
    this.author = author;
    this.authorPhoto = authorPhoto;
    this.authorId=authorId;
  }
}
