import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PersonFf } from './person-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PersonFfService {

    private resourceUrl = SERVER_API_URL + 'api/people';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(person: PersonFf): Observable<PersonFf> {
        const copy = this.convert(person);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(person: PersonFf): Observable<PersonFf> {
        const copy = this.convert(person);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PersonFf> {
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
     * Convert a returned JSON object to PersonFf.
     */
    private convertItemFromServer(json: any): PersonFf {
        const entity: PersonFf = Object.assign(new PersonFf(), json);
        entity.geburtsdatum = this.dateUtils
            .convertLocalDateFromServer(json.geburtsdatum);
        entity.ehrung25Jahre = this.dateUtils
            .convertLocalDateFromServer(json.ehrung25Jahre);
        entity.ehrung40Jahre = this.dateUtils
            .convertLocalDateFromServer(json.ehrung40Jahre);
        return entity;
    }

    /**
     * Convert a PersonFf to a JSON which can be sent to the server.
     */
    private convert(person: PersonFf): PersonFf {
        const copy: PersonFf = Object.assign({}, person);
        copy.geburtsdatum = this.dateUtils
            .convertLocalDateToServer(person.geburtsdatum);
        copy.ehrung25Jahre = this.dateUtils
            .convertLocalDateToServer(person.ehrung25Jahre);
        copy.ehrung40Jahre = this.dateUtils
            .convertLocalDateToServer(person.ehrung40Jahre);
        return copy;
    }
}
