import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { InventarKategorieFf } from './inventar-kategorie-ff.model';
import { InventarKategorieFfPopupService } from './inventar-kategorie-ff-popup.service';
import { InventarKategorieFfService } from './inventar-kategorie-ff.service';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-inventar-kategorie-ff-dialog',
    templateUrl: './inventar-kategorie-ff-dialog.component.html'
})
export class InventarKategorieFfDialogComponent implements OnInit {

    inventarKategorie: InventarKategorieFf;
    isSaving: boolean;

    inventarkategories: InventarKategorieFf[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
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
        if (this.inventarKategorie.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inventarKategorieService.update(this.inventarKategorie));
        } else {
            this.subscribeToSaveResponse(
                this.inventarKategorieService.create(this.inventarKategorie));
        }
    }

    private subscribeToSaveResponse(result: Observable<InventarKategorieFf>) {
        result.subscribe((res: InventarKategorieFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: InventarKategorieFf) {
        this.eventManager.broadcast({ name: 'inventarKategorieListModification', content: 'OK'});
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
    selector: 'jhi-inventar-kategorie-ff-popup',
    template: ''
})
export class InventarKategorieFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inventarKategoriePopupService: InventarKategorieFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.inventarKategoriePopupService
                    .open(InventarKategorieFfDialogComponent as Component, params['id']);
            } else {
                this.inventarKategoriePopupService
                    .open(InventarKategorieFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
