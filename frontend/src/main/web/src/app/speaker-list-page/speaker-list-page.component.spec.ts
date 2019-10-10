import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpeakerListPageComponent } from './speaker-list-page.component';

describe('SpeakerListPageComponent', () => {
  let component: SpeakerListPageComponent;
  let fixture: ComponentFixture<SpeakerListPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpeakerListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpeakerListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
