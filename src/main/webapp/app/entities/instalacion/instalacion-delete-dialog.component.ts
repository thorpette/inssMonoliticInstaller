import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInstalacion } from 'app/shared/model/instalacion.model';
import { InstalacionService } from './instalacion.service';

@Component({
  selector: 'jhi-instalacion-delete-dialog',
  templateUrl: './instalacion-delete-dialog.component.html'
})
export class InstalacionDeleteDialogComponent {
  instalacion: IInstalacion;

  constructor(
    protected instalacionService: InstalacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.instalacionService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'instalacionListModification',
        content: 'Deleted an instalacion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-instalacion-delete-popup',
  template: ''
})
export class InstalacionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ instalacion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InstalacionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.instalacion = instalacion;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/instalacion', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/instalacion', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
