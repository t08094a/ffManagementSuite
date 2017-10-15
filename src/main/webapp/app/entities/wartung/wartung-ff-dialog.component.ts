import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WartungFf } from './wartung-ff.model';
import { WartungFfPopupService } from './wartung-ff-popup.service';
import { WartungFfService } from './wartung-ff.service';
import { InventarKategorieFf, InventarKategorieFfService } from '../inventar-kategorie';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-wartung-ff-dialog',
    templateUrl: './wartung-ff-dialog.component.html'
})
export class WartungFfDialogComponent implements OnInit {

    wartung: WartungFf;
    isSaving: boolean;

    inventarkategories: InventarKategorieFf[];
    beginnDp: any;
    letzteWartungDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private wartungService: WartungFfService,
        private inventarKategorieService: InventarKategorieFfService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.inventarKategorieService.query()
            .subscribe((res: ResponseWrapper) => { this.inventarkategories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.wartung.id !== undefined) {
            this.subscribeToSaveResponse(
                this.wartungService.update(this.wartung));
        } else {
            this.subscribeToSaveResponse(
                this.wartungService.create(this.wartung));
        }
    }

    private subscribeToSaveResponse(result: Observable<WartungFf>) {
        result.subscribe((res: WartungFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: WartungFf) {
        this.eventManager.broadcast({ name: 'wartungListModification', content: 'OK'});
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
    selector: 'jhi-wartung-ff-popup',
    template: ''
})
export class WartungFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private wartungPopupService: WartungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.wartungPopupService
                    .open(WartungFfDialogComponent as Component, params['id']);
            } else {
                this.wartungPopupService
                    .open(WartungFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
