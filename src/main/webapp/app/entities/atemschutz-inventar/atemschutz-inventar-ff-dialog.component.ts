import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AtemschutzInventarFf } from './atemschutz-inventar-ff.model';
import { AtemschutzInventarFfPopupService } from './atemschutz-inventar-ff-popup.service';
import { AtemschutzInventarFfService } from './atemschutz-inventar-ff.service';
import { InventarKategorieFf, InventarKategorieFfService } from '../inventar-kategorie';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-atemschutz-inventar-ff-dialog',
    templateUrl: './atemschutz-inventar-ff-dialog.component.html'
})
export class AtemschutzInventarFfDialogComponent implements OnInit {

    atemschutzInventar: AtemschutzInventarFf;
    isSaving: boolean;

    kategories: InventarKategorieFf[];
    angeschafftAmDp: any;
    ausgemustertAmDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private atemschutzInventarService: AtemschutzInventarFfService,
        private inventarKategorieService: InventarKategorieFfService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.inventarKategorieService
            .query({filter: 'atemschutzinventar-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.atemschutzInventar.kategorie || !this.atemschutzInventar.kategorie.id) {
                    this.kategories = res.json;
                } else {
                    this.inventarKategorieService
                        .find(this.atemschutzInventar.kategorie.id)
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
        if (this.atemschutzInventar.id !== undefined) {
            this.subscribeToSaveResponse(
                this.atemschutzInventarService.update(this.atemschutzInventar));
        } else {
            this.subscribeToSaveResponse(
                this.atemschutzInventarService.create(this.atemschutzInventar));
        }
    }

    private subscribeToSaveResponse(result: Observable<AtemschutzInventarFf>) {
        result.subscribe((res: AtemschutzInventarFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AtemschutzInventarFf) {
        this.eventManager.broadcast({ name: 'atemschutzInventarListModification', content: 'OK'});
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
    selector: 'jhi-atemschutz-inventar-ff-popup',
    template: ''
})
export class AtemschutzInventarFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private atemschutzInventarPopupService: AtemschutzInventarFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.atemschutzInventarPopupService
                    .open(AtemschutzInventarFfDialogComponent as Component, params['id']);
            } else {
                this.atemschutzInventarPopupService
                    .open(AtemschutzInventarFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
