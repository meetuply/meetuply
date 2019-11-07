import { Language } from "./language";
import { Topic } from "./topic";

export class Meetup {
  meetupId: number;
  meetupPlace: string;
  meetupTitle: string;
  meetupDescription: string;
  meetupRegisteredAttendees: number;
  meetupMinAttendees: number;
  meetupMaxAttendees: number;
  meetupStartDateTime: Date;
  meetupFinishDateTime: Date;
  stateId: number;
  speakerId: number;

  language: string;
  topics: number[];
}
