import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PruefungFf } from './pruefung-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PruefungFfService {

    private resourceUrl = SERVER_API_URL + 'api/pruefungs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(pruefung: PruefungFf): Observable<PruefungFf> {
        const copy = this.convert(pruefung);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(pruefung: PruefungFf): Observable<PruefungFf> {
        const copy = this.convert(pruefung);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PruefungFf> {
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
     * Convert a returned JSON object to PruefungFf.
     */
    private convertItemFromServer(json: any): PruefungFf {
        const entity: PruefungFf = Object.assign(new PruefungFf(), json);
        entity.beginn = this.dateUtils
            .convertLocalDateFromServer(json.beginn);
        entity.letztePruefung = this.dateUtils
            .convertLocalDateFromServer(json.letztePruefung);
        return entity;
    }

    /**
     * Convert a PruefungFf to a JSON which can be sent to the server.
     */
    private convert(pruefung: PruefungFf): PruefungFf {
        const copy: PruefungFf = Object.assign({}, pruefung);
        copy.beginn = this.dateUtils
            .convertLocalDateToServer(pruefung.beginn);
        copy.letztePruefung = this.dateUtils
            .convertLocalDateToServer(pruefung.letztePruefung);
        return copy;
    }
}
