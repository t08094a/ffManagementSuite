import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { InventarKategorieFf } from './inventar-kategorie-ff.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class InventarKategorieFfService {

    private resourceUrl = SERVER_API_URL + 'api/inventar-kategories';

    constructor(private http: Http) { }

    create(inventarKategorie: InventarKategorieFf): Observable<InventarKategorieFf> {
        const copy = this.convert(inventarKategorie);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(inventarKategorie: InventarKategorieFf): Observable<InventarKategorieFf> {
        const copy = this.convert(inventarKategorie);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<InventarKategorieFf> {
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
     * Convert a returned JSON object to InventarKategorieFf.
     */
    private convertItemFromServer(json: any): InventarKategorieFf {
        const entity: InventarKategorieFf = Object.assign(new InventarKategorieFf(), json);
        return entity;
    }

    /**
     * Convert a InventarKategorieFf to a JSON which can be sent to the server.
     */
    private convert(inventarKategorie: InventarKategorieFf): InventarKategorieFf {
        const copy: InventarKategorieFf = Object.assign({}, inventarKategorie);
        return copy;
    }
}
