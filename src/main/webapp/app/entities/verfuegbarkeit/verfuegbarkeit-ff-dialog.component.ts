import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VerfuegbarkeitFf } from './verfuegbarkeit-ff.model';
import { VerfuegbarkeitFfPopupService } from './verfuegbarkeit-ff-popup.service';
import { VerfuegbarkeitFfService } from './verfuegbarkeit-ff.service';

@Component({
    selector: 'jhi-verfuegbarkeit-ff-dialog',
    templateUrl: './verfuegbarkeit-ff-dialog.component.html'
})
export class VerfuegbarkeitFfDialogComponent implements OnInit {

    verfuegbarkeit: VerfuegbarkeitFf;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private verfuegbarkeitService: VerfuegbarkeitFfService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.verfuegbarkeit.id !== undefined) {
            this.subscribeToSaveResponse(
                this.verfuegbarkeitService.update(this.verfuegbarkeit));
        } else {
            this.subscribeToSaveResponse(
                this.verfuegbarkeitService.create(this.verfuegbarkeit));
        }
    }

    private subscribeToSaveResponse(result: Observable<VerfuegbarkeitFf>) {
        result.subscribe((res: VerfuegbarkeitFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: VerfuegbarkeitFf) {
        this.eventManager.broadcast({ name: 'verfuegbarkeitListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-verfuegbarkeit-ff-popup',
    template: ''
})
export class VerfuegbarkeitFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private verfuegbarkeitPopupService: VerfuegbarkeitFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.verfuegbarkeitPopupService
                    .open(VerfuegbarkeitFfDialogComponent as Component, params['id']);
            } else {
                this.verfuegbarkeitPopupService
                    .open(VerfuegbarkeitFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
