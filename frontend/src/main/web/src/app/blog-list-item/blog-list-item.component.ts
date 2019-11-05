import { Component, OnInit, Input } from '@angular/core';
import {BlogService} from "../_services/blog.service";
import {Blog_list_item} from "../_models/blog_list_item";
import {UserService} from "../_services";


@Component({
  selector: 'app-blog-list-item',
  templateUrl: './blog-list-item.component.html',
  styleUrls: ['./blog-list-item.component.css']
})

export class BlogListItemComponent implements OnInit {

  @Input() blog_list_item: Blog_list_item;
  error;

  constructor(private userService: UserService,
              private blogService: BlogService) { }

  ngOnInit() {
  }

  isAdmin(){
    return this.userService.currentUser.role.roleName==="admin";
  }

  deletePost(){
    this.blogService.deleteBlogPost(this.blog_list_item.id);
  }

}
