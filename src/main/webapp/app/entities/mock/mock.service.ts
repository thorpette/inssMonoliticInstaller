import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMock } from 'app/shared/model/mock.model';

type EntityResponseType = HttpResponse<IMock>;
type EntityArrayResponseType = HttpResponse<IMock[]>;

@Injectable({ providedIn: 'root' })
export class MockService {
  public resourceUrl = SERVER_API_URL + 'api/mocks';

  constructor(protected http: HttpClient) {}

  create(mock: IMock): Observable<EntityResponseType> {
    return this.http.post<IMock>(this.resourceUrl, mock, { observe: 'response' });
  }

  update(mock: IMock): Observable<EntityResponseType> {
    return this.http.put<IMock>(this.resourceUrl, mock, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMock>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMock[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
