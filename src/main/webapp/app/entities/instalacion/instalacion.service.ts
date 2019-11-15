import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInstalacion } from 'app/shared/model/instalacion.model';

type EntityResponseType = HttpResponse<IInstalacion>;
type EntityArrayResponseType = HttpResponse<IInstalacion[]>;

@Injectable({ providedIn: 'root' })
export class InstalacionService {
  public resourceUrl = SERVER_API_URL + 'api/instalacions';

  constructor(protected http: HttpClient) {}

  create(instalacion: IInstalacion): Observable<EntityResponseType> {
    return this.http.post<IInstalacion>(this.resourceUrl, instalacion, { observe: 'response' });
  }

  update(instalacion: IInstalacion): Observable<EntityResponseType> {
    return this.http.put<IInstalacion>(this.resourceUrl, instalacion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInstalacion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInstalacion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
