import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { LehrgangFf } from './lehrgang-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LehrgangFfService {

    private resourceUrl = SERVER_API_URL + 'api/lehrgangs';

    constructor(private http: Http) { }

    create(lehrgang: LehrgangFf): Observable<LehrgangFf> {
        const copy = this.convert(lehrgang);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(lehrgang: LehrgangFf): Observable<LehrgangFf> {
        const copy = this.convert(lehrgang);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<LehrgangFf> {
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
     * Convert a returned JSON object to LehrgangFf.
     */
    private convertItemFromServer(json: any): LehrgangFf {
        const entity: LehrgangFf = Object.assign(new LehrgangFf(), json);
        return entity;
    }

    /**
     * Convert a LehrgangFf to a JSON which can be sent to the server.
     */
    private convert(lehrgang: LehrgangFf): LehrgangFf {
        const copy: LehrgangFf = Object.assign({}, lehrgang);
        return copy;
    }
}
