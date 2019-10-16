import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LogoutMenuItemComponent } from './logout-menu-item.component';

describe('LogoutMenuItemComponent', () => {
  let component: LogoutMenuItemComponent;
  let fixture: ComponentFixture<LogoutMenuItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LogoutMenuItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LogoutMenuItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
