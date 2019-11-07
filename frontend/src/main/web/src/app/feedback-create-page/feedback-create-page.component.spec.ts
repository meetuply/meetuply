import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedbackCreatePageComponent } from './feedback-create-page.component';

describe('FeedbackCreatePageComponent', () => {
  let component: FeedbackCreatePageComponent;
  let fixture: ComponentFixture<FeedbackCreatePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FeedbackCreatePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedbackCreatePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
