import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ReinigungFf } from './reinigung-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ReinigungFfService {

    private resourceUrl = SERVER_API_URL + 'api/reinigungs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(reinigung: ReinigungFf): Observable<ReinigungFf> {
        const copy = this.convert(reinigung);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(reinigung: ReinigungFf): Observable<ReinigungFf> {
        const copy = this.convert(reinigung);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ReinigungFf> {
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
     * Convert a returned JSON object to ReinigungFf.
     */
    private convertItemFromServer(json: any): ReinigungFf {
        const entity: ReinigungFf = Object.assign(new ReinigungFf(), json);
        entity.durchfuehrung = this.dateUtils
            .convertLocalDateFromServer(json.durchfuehrung);
        return entity;
    }

    /**
     * Convert a ReinigungFf to a JSON which can be sent to the server.
     */
    private convert(reinigung: ReinigungFf): ReinigungFf {
        const copy: ReinigungFf = Object.assign({}, reinigung);
        copy.durchfuehrung = this.dateUtils
            .convertLocalDateToServer(reinigung.durchfuehrung);
        return copy;
    }
}
