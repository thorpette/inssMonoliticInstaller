import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaso } from 'app/shared/model/paso.model';

@Component({
  selector: 'jhi-paso-detail',
  templateUrl: './paso-detail.component.html'
})
export class PasoDetailComponent implements OnInit {
  paso: IPaso;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paso }) => {
      this.paso = paso;
    });
  }

  previousState() {
    window.history.back();
  }
}
