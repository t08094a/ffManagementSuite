import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { AtemschutzInventarFf } from './atemschutz-inventar-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AtemschutzInventarFfService {

    private resourceUrl = SERVER_API_URL + 'api/atemschutz-inventars';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(atemschutzInventar: AtemschutzInventarFf): Observable<AtemschutzInventarFf> {
        const copy = this.convert(atemschutzInventar);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(atemschutzInventar: AtemschutzInventarFf): Observable<AtemschutzInventarFf> {
        const copy = this.convert(atemschutzInventar);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AtemschutzInventarFf> {
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
     * Convert a returned JSON object to AtemschutzInventarFf.
     */
    private convertItemFromServer(json: any): AtemschutzInventarFf {
        const entity: AtemschutzInventarFf = Object.assign(new AtemschutzInventarFf(), json);
        entity.angeschafftAm = this.dateUtils
            .convertLocalDateFromServer(json.angeschafftAm);
        entity.ausgemustertAm = this.dateUtils
            .convertLocalDateFromServer(json.ausgemustertAm);
        return entity;
    }

    /**
     * Convert a AtemschutzInventarFf to a JSON which can be sent to the server.
     */
    private convert(atemschutzInventar: AtemschutzInventarFf): AtemschutzInventarFf {
        const copy: AtemschutzInventarFf = Object.assign({}, atemschutzInventar);
        copy.angeschafftAm = this.dateUtils
            .convertLocalDateToServer(atemschutzInventar.angeschafftAm);
        copy.ausgemustertAm = this.dateUtils
            .convertLocalDateToServer(atemschutzInventar.ausgemustertAm);
        return copy;
    }
}
