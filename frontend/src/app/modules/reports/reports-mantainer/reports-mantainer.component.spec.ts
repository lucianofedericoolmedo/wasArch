import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportsMantainerComponent } from './reports-mantainer.component';

describe('ReportsMantainerComponent', () => {
  let component: ReportsMantainerComponent;
  let fixture: ComponentFixture<ReportsMantainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportsMantainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportsMantainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
