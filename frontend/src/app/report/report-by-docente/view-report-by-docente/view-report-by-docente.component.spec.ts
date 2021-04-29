import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewReportByDocenteComponent } from './view-report-by-docente.component';

describe('ViewReportByDocenteComponent', () => {
  let component: ViewReportByDocenteComponent;
  let fixture: ComponentFixture<ViewReportByDocenteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewReportByDocenteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewReportByDocenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
