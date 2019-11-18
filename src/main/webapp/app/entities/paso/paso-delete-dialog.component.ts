import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaso } from 'app/shared/model/paso.model';
import { PasoService } from './paso.service';

@Component({
  selector: 'jhi-paso-delete-dialog',
  templateUrl: './paso-delete-dialog.component.html'
})
export class PasoDeleteDialogComponent {
  paso: IPaso;

  constructor(protected pasoService: PasoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pasoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'pasoListModification',
        content: 'Deleted an paso'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-paso-delete-popup',
  template: ''
})
export class PasoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paso }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PasoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.paso = paso;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/paso', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/paso', { outlets: { popup: null } }]);
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
