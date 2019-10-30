import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs";
import {UserService} from "../_services";
import {BlogService} from "../_services/blog.service";
import {BlogPost} from "../_models";

@Component({
  selector: 'app-blog-page',
  templateUrl: './blog-page.component.html',
  styleUrls: ['./blog-page.component.css', '../fonts.css']
})
export class BlogPageComponent implements OnInit {


  // name = "John"
  // surname = "Dee"
  // post_title = "Blog #1: Playing baseball"
  // post_content="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Eget mi proin sed libero enim sed faucibus turpis in. Metus aliquam eleifend mi in nulla. Eu scelerisque felis imperdiet proin fermentum leo vel. Ornare aenean euismod elementum nisi quis. Congue mauris rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur vitae nunc. Malesuada proin libero nunc consequat interdum. Sit amet nulla facilisi morbi tempus iaculis. Sagittis orci a scelerisque purus semper. Arcu vitae elementum curabitur vitae nunc sed. Vulputate ut pharetra sit amet aliquam id diam maecenas. Scelerisque felis imperdiet proin fermentum leo. Eget mi proin sed libero. Orci porta non pulvinar neque laoreet suspendisse interdum consectetur libero. Vel facilisis volutpat est velit egestas dui. Ut diam quam nulla porttitor.\n" +
  //   "\n" +
  //   "Nec ullamcorper sit amet risus nullam eget felis eget. Turpis massa sed elementum tempus egestas sed. Sollicitudin aliquam ultrices sagittis orci a scelerisque purus. Euismod in pellentesque massa placerat duis ultricies lacus. Gravida in fermentum et sollicitudin ac orci. Amet risus nullam eget felis. Mattis molestie a iaculis at erat pellentesque adipiscing commodo. Quis varius quam quisque id diam. Elit duis tristique sollicitudin nibh. Auctor urna nunc id cursus metus aliquam. Ac auctor augue mauris augue neque gravida in fermentum et. Id nibh tortor id aliquet lectus proin nibh nisl. In eu mi bibendum neque egestas.\n" +
  //   "\n" +
  //   "Mattis pellentesque id nibh tortor id aliquet lectus proin. Ut tristique et egestas quis. Sed felis eget velit aliquet. Amet nulla facilisi morbi tempus iaculis urna. Ultrices gravida dictum fusce ut placerat orci. Aliquet nec ullamcorper sit amet. Eleifend donec pretium vulputate sapien nec sagittis aliquam malesuada. Donec massa sapien faucibus et molestie ac. Risus pretium quam vulputate dignissim suspendisse in est ante in. In nibh mauris cursus mattis molestie a iaculis at. Ultrices eros in cursus turpis massa tincidunt dui ut. Bibendum neque egestas congue quisque egestas diam in arcu. Odio morbi quis commodo odio aenean sed adipiscing diam. Ac turpis egestas sed tempus urna et pharetra. Senectus et netus et malesuada fames ac turpis egestas. Nisl tincidunt eget nullam non nisi est. Pharetra diam sit amet nisl suscipit. Amet justo donec enim diam vulputate ut pharetra sit amet.\n" +
  //   "\n" +
  //   "Cursus vitae congue mauris rhoncus aenean vel elit. A lacus vestibulum sed arcu non odio. Nisi scelerisque eu ultrices vitae auctor eu augue ut lectus. A cras semper auctor neque vitae tempus quam pellentesque nec. Pellentesque dignissim enim sit amet venenatis urna cursus. Amet facilisis magna etiam tempor orci eu lobortis elementum nibh. Vitae justo eget magna fermentum. Enim sed faucibus turpis in eu mi bibendum neque egestas. Elit duis tristique sollicitudin nibh. Morbi blandit cursus risus at ultrices."
  //

  blogpost: BlogPost = new BlogPost();
  loading = false;
  private sub: Subscription;
  id: number;
  author: string;
  authorPhoto: string;
  error = null;

  constructor(private userService: UserService, private blogService: BlogService,
              private _location: Location, private route: ActivatedRoute) {}

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.loadBlogPost(this.id);
  }

  loadBlogPost(id:number) {
    this.loading = true;
    this.sub = this.blogService.getBlogPost(id).subscribe(
      data => {
        this.blogpost = data;
        this.loading = false;
        console.log(this.blogpost.blogPostContent);
        this.getAuthorInfo(data['authorId']);
      },
      error => {
        // this.alertService.error(error);
        this.loading = false;
      }
    );
  }

  getAuthorInfo(id: number){
    this.loading = true;
    this.userService.get(id).subscribe(
      data => {
        this.loading = false;
        this.author = data['firstName']+ " " + data['lastName'];
        this.authorPhoto = data['photo']
      }
    );
  }

  ngOnDestroy(){
    if (this.sub) this.sub.unsubscribe;
  }

  goBack() {
    this._location.back();
  }

}
