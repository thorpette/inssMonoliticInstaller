import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { InssMonoliticInstallerTestModule } from '../../../test.module';
import { MockUpdateComponent } from 'app/entities/mock/mock-update.component';
import { MockService } from 'app/entities/mock/mock.service';
import { Mock } from 'app/shared/model/mock.model';

describe('Component Tests', () => {
  describe('Mock Management Update Component', () => {
    let comp: MockUpdateComponent;
    let fixture: ComponentFixture<MockUpdateComponent>;
    let service: MockService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InssMonoliticInstallerTestModule],
        declarations: [MockUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MockUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MockUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MockService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Mock(123);
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
        const entity = new Mock();
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
