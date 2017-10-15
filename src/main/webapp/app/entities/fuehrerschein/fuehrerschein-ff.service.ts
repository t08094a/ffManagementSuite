import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { FuehrerscheinFf } from './fuehrerschein-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FuehrerscheinFfService {

    private resourceUrl = SERVER_API_URL + 'api/fuehrerscheins';

    constructor(private http: Http) { }

    create(fuehrerschein: FuehrerscheinFf): Observable<FuehrerscheinFf> {
        const copy = this.convert(fuehrerschein);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(fuehrerschein: FuehrerscheinFf): Observable<FuehrerscheinFf> {
        const copy = this.convert(fuehrerschein);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<FuehrerscheinFf> {
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
     * Convert a returned JSON object to FuehrerscheinFf.
     */
    private convertItemFromServer(json: any): FuehrerscheinFf {
        const entity: FuehrerscheinFf = Object.assign(new FuehrerscheinFf(), json);
        return entity;
    }

    /**
     * Convert a FuehrerscheinFf to a JSON which can be sent to the server.
     */
    private convert(fuehrerschein: FuehrerscheinFf): FuehrerscheinFf {
        const copy: FuehrerscheinFf = Object.assign({}, fuehrerschein);
        return copy;
    }
}
