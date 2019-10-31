import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BlogCommentItemComponent } from './blog-comment-item.component';

describe('BlogListItemComponent', () => {
  let component: BlogCommentItemComponent;
  let fixture: ComponentFixture<BlogCommentItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BlogCommentItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BlogCommentItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
