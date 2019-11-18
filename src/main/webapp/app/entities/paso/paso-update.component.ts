import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IPaso, Paso } from 'app/shared/model/paso.model';
import { PasoService } from './paso.service';
import { IInstalacion } from 'app/shared/model/instalacion.model';
import { InstalacionService } from 'app/entities/instalacion/instalacion.service';

@Component({
  selector: 'jhi-paso-update',
  templateUrl: './paso-update.component.html'
})
export class PasoUpdateComponent implements OnInit {
  isSaving: boolean;

  instalacions: IInstalacion[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    command: [],
    origen: [],
    destino: [],
    parametro: [],
    instalacion: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pasoService: PasoService,
    protected instalacionService: InstalacionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ paso }) => {
      this.updateForm(paso);
    });
    this.instalacionService
      .query()
      .subscribe(
        (res: HttpResponse<IInstalacion[]>) => (this.instalacions = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(paso: IPaso) {
    this.editForm.patchValue({
      id: paso.id,
      name: paso.name,
      command: paso.command,
      origen: paso.origen,
      destino: paso.destino,
      parametro: paso.parametro,
      instalacion: paso.instalacion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const paso = this.createFromForm();
    if (paso.id !== undefined) {
      this.subscribeToSaveResponse(this.pasoService.update(paso));
    } else {
      this.subscribeToSaveResponse(this.pasoService.create(paso));
    }
  }

  private createFromForm(): IPaso {
    return {
      ...new Paso(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      command: this.editForm.get(['command']).value,
      origen: this.editForm.get(['origen']).value,
      destino: this.editForm.get(['destino']).value,
      parametro: this.editForm.get(['parametro']).value,
      instalacion: this.editForm.get(['instalacion']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaso>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackInstalacionById(index: number, item: IInstalacion) {
    return item.id;
  }
}
