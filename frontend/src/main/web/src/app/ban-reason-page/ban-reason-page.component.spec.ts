import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BanReasonPageComponent } from './ban-reason-page.component';

describe('BanReasonPageComponent', () => {
  let component: BanReasonPageComponent;
  let fixture: ComponentFixture<BanReasonPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BanReasonPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BanReasonPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
