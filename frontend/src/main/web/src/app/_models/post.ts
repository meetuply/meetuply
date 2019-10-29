import {User} from "./user";

export class BlogPost {
  blogPostId: number;
  author: User;
  blogPostTitle: string;
  blogPostContent: string;
  time: Date;
}
