import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InssMonoliticInstallerTestModule } from '../../../test.module';
import { InstalacionDetailComponent } from 'app/entities/instalacion/instalacion-detail.component';
import { Instalacion } from 'app/shared/model/instalacion.model';

describe('Component Tests', () => {
  describe('Instalacion Management Detail Component', () => {
    let comp: InstalacionDetailComponent;
    let fixture: ComponentFixture<InstalacionDetailComponent>;
    const route = ({ data: of({ instalacion: new Instalacion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InssMonoliticInstallerTestModule],
        declarations: [InstalacionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InstalacionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InstalacionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.instalacion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
