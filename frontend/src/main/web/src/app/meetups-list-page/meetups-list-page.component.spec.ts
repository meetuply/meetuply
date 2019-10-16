import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeetupsListPageComponent } from './meetups-list-page.component';

describe('MeetupsListPageComponent', () => {
  let component: MeetupsListPageComponent;
  let fixture: ComponentFixture<MeetupsListPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeetupsListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeetupsListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
