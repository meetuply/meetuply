import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatsListItemComponent } from './chats-list-item.component';

describe('ChatsListItemComponent', () => {
  let component: ChatsListItemComponent;
  let fixture: ComponentFixture<ChatsListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChatsListItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChatsListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
