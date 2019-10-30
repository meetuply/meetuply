import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AchievementCreatePageComponent } from './achievement-create-page.component';

describe('AchievementCreatePageComponent', () => {
  let component: AchievementCreatePageComponent;
  let fixture: ComponentFixture<AchievementCreatePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AchievementCreatePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AchievementCreatePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
