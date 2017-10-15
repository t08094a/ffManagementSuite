import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FahrzeugFf } from './fahrzeug-ff.model';
import { FahrzeugFfPopupService } from './fahrzeug-ff-popup.service';
import { FahrzeugFfService } from './fahrzeug-ff.service';
import { InventarKategorieFf, InventarKategorieFfService } from '../inventar-kategorie';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-fahrzeug-ff-dialog',
    templateUrl: './fahrzeug-ff-dialog.component.html'
})
export class FahrzeugFfDialogComponent implements OnInit {

    fahrzeug: FahrzeugFf;
    isSaving: boolean;

    kategories: InventarKategorieFf[];
    angeschafftAmDp: any;
    ausgemustertAmDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private fahrzeugService: FahrzeugFfService,
        private inventarKategorieService: InventarKategorieFfService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.inventarKategorieService
            .query({filter: 'fahrzeug-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.fahrzeug.kategorie || !this.fahrzeug.kategorie.id) {
                    this.kategories = res.json;
                } else {
                    this.inventarKategorieService
                        .find(this.fahrzeug.kategorie.id)
                        .subscribe((subRes: InventarKategorieFf) => {
                            this.kategories = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.fahrzeug.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fahrzeugService.update(this.fahrzeug));
        } else {
            this.subscribeToSaveResponse(
                this.fahrzeugService.create(this.fahrzeug));
        }
    }

    private subscribeToSaveResponse(result: Observable<FahrzeugFf>) {
        result.subscribe((res: FahrzeugFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FahrzeugFf) {
        this.eventManager.broadcast({ name: 'fahrzeugListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-fahrzeug-ff-popup',
    template: ''
})
export class FahrzeugFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fahrzeugPopupService: FahrzeugFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.fahrzeugPopupService
                    .open(FahrzeugFfDialogComponent as Component, params['id']);
            } else {
                this.fahrzeugPopupService
                    .open(FahrzeugFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
