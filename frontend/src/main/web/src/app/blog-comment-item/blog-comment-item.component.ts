import { Component, OnInit, Input } from '@angular/core';
import {BlogService} from "../_services/blog.service";
import {Blog_comment_item} from "../_models/blog_comment_item";

@Component({
  selector: 'app-blog-comment-item',
  templateUrl: './blog-comment-item.component.html',
  styleUrls: ['./blog-comment-item.component.css']
})

export class BlogCommentItemComponent implements OnInit {

  @Input() blog_comment_item: Blog_comment_item;
  error;

  constructor(private blogService: BlogService) { }

  ngOnInit() {
  }

}
