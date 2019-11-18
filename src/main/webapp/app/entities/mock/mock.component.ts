import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IMock } from 'app/shared/model/mock.model';
import { MockService } from './mock.service';

@Component({
  selector: 'jhi-mock',
  templateUrl: './mock.component.html'
})
export class MockComponent implements OnInit, OnDestroy {
  mocks: IMock[];
  eventSubscriber: Subscription;

  constructor(protected mockService: MockService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.mockService.query().subscribe((res: HttpResponse<IMock[]>) => {
      this.mocks = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInMocks();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMock) {
    return item.id;
  }

  registerChangeInMocks() {
    this.eventSubscriber = this.eventManager.subscribe('mockListModification', () => this.loadAll());
  }
}
