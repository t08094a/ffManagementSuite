import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DurchfuehrungWartungFf } from './durchfuehrung-wartung-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DurchfuehrungWartungFfService {

    private resourceUrl = SERVER_API_URL + 'api/durchfuehrung-wartungs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(durchfuehrungWartung: DurchfuehrungWartungFf): Observable<DurchfuehrungWartungFf> {
        const copy = this.convert(durchfuehrungWartung);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(durchfuehrungWartung: DurchfuehrungWartungFf): Observable<DurchfuehrungWartungFf> {
        const copy = this.convert(durchfuehrungWartung);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<DurchfuehrungWartungFf> {
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
     * Convert a returned JSON object to DurchfuehrungWartungFf.
     */
    private convertItemFromServer(json: any): DurchfuehrungWartungFf {
        const entity: DurchfuehrungWartungFf = Object.assign(new DurchfuehrungWartungFf(), json);
        entity.datum = this.dateUtils
            .convertLocalDateFromServer(json.datum);
        return entity;
    }

    /**
     * Convert a DurchfuehrungWartungFf to a JSON which can be sent to the server.
     */
    private convert(durchfuehrungWartung: DurchfuehrungWartungFf): DurchfuehrungWartungFf {
        const copy: DurchfuehrungWartungFf = Object.assign({}, durchfuehrungWartung);
        copy.datum = this.dateUtils
            .convertLocalDateToServer(durchfuehrungWartung.datum);
        return copy;
    }
}
