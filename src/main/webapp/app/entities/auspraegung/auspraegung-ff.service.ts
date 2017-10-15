import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AuspraegungFf } from './auspraegung-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuspraegungFfService {

    private resourceUrl = SERVER_API_URL + 'api/auspraegungs';

    constructor(private http: Http) { }

    create(auspraegung: AuspraegungFf): Observable<AuspraegungFf> {
        const copy = this.convert(auspraegung);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(auspraegung: AuspraegungFf): Observable<AuspraegungFf> {
        const copy = this.convert(auspraegung);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AuspraegungFf> {
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
     * Convert a returned JSON object to AuspraegungFf.
     */
    private convertItemFromServer(json: any): AuspraegungFf {
        const entity: AuspraegungFf = Object.assign(new AuspraegungFf(), json);
        return entity;
    }

    /**
     * Convert a AuspraegungFf to a JSON which can be sent to the server.
     */
    private convert(auspraegung: AuspraegungFf): AuspraegungFf {
        const copy: AuspraegungFf = Object.assign({}, auspraegung);
        return copy;
    }
}
