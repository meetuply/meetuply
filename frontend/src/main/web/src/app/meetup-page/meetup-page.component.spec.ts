import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeetupPageComponent } from './meetup-page.component';

describe('MeetupPageComponent', () => {
  let component: MeetupPageComponent;
  let fixture: ComponentFixture<MeetupPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeetupPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeetupPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
