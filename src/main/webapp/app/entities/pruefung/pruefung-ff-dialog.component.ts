import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PruefungFf } from './pruefung-ff.model';
import { PruefungFfPopupService } from './pruefung-ff-popup.service';
import { PruefungFfService } from './pruefung-ff.service';
import { InventarKategorieFf, InventarKategorieFfService } from '../inventar-kategorie';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-pruefung-ff-dialog',
    templateUrl: './pruefung-ff-dialog.component.html'
})
export class PruefungFfDialogComponent implements OnInit {

    pruefung: PruefungFf;
    isSaving: boolean;

    inventarkategories: InventarKategorieFf[];
    beginnDp: any;
    letztePruefungDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private pruefungService: PruefungFfService,
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
        if (this.pruefung.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pruefungService.update(this.pruefung));
        } else {
            this.subscribeToSaveResponse(
                this.pruefungService.create(this.pruefung));
        }
    }

    private subscribeToSaveResponse(result: Observable<PruefungFf>) {
        result.subscribe((res: PruefungFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PruefungFf) {
        this.eventManager.broadcast({ name: 'pruefungListModification', content: 'OK'});
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
    selector: 'jhi-pruefung-ff-popup',
    template: ''
})
export class PruefungFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pruefungPopupService: PruefungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pruefungPopupService
                    .open(PruefungFfDialogComponent as Component, params['id']);
            } else {
                this.pruefungPopupService
                    .open(PruefungFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
