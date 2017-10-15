import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DurchfuehrungPruefungFf } from './durchfuehrung-pruefung-ff.model';
import { DurchfuehrungPruefungFfPopupService } from './durchfuehrung-pruefung-ff-popup.service';
import { DurchfuehrungPruefungFfService } from './durchfuehrung-pruefung-ff.service';
import { InventarFf, InventarFfService } from '../inventar';
import { SchutzausruestungFf, SchutzausruestungFfService } from '../schutzausruestung';
import { FahrzeugFf, FahrzeugFfService } from '../fahrzeug';
import { AtemschutzInventarFf, AtemschutzInventarFfService } from '../atemschutz-inventar';
import { PruefungFf, PruefungFfService } from '../pruefung';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-durchfuehrung-pruefung-ff-dialog',
    templateUrl: './durchfuehrung-pruefung-ff-dialog.component.html'
})
export class DurchfuehrungPruefungFfDialogComponent implements OnInit {

    durchfuehrungPruefung: DurchfuehrungPruefungFf;
    isSaving: boolean;

    inventars: InventarFf[];

    schutzausruestungs: SchutzausruestungFf[];

    fahrzeugs: FahrzeugFf[];

    atemschutzinventars: AtemschutzInventarFf[];

    definitions: PruefungFf[];
    datumDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private durchfuehrungPruefungService: DurchfuehrungPruefungFfService,
        private inventarService: InventarFfService,
        private schutzausruestungService: SchutzausruestungFfService,
        private fahrzeugService: FahrzeugFfService,
        private atemschutzInventarService: AtemschutzInventarFfService,
        private pruefungService: PruefungFfService,
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
        this.pruefungService
            .query({filter: 'durchfuehrungpruefung-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.durchfuehrungPruefung.definition || !this.durchfuehrungPruefung.definition.id) {
                    this.definitions = res.json;
                } else {
                    this.pruefungService
                        .find(this.durchfuehrungPruefung.definition.id)
                        .subscribe((subRes: PruefungFf) => {
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
        if (this.durchfuehrungPruefung.id !== undefined) {
            this.subscribeToSaveResponse(
                this.durchfuehrungPruefungService.update(this.durchfuehrungPruefung));
        } else {
            this.subscribeToSaveResponse(
                this.durchfuehrungPruefungService.create(this.durchfuehrungPruefung));
        }
    }

    private subscribeToSaveResponse(result: Observable<DurchfuehrungPruefungFf>) {
        result.subscribe((res: DurchfuehrungPruefungFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: DurchfuehrungPruefungFf) {
        this.eventManager.broadcast({ name: 'durchfuehrungPruefungListModification', content: 'OK'});
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

    trackPruefungById(index: number, item: PruefungFf) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-durchfuehrung-pruefung-ff-popup',
    template: ''
})
export class DurchfuehrungPruefungFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private durchfuehrungPruefungPopupService: DurchfuehrungPruefungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.durchfuehrungPruefungPopupService
                    .open(DurchfuehrungPruefungFfDialogComponent as Component, params['id']);
            } else {
                this.durchfuehrungPruefungPopupService
                    .open(DurchfuehrungPruefungFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
