import {Topic} from "./topic";

export class Filter {
  id: number;
  name: string;
  ratingFrom: number;
  ratingTo: number;
  dateFrom: Date;
  dateTo: Date;
  userId: number;
  topics: Topic[];

}
