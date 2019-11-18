import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMock } from 'app/shared/model/mock.model';

@Component({
  selector: 'jhi-mock-detail',
  templateUrl: './mock-detail.component.html'
})
export class MockDetailComponent implements OnInit {
  mock: IMock;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ mock }) => {
      this.mock = mock;
    });
  }

  previousState() {
    window.history.back();
  }
}
