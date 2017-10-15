import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { AusbildungFf } from './ausbildung-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AusbildungFfService {

    private resourceUrl = SERVER_API_URL + 'api/ausbildungs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(ausbildung: AusbildungFf): Observable<AusbildungFf> {
        const copy = this.convert(ausbildung);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(ausbildung: AusbildungFf): Observable<AusbildungFf> {
        const copy = this.convert(ausbildung);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AusbildungFf> {
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
     * Convert a returned JSON object to AusbildungFf.
     */
    private convertItemFromServer(json: any): AusbildungFf {
        const entity: AusbildungFf = Object.assign(new AusbildungFf(), json);
        entity.beginn = this.dateUtils
            .convertLocalDateFromServer(json.beginn);
        entity.ende = this.dateUtils
            .convertLocalDateFromServer(json.ende);
        return entity;
    }

    /**
     * Convert a AusbildungFf to a JSON which can be sent to the server.
     */
    private convert(ausbildung: AusbildungFf): AusbildungFf {
        const copy: AusbildungFf = Object.assign({}, ausbildung);
        copy.beginn = this.dateUtils
            .convertLocalDateToServer(ausbildung.beginn);
        copy.ende = this.dateUtils
            .convertLocalDateToServer(ausbildung.ende);
        return copy;
    }
}
