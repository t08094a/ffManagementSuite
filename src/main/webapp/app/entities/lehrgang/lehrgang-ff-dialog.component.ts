import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LehrgangFf } from './lehrgang-ff.model';
import { LehrgangFfPopupService } from './lehrgang-ff-popup.service';
import { LehrgangFfService } from './lehrgang-ff.service';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-lehrgang-ff-dialog',
    templateUrl: './lehrgang-ff-dialog.component.html'
})
export class LehrgangFfDialogComponent implements OnInit {

    lehrgang: LehrgangFf;
    isSaving: boolean;

    lehrgangs: LehrgangFf[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private lehrgangService: LehrgangFfService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.lehrgangService.query()
            .subscribe((res: ResponseWrapper) => { this.lehrgangs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.lehrgang.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lehrgangService.update(this.lehrgang));
        } else {
            this.subscribeToSaveResponse(
                this.lehrgangService.create(this.lehrgang));
        }
    }

    private subscribeToSaveResponse(result: Observable<LehrgangFf>) {
        result.subscribe((res: LehrgangFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: LehrgangFf) {
        this.eventManager.broadcast({ name: 'lehrgangListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackLehrgangById(index: number, item: LehrgangFf) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-lehrgang-ff-popup',
    template: ''
})
export class LehrgangFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lehrgangPopupService: LehrgangFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.lehrgangPopupService
                    .open(LehrgangFfDialogComponent as Component, params['id']);
            } else {
                this.lehrgangPopupService
                    .open(LehrgangFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
