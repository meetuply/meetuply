import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {BlogCommentItem} from "../_models/blogCommentItem";
import {UserService} from "../_services";
import {BlogService} from "../_services/blog.service";

@Component({
  selector: 'app-blog-comment-item',
  templateUrl: './blog-comment-item.component.html',
  styleUrls: ['./blog-comment-item.component.css']
})

export class BlogCommentItemComponent implements OnInit {

  @Input() blogCommentItem: BlogCommentItem;

  @Output() itemDeleted: EventEmitter<BlogCommentItem> = new EventEmitter();
  error;

  constructor(private userService: UserService,
              private blogService: BlogService) { }

  ngOnInit() {
  }

  isAdmin(){
    return this.userService.currentUser.role.roleName==="admin";
  }

  deleteComment(){
    this.blogService.deleteBlogComment(this.blogCommentItem.comment.blogCommentId).subscribe();
    this.itemDeleted.emit(this.blogCommentItem);
  }

}
