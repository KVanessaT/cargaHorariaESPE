import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportByDocenteComponent } from './report-by-docente.component';

describe('ReportByDocenteComponent', () => {
  let component: ReportByDocenteComponent;
  let fixture: ComponentFixture<ReportByDocenteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportByDocenteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportByDocenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
