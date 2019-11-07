import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BanPageComponent } from './ban-page.component';

describe('BanPageComponent', () => {
  let component: BanPageComponent;
  let fixture: ComponentFixture<BanPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BanPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BanPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
