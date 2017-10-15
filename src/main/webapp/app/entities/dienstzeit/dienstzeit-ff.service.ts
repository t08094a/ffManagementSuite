import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DienstzeitFf } from './dienstzeit-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DienstzeitFfService {

    private resourceUrl = SERVER_API_URL + 'api/dienstzeits';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(dienstzeit: DienstzeitFf): Observable<DienstzeitFf> {
        const copy = this.convert(dienstzeit);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(dienstzeit: DienstzeitFf): Observable<DienstzeitFf> {
        const copy = this.convert(dienstzeit);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<DienstzeitFf> {
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
     * Convert a returned JSON object to DienstzeitFf.
     */
    private convertItemFromServer(json: any): DienstzeitFf {
        const entity: DienstzeitFf = Object.assign(new DienstzeitFf(), json);
        entity.beginn = this.dateUtils
            .convertLocalDateFromServer(json.beginn);
        entity.ende = this.dateUtils
            .convertLocalDateFromServer(json.ende);
        return entity;
    }

    /**
     * Convert a DienstzeitFf to a JSON which can be sent to the server.
     */
    private convert(dienstzeit: DienstzeitFf): DienstzeitFf {
        const copy: DienstzeitFf = Object.assign({}, dienstzeit);
        copy.beginn = this.dateUtils
            .convertLocalDateToServer(dienstzeit.beginn);
        copy.ende = this.dateUtils
            .convertLocalDateToServer(dienstzeit.ende);
        return copy;
    }
}
