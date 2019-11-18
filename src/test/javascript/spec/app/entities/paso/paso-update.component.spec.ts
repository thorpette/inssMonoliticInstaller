import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { InssMonoliticInstallerTestModule } from '../../../test.module';
import { PasoUpdateComponent } from 'app/entities/paso/paso-update.component';
import { PasoService } from 'app/entities/paso/paso.service';
import { Paso } from 'app/shared/model/paso.model';

describe('Component Tests', () => {
  describe('Paso Management Update Component', () => {
    let comp: PasoUpdateComponent;
    let fixture: ComponentFixture<PasoUpdateComponent>;
    let service: PasoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InssMonoliticInstallerTestModule],
        declarations: [PasoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PasoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PasoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PasoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Paso(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Paso();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
