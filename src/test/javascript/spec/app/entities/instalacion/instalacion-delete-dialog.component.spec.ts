import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { InssMonoliticInstallerTestModule } from '../../../test.module';
import { InstalacionDeleteDialogComponent } from 'app/entities/instalacion/instalacion-delete-dialog.component';
import { InstalacionService } from 'app/entities/instalacion/instalacion.service';

describe('Component Tests', () => {
  describe('Instalacion Management Delete Component', () => {
    let comp: InstalacionDeleteDialogComponent;
    let fixture: ComponentFixture<InstalacionDeleteDialogComponent>;
    let service: InstalacionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InssMonoliticInstallerTestModule],
        declarations: [InstalacionDeleteDialogComponent]
      })
        .overrideTemplate(InstalacionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InstalacionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InstalacionService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
