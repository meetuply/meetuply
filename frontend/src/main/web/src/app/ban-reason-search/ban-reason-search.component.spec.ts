import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BanReasonSearchComponent } from './ban-reason-search.component';

describe('BanReasonSearchComponent', () => {
  let component: BanReasonSearchComponent;
  let fixture: ComponentFixture<BanReasonSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BanReasonSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BanReasonSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
