import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMock } from 'app/shared/model/mock.model';
import { MockService } from './mock.service';

@Component({
  selector: 'jhi-mock-delete-dialog',
  templateUrl: './mock-delete-dialog.component.html'
})
export class MockDeleteDialogComponent {
  mock: IMock;

  constructor(protected mockService: MockService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.mockService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'mockListModification',
        content: 'Deleted an mock'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-mock-delete-popup',
  template: ''
})
export class MockDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ mock }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MockDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.mock = mock;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/mock', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/mock', { outlets: { popup: null } }]);
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
