import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import * as moment from 'moment';

@Component({
  selector: 'app-reschedule-dialog',
  templateUrl: './reschedule-dialog.component.html',
  styleUrls: ['./reschedule-dialog.component.less']
})
export class RescheduleDialogComponent implements OnInit {

  form: FormGroup;
  title:string
  description:string;

  meetup_start_date: string;
  meetup_start_time: string;
  meetup_end_date: string;
  meetup_end_time: string;
  error: any;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<RescheduleDialogComponent>,
    @Inject(MAT_DIALOG_DATA) data) {

    this.title = data.title;
    this.description = data.description;

    this.form = fb.group({
      startDate: [moment(), Validators.required]
    });

  }

  ngOnInit() {}

  confirm(event) {
    this.dialogRef.close({
      meetupStartDateTime: new Date(this.meetup_start_date + 'T' + this.meetup_start_time),
      meetupFinishDateTime: new Date(this.meetup_end_date + 'T' + this.meetup_end_time)
    });
  }

  close(event) {
    this.dialogRef.close(false);
  }

}
