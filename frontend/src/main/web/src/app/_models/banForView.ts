import {BanReason} from "./banReason";
import {User} from "./user";

export class BanForView {
  banReason: BanReason;
  author: User;
  reported: User;
  description: string;
  dateTime: Date;
}
