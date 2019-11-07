import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BansPageComponent } from './bans-page.component';

describe('BansPageComponent', () => {
  let component: BansPageComponent;
  let fixture: ComponentFixture<BansPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BansPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BansPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
