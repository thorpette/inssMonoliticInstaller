import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IMock, Mock } from 'app/shared/model/mock.model';
import { MockService } from './mock.service';

@Component({
  selector: 'jhi-mock-update',
  templateUrl: './mock-update.component.html'
})
export class MockUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    input: [],
    output: [],
    url: []
  });

  constructor(protected mockService: MockService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ mock }) => {
      this.updateForm(mock);
    });
  }

  updateForm(mock: IMock) {
    this.editForm.patchValue({
      id: mock.id,
      name: mock.name,
      input: mock.input,
      output: mock.output,
      url: mock.url
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const mock = this.createFromForm();
    if (mock.id !== undefined) {
      this.subscribeToSaveResponse(this.mockService.update(mock));
    } else {
      this.subscribeToSaveResponse(this.mockService.create(mock));
    }
  }

  private createFromForm(): IMock {
    return {
      ...new Mock(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      input: this.editForm.get(['input']).value,
      output: this.editForm.get(['output']).value,
      url: this.editForm.get(['url']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMock>>) {
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
