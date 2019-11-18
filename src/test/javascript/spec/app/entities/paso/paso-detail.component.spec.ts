import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InssMonoliticInstallerTestModule } from '../../../test.module';
import { PasoDetailComponent } from 'app/entities/paso/paso-detail.component';
import { Paso } from 'app/shared/model/paso.model';

describe('Component Tests', () => {
  describe('Paso Management Detail Component', () => {
    let comp: PasoDetailComponent;
    let fixture: ComponentFixture<PasoDetailComponent>;
    const route = ({ data: of({ paso: new Paso(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InssMonoliticInstallerTestModule],
        declarations: [PasoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PasoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PasoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paso).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
