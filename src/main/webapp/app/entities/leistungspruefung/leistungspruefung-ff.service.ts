import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { LeistungspruefungFf } from './leistungspruefung-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LeistungspruefungFfService {

    private resourceUrl = SERVER_API_URL + 'api/leistungspruefungs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(leistungspruefung: LeistungspruefungFf): Observable<LeistungspruefungFf> {
        const copy = this.convert(leistungspruefung);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(leistungspruefung: LeistungspruefungFf): Observable<LeistungspruefungFf> {
        const copy = this.convert(leistungspruefung);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<LeistungspruefungFf> {
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
     * Convert a returned JSON object to LeistungspruefungFf.
     */
    private convertItemFromServer(json: any): LeistungspruefungFf {
        const entity: LeistungspruefungFf = Object.assign(new LeistungspruefungFf(), json);
        entity.abgelegtAm = this.dateUtils
            .convertLocalDateFromServer(json.abgelegtAm);
        return entity;
    }

    /**
     * Convert a LeistungspruefungFf to a JSON which can be sent to the server.
     */
    private convert(leistungspruefung: LeistungspruefungFf): LeistungspruefungFf {
        const copy: LeistungspruefungFf = Object.assign({}, leistungspruefung);
        copy.abgelegtAm = this.dateUtils
            .convertLocalDateToServer(leistungspruefung.abgelegtAm);
        return copy;
    }
}
