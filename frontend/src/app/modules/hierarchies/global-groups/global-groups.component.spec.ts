import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GlobalGroupsComponent } from './global-groups.component';

describe('GlobalGroupsComponent', () => {
  let component: GlobalGroupsComponent;
  let fixture: ComponentFixture<GlobalGroupsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GlobalGroupsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GlobalGroupsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
