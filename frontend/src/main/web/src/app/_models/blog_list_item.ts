import {BlogPost} from "./post";

export class Blog_list_item {
  post: BlogPost;
  author: string;
  authorPhoto: string;
  get date() {return this.post.time}
  get content(){ return this.post.blogPostContent}
  get title(){return this.post.blogPostTitle}
  get id() {return this.post.blogPostId}

  constructor(post: BlogPost, author: string, authorPhoto: string){
    this.post = post;
    this.author = author;
    this.authorPhoto = authorPhoto;
  }
}