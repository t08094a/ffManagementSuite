import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DurchfuehrungPruefungFf } from './durchfuehrung-pruefung-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DurchfuehrungPruefungFfService {

    private resourceUrl = SERVER_API_URL + 'api/durchfuehrung-pruefungs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(durchfuehrungPruefung: DurchfuehrungPruefungFf): Observable<DurchfuehrungPruefungFf> {
        const copy = this.convert(durchfuehrungPruefung);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(durchfuehrungPruefung: DurchfuehrungPruefungFf): Observable<DurchfuehrungPruefungFf> {
        const copy = this.convert(durchfuehrungPruefung);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<DurchfuehrungPruefungFf> {
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
     * Convert a returned JSON object to DurchfuehrungPruefungFf.
     */
    private convertItemFromServer(json: any): DurchfuehrungPruefungFf {
        const entity: DurchfuehrungPruefungFf = Object.assign(new DurchfuehrungPruefungFf(), json);
        entity.datum = this.dateUtils
            .convertLocalDateFromServer(json.datum);
        return entity;
    }

    /**
     * Convert a DurchfuehrungPruefungFf to a JSON which can be sent to the server.
     */
    private convert(durchfuehrungPruefung: DurchfuehrungPruefungFf): DurchfuehrungPruefungFf {
        const copy: DurchfuehrungPruefungFf = Object.assign({}, durchfuehrungPruefung);
        copy.datum = this.dateUtils
            .convertLocalDateToServer(durchfuehrungPruefung.datum);
        return copy;
    }
}
