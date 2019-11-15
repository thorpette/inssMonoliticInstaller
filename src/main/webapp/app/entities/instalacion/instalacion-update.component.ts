import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IInstalacion, Instalacion } from 'app/shared/model/instalacion.model';
import { InstalacionService } from './instalacion.service';

@Component({
  selector: 'jhi-instalacion-update',
  templateUrl: './instalacion-update.component.html'
})
export class InstalacionUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    descripcion: []
  });

  constructor(protected instalacionService: InstalacionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ instalacion }) => {
      this.updateForm(instalacion);
    });
  }

  updateForm(instalacion: IInstalacion) {
    this.editForm.patchValue({
      id: instalacion.id,
      name: instalacion.name,
      descripcion: instalacion.descripcion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const instalacion = this.createFromForm();
    if (instalacion.id !== undefined) {
      this.subscribeToSaveResponse(this.instalacionService.update(instalacion));
    } else {
      this.subscribeToSaveResponse(this.instalacionService.create(instalacion));
    }
  }

  private createFromForm(): IInstalacion {
    return {
      ...new Instalacion(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      descripcion: this.editForm.get(['descripcion']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInstalacion>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
