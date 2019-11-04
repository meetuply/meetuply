import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BanReasonsPageComponent } from './ban-reasons-page.component';

describe('BanReasonsPageComponent', () => {
  let component: BanReasonsPageComponent;
  let fixture: ComponentFixture<BanReasonsPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BanReasonsPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BanReasonsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
