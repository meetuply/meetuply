import { Component, OnInit, Input } from '@angular/core';


@Component({
  selector: 'app-blog-list-item',
  templateUrl: './blog-list-item.component.html',
  styleUrls: ['./blog-list-item.component.css']
})

export class BlogListItemComponent implements OnInit {

  @Input() name: string;
  @Input() surname: string;
  @Input() post_title: string;
  @Input() post_content: string;
  @Input() id: number;

  constructor() { }

  ngOnInit() {

  }

  shorten(text: string):string{
    var n=300;
    if (text.length>n){
      text=text.substr(0,n)+"...";
    }
    return text;
  }

}
