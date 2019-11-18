import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInstalacion } from 'app/shared/model/instalacion.model';

@Component({
  selector: 'jhi-instalacion-detail',
  templateUrl: './instalacion-detail.component.html'
})
export class InstalacionDetailComponent implements OnInit {
  instalacion: IInstalacion;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ instalacion }) => {
      this.instalacion = instalacion;
    });
  }

  previousState() {
    window.history.back();
  }
}
