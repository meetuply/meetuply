import { Component, OnInit } from '@angular/core';
import { Blog_list_item } from '../blog_list_item';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-blog-list-page',
  templateUrl: './blog-list-page.component.html',
  styleUrls: ['./blog-list-page.component.css', '../fonts.css']
})

export class BlogListPageComponent implements OnInit {

  posts_list: Blog_list_item[] = [
    {
      name: "Jared", surname: 'Sunn', post_title: "My first post", post_content: "Aenean vel seferrgrgrgedrhdtfhedrhdtjrtjurna odio. Suspendisse libero arcu, posuere quis tincidunt eu, tincidunt eget dui. Integer vitae ipsum eu nulla convallis sagittis"
    },
    {
      name: "Jared2", surname: 'Sunn', post_title: "Some fresh info", post_content: "Aenean vel seferrgrgrgedrhdtfhedrhdtjrtjurna odio. Suspendisse libero arcu, posuere quis tincidunt eu, tincidunt eget dui. Integer vitae ipsum eu nulla convallis sagittis"
    },
    {
      name: "Jared3", surname: 'Sunn', post_title: "AUHKjdfkhdj", post_content: "Aenean vel seferrgrgrgedrhdtfhedrhdtjrtjurna odio. Suspendisse libero arcu, posuere quis tincidunt eu, tincidunt eget dui. Integer vitae ipsum eu nulla convallis sagittis"
    },
    {
      name: "Jared4", surname: 'Sunn', post_title: "How to become barista", post_content: "Hallo Aenean vel seferrgrgrgedrhdtfhedrhdtjrtjurna odio. Suspendisse libero arcu, posuere quis tincidunt eu, tincidunt eget dui. Integer vitae ipsum eu nulla convallis sagittis"
    }
  ];

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.http.get<any>(`/api/blog/posts/`).subscribe(next => console.log());
  }

}
