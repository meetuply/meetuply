import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeetupListItemComponent } from './meetup-list-item.component';

describe('MeetupListItemComponent', () => {
  let component: MeetupListItemComponent;
  let fixture: ComponentFixture<MeetupListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeetupListItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeetupListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
