import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserDeactivationComponent } from './user-deactivation.component';

describe('UserDeactivationComponent', () => {
  let component: UserDeactivationComponent;
  let fixture: ComponentFixture<UserDeactivationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserDeactivationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserDeactivationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
