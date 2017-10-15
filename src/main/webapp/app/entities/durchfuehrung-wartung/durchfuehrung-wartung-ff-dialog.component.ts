import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DurchfuehrungWartungFf } from './durchfuehrung-wartung-ff.model';
import { DurchfuehrungWartungFfPopupService } from './durchfuehrung-wartung-ff-popup.service';
import { DurchfuehrungWartungFfService } from './durchfuehrung-wartung-ff.service';
import { InventarFf, InventarFfService } from '../inventar';
import { SchutzausruestungFf, SchutzausruestungFfService } from '../schutzausruestung';
import { FahrzeugFf, FahrzeugFfService } from '../fahrzeug';
import { AtemschutzInventarFf, AtemschutzInventarFfService } from '../atemschutz-inventar';
import { WartungFf, WartungFfService } from '../wartung';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-durchfuehrung-wartung-ff-dialog',
    templateUrl: './durchfuehrung-wartung-ff-dialog.component.html'
})
export class DurchfuehrungWartungFfDialogComponent implements OnInit {

    durchfuehrungWartung: DurchfuehrungWartungFf;
    isSaving: boolean;

    inventars: InventarFf[];

    schutzausruestungs: SchutzausruestungFf[];

    fahrzeugs: FahrzeugFf[];

    atemschutzinventars: AtemschutzInventarFf[];

    definitions: WartungFf[];
    datumDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private durchfuehrungWartungService: DurchfuehrungWartungFfService,
        private inventarService: InventarFfService,
        private schutzausruestungService: SchutzausruestungFfService,
        private fahrzeugService: FahrzeugFfService,
        private atemschutzInventarService: AtemschutzInventarFfService,
        private wartungService: WartungFfService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.inventarService.query()
            .subscribe((res: ResponseWrapper) => { this.inventars = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.schutzausruestungService.query()
            .subscribe((res: ResponseWrapper) => { this.schutzausruestungs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.fahrzeugService.query()
            .subscribe((res: ResponseWrapper) => { this.fahrzeugs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.atemschutzInventarService.query()
            .subscribe((res: ResponseWrapper) => { this.atemschutzinventars = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.wartungService
            .query({filter: 'durchfuehrungwartung-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.durchfuehrungWartung.definition || !this.durchfuehrungWartung.definition.id) {
                    this.definitions = res.json;
                } else {
                    this.wartungService
                        .find(this.durchfuehrungWartung.definition.id)
                        .subscribe((subRes: WartungFf) => {
                            this.definitions = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.durchfuehrungWartung.id !== undefined) {
            this.subscribeToSaveResponse(
                this.durchfuehrungWartungService.update(this.durchfuehrungWartung));
        } else {
            this.subscribeToSaveResponse(
                this.durchfuehrungWartungService.create(this.durchfuehrungWartung));
        }
    }

    private subscribeToSaveResponse(result: Observable<DurchfuehrungWartungFf>) {
        result.subscribe((res: DurchfuehrungWartungFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: DurchfuehrungWartungFf) {
        this.eventManager.broadcast({ name: 'durchfuehrungWartungListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackInventarById(index: number, item: InventarFf) {
        return item.id;
    }

    trackSchutzausruestungById(index: number, item: SchutzausruestungFf) {
        return item.id;
    }

    trackFahrzeugById(index: number, item: FahrzeugFf) {
        return item.id;
    }

    trackAtemschutzInventarById(index: number, item: AtemschutzInventarFf) {
        return item.id;
    }

    trackWartungById(index: number, item: WartungFf) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-durchfuehrung-wartung-ff-popup',
    template: ''
})
export class DurchfuehrungWartungFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private durchfuehrungWartungPopupService: DurchfuehrungWartungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.durchfuehrungWartungPopupService
                    .open(DurchfuehrungWartungFfDialogComponent as Component, params['id']);
            } else {
                this.durchfuehrungWartungPopupService
                    .open(DurchfuehrungWartungFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
