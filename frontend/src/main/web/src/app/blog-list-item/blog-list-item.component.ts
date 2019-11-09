import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {BlogService} from "../_services/blog.service";
import {BlogListItem} from "../_models/blogListItem";
import {UserService} from "../_services";


@Component({
  selector: 'app-blog-list-item',
  templateUrl: './blog-list-item.component.html',
  styleUrls: ['./blog-list-item.component.css']
})

export class BlogListItemComponent implements OnInit {

  @Input() blogListItem: BlogListItem;

  @Output() itemDeleted: EventEmitter<BlogListItem> = new EventEmitter();

  error;

  constructor(private userService: UserService,
              private blogService: BlogService) { }

  ngOnInit() {
  }

  isAdmin(){
    return this.userService.currentUser.role.roleName==="admin";
  }

  deletePost(){
    this.blogService.deleteBlogPost(this.blogListItem.id).subscribe();
    this.itemDeleted.emit(this.blogListItem);
  }

}
