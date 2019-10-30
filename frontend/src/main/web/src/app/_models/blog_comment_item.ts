import {BlogComment} from "./comment";

export class Blog_comment_item {
  comment: BlogComment;
  author: string;
  authorPhoto: string;
  get date() {return this.comment.time}
  get content(){ return this.comment.blogCommentContent}
  get id() {return this.comment.blogCommentId}

  constructor(comment: BlogComment, author: string, authorPhoto: string){
    this.comment = comment;
    this.author = author;
    this.authorPhoto = authorPhoto;
  }
}
