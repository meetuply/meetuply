import {BlogPost} from "./post";

export class BlogListItem {
  post: BlogPost;
  author: string;
  authorPhoto: string;
  authorId: number;
  get date() {return this.post.time}
  get content(){ return this.post.blogPostContent}
  get title(){return this.post.blogPostTitle}
  get id() {return this.post.blogPostId}

  constructor(post: BlogPost, author: string, authorPhoto: string, authorId:number){
    this.post = post;
    this.author = author;
    this.authorPhoto = authorPhoto;
    this.authorId=authorId;
  }
}
