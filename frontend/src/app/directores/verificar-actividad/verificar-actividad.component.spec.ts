import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VerificarActividadComponent } from './verificar-actividad.component';

describe('VerificarActividadComponent', () => {
  let component: VerificarActividadComponent;
  let fixture: ComponentFixture<VerificarActividadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VerificarActividadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VerificarActividadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
