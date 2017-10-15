import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { InventarFf } from './inventar-ff.model';
import { InventarFfPopupService } from './inventar-ff-popup.service';
import { InventarFfService } from './inventar-ff.service';
import { InventarKategorieFf, InventarKategorieFfService } from '../inventar-kategorie';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-inventar-ff-dialog',
    templateUrl: './inventar-ff-dialog.component.html'
})
export class InventarFfDialogComponent implements OnInit {

    inventar: InventarFf;
    isSaving: boolean;

    kategories: InventarKategorieFf[];
    angeschafftAmDp: any;
    ausgemustertAmDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private inventarService: InventarFfService,
        private inventarKategorieService: InventarKategorieFfService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.inventarKategorieService
            .query({filter: 'inventar-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.inventar.kategorie || !this.inventar.kategorie.id) {
                    this.kategories = res.json;
                } else {
                    this.inventarKategorieService
                        .find(this.inventar.kategorie.id)
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
        if (this.inventar.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inventarService.update(this.inventar));
        } else {
            this.subscribeToSaveResponse(
                this.inventarService.create(this.inventar));
        }
    }

    private subscribeToSaveResponse(result: Observable<InventarFf>) {
        result.subscribe((res: InventarFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: InventarFf) {
        this.eventManager.broadcast({ name: 'inventarListModification', content: 'OK'});
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
    selector: 'jhi-inventar-ff-popup',
    template: ''
})
export class InventarFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inventarPopupService: InventarFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.inventarPopupService
                    .open(InventarFfDialogComponent as Component, params['id']);
            } else {
                this.inventarPopupService
                    .open(InventarFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
