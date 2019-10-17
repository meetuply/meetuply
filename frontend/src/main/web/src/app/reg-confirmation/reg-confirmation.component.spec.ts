import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegConfirmationComponent } from './reg-confirmation.component';

describe('RegConfirmationComponent', () => {
  let component: RegConfirmationComponent;
  let fixture: ComponentFixture<RegConfirmationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegConfirmationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
