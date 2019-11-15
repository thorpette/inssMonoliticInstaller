import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { InssMonoliticInstallerTestModule } from '../../../test.module';
import { PasoComponent } from 'app/entities/paso/paso.component';
import { PasoService } from 'app/entities/paso/paso.service';
import { Paso } from 'app/shared/model/paso.model';

describe('Component Tests', () => {
  describe('Paso Management Component', () => {
    let comp: PasoComponent;
    let fixture: ComponentFixture<PasoComponent>;
    let service: PasoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InssMonoliticInstallerTestModule],
        declarations: [PasoComponent],
        providers: []
      })
        .overrideTemplate(PasoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PasoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PasoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Paso(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pasos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
