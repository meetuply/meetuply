import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpeakerListItemComponent } from './speaker-list-item.component';

describe('SpeakerListItemComponent', () => {
  let component: SpeakerListItemComponent;
  let fixture: ComponentFixture<SpeakerListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpeakerListItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpeakerListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
