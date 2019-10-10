import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpeakerPageComponent } from './speaker-page.component';

describe('SpeakerPageComponent', () => {
  let component: SpeakerPageComponent;
  let fixture: ComponentFixture<SpeakerPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpeakerPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpeakerPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
