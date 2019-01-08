import { Injectable } from '@angular/core';
import { GenericHttpRequestService } from 'src/app/providers/http/generic.service';
import { HttpClient } from '@angular/common/http';
import { Product } from '../../interfaces/product.interface';
import { HttpErrorHandler, HandleError } from './http-error-handler.service';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { HierarchyNode } from 'src/app/interfaces/hierarchy-node.interface';

@Injectable({
  providedIn: 'root'
})
export class HierarchyService extends GenericHttpRequestService {
  handleError: HandleError;

  constructor(public http: HttpClient, public httpErrorHandler: HttpErrorHandler) {
    super(http);
    this.handleError = httpErrorHandler.createHandleError('AccountingService');
  }

  getServiceBasePath(): string {
    return 'jerarquias';
  }

  public getCodigosDimensiones() {
    const url = `${ this.getServiceBasePath() }/codigos-dimensiones`;
    return this._http.get(url);
  }

  public getCodigosJerarquias() {
    const url = `${ this.getServiceBasePath() }/codigos-jerarquias`;
    return this._http.get(url);
  }

  public getNivelesPadres() {
    const url = `${ this.getServiceBasePath() }/niveles-padres`;
    return this._http.get(url);
  }

  public getCodigosValoresPadres() {
    const url = `${ this.getServiceBasePath() }/valores-padres`;
    return this._http.get(url);
  }

  public postNodo(nodo: HierarchyNode) {
    const url = `${ this.getServiceBasePath() }/nodo`;
    return this.http.post(url, nodo)
      .toPromise()
      .then(data => console.log('success ', data))
      .catch(data => console.log('error ', data));
  }

}
