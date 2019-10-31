import {Injectable} from "@angular/core";
import {BlogPost} from "../_models";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {environment} from "../../environments/environment";

import {map} from 'rxjs/operators';
import {BlogComment} from "../_models/comment";

@Injectable({providedIn: 'root'})
export class BlogService {

  private blogApiUrl = `${environment.apiUrl}/api/blog/`;

  constructor(private http: HttpClient) {
  }

  getBlogPost(id: number): Observable<BlogPost> {
    return this.http.get<BlogPost>(this.blogApiUrl + `${id}`);
  }

  getAllBlogPosts(): Observable<BlogPost[]> {
    return this.http.get<BlogPost[]>(this.blogApiUrl)
  }

  getBlogPostsChunk(start: number, size: number): Observable<BlogPost[]> {
    return this.http.get<BlogPost[]>(this.blogApiUrl + start + "/" + size)
  }

  createBlogPost(blogPost: BlogPost): Observable<{}> {
    return this.http.post(this.blogApiUrl, blogPost);
  }

  getBlogComment(id: number): Observable<BlogComment> {
    return this.http.get<BlogComment>(this.blogApiUrl + "comments" + `${id}`);
  }

  getAllBlogComments(): Observable<BlogComment[]> {
    return this.http.get<BlogComment[]>(this.blogApiUrl + "comments")
  }

  getBlogPostComments(id: number): Observable<BlogComment[]> {
    return this.http.get<BlogComment[]>(this.blogApiUrl + `${id}` + "/comments")
  }

  getBlogCommentsChunk(id: number, start: number, size: number): Observable<BlogComment[]> {
    return this.http.get<BlogComment[]>(this.blogApiUrl + `${id}` + "/comments/" + start + "/" + size)
  }

  createBlogComment(blogComment: BlogComment): Observable<{}> {
    return this.http.post(this.blogApiUrl + `${blogComment.postId}` + "/comments/", blogComment);
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    return throwError(
      'Something bad happened; please try again later.');
  };

}
