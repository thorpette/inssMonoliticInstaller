import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { InssMonoliticInstallerTestModule } from '../../../test.module';
import { InstalacionUpdateComponent } from 'app/entities/instalacion/instalacion-update.component';
import { InstalacionService } from 'app/entities/instalacion/instalacion.service';
import { Instalacion } from 'app/shared/model/instalacion.model';

describe('Component Tests', () => {
  describe('Instalacion Management Update Component', () => {
    let comp: InstalacionUpdateComponent;
    let fixture: ComponentFixture<InstalacionUpdateComponent>;
    let service: InstalacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InssMonoliticInstallerTestModule],
        declarations: [InstalacionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InstalacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InstalacionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InstalacionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Instalacion(123);
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
        const entity = new Instalacion();
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
