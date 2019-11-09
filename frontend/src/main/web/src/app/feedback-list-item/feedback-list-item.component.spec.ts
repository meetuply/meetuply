import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedbackListItemComponent } from './feedback-list-item.component';

describe('FeedbackListItemComponent', () => {
  let component: FeedbackListItemComponent;
  let fixture: ComponentFixture<FeedbackListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FeedbackListItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedbackListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
