import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatSubListItemComponent } from './chat-sub-list-item.component';

describe('ChatSubListItemComponent', () => {
  let component: ChatSubListItemComponent;
  let fixture: ComponentFixture<ChatSubListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChatSubListItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChatSubListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
