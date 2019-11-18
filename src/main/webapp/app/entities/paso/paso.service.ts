import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPaso } from 'app/shared/model/paso.model';

type EntityResponseType = HttpResponse<IPaso>;
type EntityArrayResponseType = HttpResponse<IPaso[]>;

@Injectable({ providedIn: 'root' })
export class PasoService {
  public resourceUrl = SERVER_API_URL + 'api/pasos';

  constructor(protected http: HttpClient) {}

  create(paso: IPaso): Observable<EntityResponseType> {
    return this.http.post<IPaso>(this.resourceUrl, paso, { observe: 'response' });
  }

  update(paso: IPaso): Observable<EntityResponseType> {
    return this.http.put<IPaso>(this.resourceUrl, paso, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaso>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaso[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
