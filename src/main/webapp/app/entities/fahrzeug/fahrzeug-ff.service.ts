import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { FahrzeugFf } from './fahrzeug-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FahrzeugFfService {

    private resourceUrl = SERVER_API_URL + 'api/fahrzeugs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(fahrzeug: FahrzeugFf): Observable<FahrzeugFf> {
        const copy = this.convert(fahrzeug);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(fahrzeug: FahrzeugFf): Observable<FahrzeugFf> {
        const copy = this.convert(fahrzeug);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<FahrzeugFf> {
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
     * Convert a returned JSON object to FahrzeugFf.
     */
    private convertItemFromServer(json: any): FahrzeugFf {
        const entity: FahrzeugFf = Object.assign(new FahrzeugFf(), json);
        entity.angeschafftAm = this.dateUtils
            .convertLocalDateFromServer(json.angeschafftAm);
        entity.ausgemustertAm = this.dateUtils
            .convertLocalDateFromServer(json.ausgemustertAm);
        return entity;
    }

    /**
     * Convert a FahrzeugFf to a JSON which can be sent to the server.
     */
    private convert(fahrzeug: FahrzeugFf): FahrzeugFf {
        const copy: FahrzeugFf = Object.assign({}, fahrzeug);
        copy.angeschafftAm = this.dateUtils
            .convertLocalDateToServer(fahrzeug.angeschafftAm);
        copy.ausgemustertAm = this.dateUtils
            .convertLocalDateToServer(fahrzeug.ausgemustertAm);
        return copy;
    }
}
