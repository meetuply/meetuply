import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {BlogService} from "../_services/blog.service";
import {BlogListItem} from "../_models/blog-list-item";
import {UserService} from "../_services";


@Component({
  selector: 'app-blog-list-item',
  templateUrl: './blog-list-item.component.html',
  styleUrls: ['./blog-list-item.component.css']
})

export class BlogListItemComponent implements OnInit {

  @Input() blog_list_item: BlogListItem;

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
    this.blogService.deleteBlogPost(this.blog_list_item.id).subscribe();
    this.itemDeleted.emit(this.blog_list_item);
  }

}
