import { Component, OnInit, Input } from '@angular/core';
import {BlogService} from "../_services/blog.service";

@Component({
  selector: 'app-blog-comment-item',
  templateUrl: './blog-comment-item.component.html',
  styleUrls: ['./blog-comment-item.component.css']
})

export class BlogCommentItemComponent implements OnInit {

  @Input() date: Date;
  @Input() author: string;
  @Input() authorPhoto: string;
  @Input() content: string;
  @Input() uid: number;
  error;

  constructor(private blogService: BlogService) { }

  ngOnInit() {
  }

}
