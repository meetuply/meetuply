import {Feedback} from "./feedback";

export class FeedbackListItem {
  feedback: Feedback;
  author: string;
  authorPhoto: string;
  authorId: number;
  rating: number;
  get date() {return this.feedback.date}
  get content(){ return this.feedback.feedbackContent}
  get id() {return this.feedback.feedbackId}

  constructor(feedback: Feedback, rating:number, author: string, authorPhoto: string, authorId:number){
    this.feedback = feedback;
    this.rating=rating;
    this.author = author;
    this.authorPhoto = authorPhoto;
    this.authorId=authorId;
  }
}
