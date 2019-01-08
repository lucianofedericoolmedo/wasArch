import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApuProcessesComponent } from './apu-processes.component';

describe('ApuProcessesComponent', () => {
  let component: ApuProcessesComponent;
  let fixture: ComponentFixture<ApuProcessesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApuProcessesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApuProcessesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
