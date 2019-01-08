import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RejectionsJoinComponent } from './rejections-join.component';

describe('RejectionsJoinComponent', () => {
  let component: RejectionsJoinComponent;
  let fixture: ComponentFixture<RejectionsJoinComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RejectionsJoinComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RejectionsJoinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
