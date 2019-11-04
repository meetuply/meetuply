export class MeetupListItem {
  joined: boolean;
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

  speakerPhoto: string;
  speakerFirstName: string;
  speakerLastName: string;
  rating: number;
}
