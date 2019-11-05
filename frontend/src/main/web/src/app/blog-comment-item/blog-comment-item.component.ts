import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {Blog_comment_item} from "../_models/blog_comment_item";
import {UserService} from "../_services";
import {BlogService} from "../_services/blog.service";

@Component({
  selector: 'app-blog-comment-item',
  templateUrl: './blog-comment-item.component.html',
  styleUrls: ['./blog-comment-item.component.css']
})

export class BlogCommentItemComponent implements OnInit {

  @Input() blog_comment_item: Blog_comment_item;

  @Output() itemDeleted: EventEmitter<Blog_comment_item> = new EventEmitter();
  error;

  constructor(private userService: UserService,
              private blogService: BlogService) { }

  ngOnInit() {
  }

  isAdmin(){
    return this.userService.currentUser.role.roleName==="admin";
  }

  deleteComment(){
    this.blogService.deleteBlogComment(this.blog_comment_item.comment.blogCommentId).subscribe();
    this.itemDeleted.emit(this.blog_comment_item);
  }

}
