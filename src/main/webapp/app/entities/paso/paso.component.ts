import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IPaso } from 'app/shared/model/paso.model';
import { PasoService } from './paso.service';

@Component({
  selector: 'jhi-paso',
  templateUrl: './paso.component.html'
})
export class PasoComponent implements OnInit, OnDestroy {
  pasos: IPaso[];
  eventSubscriber: Subscription;

  constructor(protected pasoService: PasoService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.pasoService.query().subscribe((res: HttpResponse<IPaso[]>) => {
      this.pasos = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPasos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPaso) {
    return item.id;
  }

  registerChangeInPasos() {
    this.eventSubscriber = this.eventManager.subscribe('pasoListModification', () => this.loadAll());
  }
}
