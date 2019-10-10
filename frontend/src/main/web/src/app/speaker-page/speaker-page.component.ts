import { Component, OnInit } from '@angular/core';
import { History } from '../history'
import { Feedback } from "../feedback"

@Component({
  selector: 'app-speaker-page',
  templateUrl: './speaker-page.component.html',
  styleUrls: ['./speaker-page.component.css']
})
export class SpeakerPageComponent implements OnInit {


  name = "John"
  surname = "Dee"

  location = "Barcelona, Spain"

  rate = 4.5;

  histories: History[] = [
    {
      title: "History 1",
      contents: "Contetns of history 1 sdiuheuifsheusemperfaucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suscsemper faucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suschduisheduh"
    },
    {
      title: "History 2",
      contents: "Contetns of history 1 sdiuheuifsheuhdrrrrrrrrrrrrrrsemper faucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suscrrrrrrrrrrrrrrrrrruisheduh"
    },
    {
      title: "History 3",
      contents: "Contetns of history 1 sdiuheuifsheuhdrrrrrrrrrrrrrrsemper faucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suscrrrrrrrrrrrrrrrrrruisheduh"
    },
    {
      title: "History 4",
      contents: "Contetns of history 1 sdiuheuifsheuhdrrrrrrrrrrrrrrsemper faucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suscrrrrrrrrrrrrrrrrrruisheduh"
    },
    {
      title: "History 5",
      contents: "Contetns of history 1 sdiuheuifsheuhdrrrrrrrrrrrrrrsemper faucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suscrrrrrrrrrrrrrrrrrruisheduh"
    }
  ];


  feedbacks: Feedback[] = [
    {
      name: "john",
      surname: "dee",
      contents: "ontetns of history 1 sdiuheuifsheusemperfaucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suscse",
      rating: 4.5
    },
    {
      name: "joker",
      surname: "ll",
      contents: "ontetns of history 1 sdiuheuifsheusemperfaucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suscse",
      rating: 3
    }
  ];


  description = "Aenean rhoncus semper faucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suscipit elit ut, aliquam turpis. Aenean ornare varius augue nec scelerisque. Phasellus turpis leo, venenatis sit amet imperdiet sit amet, aliquam sit amet est. Ut finibus elit a libero semper, ac faucibus lectus luctus. Cras vestibulum nulla quis arcu faucibus, sed molestie ex iaculis. Phasellus facilisis ipsum magna, quis pellentesque erat auctor sit amet. Aenean blandit magna quis est elementum elementum. Aenean tincidunt justo eu erat maximus aliquet. Etiam laoreet velit nec turpis vulputate elementum. Nunc convallis, tortor quis ultricies fringilla, magna tellus fringilla enim, ut gravida justo purus et lacus."

  constructor() { }

  ngOnInit() {
  }

}
