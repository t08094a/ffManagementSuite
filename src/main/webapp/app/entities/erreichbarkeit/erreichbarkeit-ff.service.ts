import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ErreichbarkeitFf } from './erreichbarkeit-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ErreichbarkeitFfService {

    private resourceUrl = SERVER_API_URL + 'api/erreichbarkeits';

    constructor(private http: Http) { }

    create(erreichbarkeit: ErreichbarkeitFf): Observable<ErreichbarkeitFf> {
        const copy = this.convert(erreichbarkeit);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(erreichbarkeit: ErreichbarkeitFf): Observable<ErreichbarkeitFf> {
        const copy = this.convert(erreichbarkeit);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ErreichbarkeitFf> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to ErreichbarkeitFf.
     */
    private convertItemFromServer(json: any): ErreichbarkeitFf {
        const entity: ErreichbarkeitFf = Object.assign(new ErreichbarkeitFf(), json);
        return entity;
    }

    /**
     * Convert a ErreichbarkeitFf to a JSON which can be sent to the server.
     */
    private convert(erreichbarkeit: ErreichbarkeitFf): ErreichbarkeitFf {
        const copy: ErreichbarkeitFf = Object.assign({}, erreichbarkeit);
        return copy;
    }
}
