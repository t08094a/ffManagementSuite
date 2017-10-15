import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { WartungFf } from './wartung-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class WartungFfService {

    private resourceUrl = SERVER_API_URL + 'api/wartungs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(wartung: WartungFf): Observable<WartungFf> {
        const copy = this.convert(wartung);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(wartung: WartungFf): Observable<WartungFf> {
        const copy = this.convert(wartung);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<WartungFf> {
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
     * Convert a returned JSON object to WartungFf.
     */
    private convertItemFromServer(json: any): WartungFf {
        const entity: WartungFf = Object.assign(new WartungFf(), json);
        entity.beginn = this.dateUtils
            .convertLocalDateFromServer(json.beginn);
        entity.letzteWartung = this.dateUtils
            .convertLocalDateFromServer(json.letzteWartung);
        return entity;
    }

    /**
     * Convert a WartungFf to a JSON which can be sent to the server.
     */
    private convert(wartung: WartungFf): WartungFf {
        const copy: WartungFf = Object.assign({}, wartung);
        copy.beginn = this.dateUtils
            .convertLocalDateToServer(wartung.beginn);
        copy.letzteWartung = this.dateUtils
            .convertLocalDateToServer(wartung.letzteWartung);
        return copy;
    }
}
