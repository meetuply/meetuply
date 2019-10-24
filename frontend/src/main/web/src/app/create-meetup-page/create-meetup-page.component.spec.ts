import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMeetupPageComponent } from './create-meetup-page.component';

describe('CreateMeetupPageComponent', () => {
  let component: CreateMeetupPageComponent;
  let fixture: ComponentFixture<CreateMeetupPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateMeetupPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateMeetupPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
