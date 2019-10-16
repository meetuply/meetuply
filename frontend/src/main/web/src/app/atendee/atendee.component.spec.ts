import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AtendeeComponent } from './atendee.component';

describe('AtendeeComponent', () => {
  let component: AtendeeComponent;
  let fixture: ComponentFixture<AtendeeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AtendeeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AtendeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
