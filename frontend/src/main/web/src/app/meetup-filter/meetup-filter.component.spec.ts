import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeetupFilterComponent } from './meetup-filter.component';

describe('MeetupFilterComponent', () => {
  let component: MeetupFilterComponent;
  let fixture: ComponentFixture<MeetupFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeetupFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeetupFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
