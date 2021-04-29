import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PeriodosAsignadosComponent } from './periodos-asignados.component';

describe('PeriodosAsignadosComponent', () => {
  let component: PeriodosAsignadosComponent;
  let fixture: ComponentFixture<PeriodosAsignadosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PeriodosAsignadosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PeriodosAsignadosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
