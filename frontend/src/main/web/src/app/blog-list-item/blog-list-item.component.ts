import { Component, OnInit, Input } from '@angular/core';
import {BlogService} from "../_services/blog.service";


@Component({
  selector: 'app-blog-list-item',
  templateUrl: './blog-list-item.component.html',
  styleUrls: ['./blog-list-item.component.css']
})

export class BlogListItemComponent implements OnInit {

  @Input() date: Date;
  @Input() author: string;
  @Input() authorPhoto: string;
  @Input() content: string;
  @Input() title: string;
  @Input() uid: number;
  error;


  constructor(private blogService: BlogService) { }

  ngOnInit() {
  }

}
