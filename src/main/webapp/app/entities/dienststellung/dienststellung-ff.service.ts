import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { DienststellungFf } from './dienststellung-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DienststellungFfService {

    private resourceUrl = SERVER_API_URL + 'api/dienststellungs';

    constructor(private http: Http) { }

    create(dienststellung: DienststellungFf): Observable<DienststellungFf> {
        const copy = this.convert(dienststellung);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(dienststellung: DienststellungFf): Observable<DienststellungFf> {
        const copy = this.convert(dienststellung);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<DienststellungFf> {
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
     * Convert a returned JSON object to DienststellungFf.
     */
    private convertItemFromServer(json: any): DienststellungFf {
        const entity: DienststellungFf = Object.assign(new DienststellungFf(), json);
        return entity;
    }

    /**
     * Convert a DienststellungFf to a JSON which can be sent to the server.
     */
    private convert(dienststellung: DienststellungFf): DienststellungFf {
        const copy: DienststellungFf = Object.assign({}, dienststellung);
        return copy;
    }
}
