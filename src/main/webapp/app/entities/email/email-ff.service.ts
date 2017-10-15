import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { EmailFf } from './email-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EmailFfService {

    private resourceUrl = SERVER_API_URL + 'api/emails';

    constructor(private http: Http) { }

    create(email: EmailFf): Observable<EmailFf> {
        const copy = this.convert(email);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(email: EmailFf): Observable<EmailFf> {
        const copy = this.convert(email);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<EmailFf> {
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
     * Convert a returned JSON object to EmailFf.
     */
    private convertItemFromServer(json: any): EmailFf {
        const entity: EmailFf = Object.assign(new EmailFf(), json);
        return entity;
    }

    /**
     * Convert a EmailFf to a JSON which can be sent to the server.
     */
    private convert(email: EmailFf): EmailFf {
        const copy: EmailFf = Object.assign({}, email);
        return copy;
    }
}
