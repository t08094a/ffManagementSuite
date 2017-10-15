import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SchutzausruestungFf } from './schutzausruestung-ff.model';
import { SchutzausruestungFfPopupService } from './schutzausruestung-ff-popup.service';
import { SchutzausruestungFfService } from './schutzausruestung-ff.service';
import { InventarKategorieFf, InventarKategorieFfService } from '../inventar-kategorie';
import { AuspraegungFf, AuspraegungFfService } from '../auspraegung';
import { PersonFf, PersonFfService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-schutzausruestung-ff-dialog',
    templateUrl: './schutzausruestung-ff-dialog.component.html'
})
export class SchutzausruestungFfDialogComponent implements OnInit {

    schutzausruestung: SchutzausruestungFf;
    isSaving: boolean;

    kategories: InventarKategorieFf[];

    auspraegungs: AuspraegungFf[];

    people: PersonFf[];
    angeschafftAmDp: any;
    ausgemustertAmDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private schutzausruestungService: SchutzausruestungFfService,
        private inventarKategorieService: InventarKategorieFfService,
        private auspraegungService: AuspraegungFfService,
        private personService: PersonFfService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.inventarKategorieService
            .query({filter: 'schutzausruestung-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.schutzausruestung.kategorie || !this.schutzausruestung.kategorie.id) {
                    this.kategories = res.json;
                } else {
                    this.inventarKategorieService
                        .find(this.schutzausruestung.kategorie.id)
                        .subscribe((subRes: InventarKategorieFf) => {
                            this.kategories = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.auspraegungService
            .query({filter: 'schutzausruestung-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.schutzausruestung.auspraegung || !this.schutzausruestung.auspraegung.id) {
                    this.auspraegungs = res.json;
                } else {
                    this.auspraegungService
                        .find(this.schutzausruestung.auspraegung.id)
                        .subscribe((subRes: AuspraegungFf) => {
                            this.auspraegungs = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.schutzausruestung.id !== undefined) {
            this.subscribeToSaveResponse(
                this.schutzausruestungService.update(this.schutzausruestung));
        } else {
            this.subscribeToSaveResponse(
                this.schutzausruestungService.create(this.schutzausruestung));
        }
    }

    private subscribeToSaveResponse(result: Observable<SchutzausruestungFf>) {
        result.subscribe((res: SchutzausruestungFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SchutzausruestungFf) {
        this.eventManager.broadcast({ name: 'schutzausruestungListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackInventarKategorieById(index: number, item: InventarKategorieFf) {
        return item.id;
    }

    trackAuspraegungById(index: number, item: AuspraegungFf) {
        return item.id;
    }

    trackPersonById(index: number, item: PersonFf) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-schutzausruestung-ff-popup',
    template: ''
})
export class SchutzausruestungFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private schutzausruestungPopupService: SchutzausruestungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.schutzausruestungPopupService
                    .open(SchutzausruestungFfDialogComponent as Component, params['id']);
            } else {
                this.schutzausruestungPopupService
                    .open(SchutzausruestungFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
