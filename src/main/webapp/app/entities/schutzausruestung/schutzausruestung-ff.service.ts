import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SchutzausruestungFf } from './schutzausruestung-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SchutzausruestungFfService {

    private resourceUrl = SERVER_API_URL + 'api/schutzausruestungs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(schutzausruestung: SchutzausruestungFf): Observable<SchutzausruestungFf> {
        const copy = this.convert(schutzausruestung);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(schutzausruestung: SchutzausruestungFf): Observable<SchutzausruestungFf> {
        const copy = this.convert(schutzausruestung);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<SchutzausruestungFf> {
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
     * Convert a returned JSON object to SchutzausruestungFf.
     */
    private convertItemFromServer(json: any): SchutzausruestungFf {
        const entity: SchutzausruestungFf = Object.assign(new SchutzausruestungFf(), json);
        entity.angeschafftAm = this.dateUtils
            .convertLocalDateFromServer(json.angeschafftAm);
        entity.ausgemustertAm = this.dateUtils
            .convertLocalDateFromServer(json.ausgemustertAm);
        return entity;
    }

    /**
     * Convert a SchutzausruestungFf to a JSON which can be sent to the server.
     */
    private convert(schutzausruestung: SchutzausruestungFf): SchutzausruestungFf {
        const copy: SchutzausruestungFf = Object.assign({}, schutzausruestung);
        copy.angeschafftAm = this.dateUtils
            .convertLocalDateToServer(schutzausruestung.angeschafftAm);
        copy.ausgemustertAm = this.dateUtils
            .convertLocalDateToServer(schutzausruestung.ausgemustertAm);
        return copy;
    }
}
