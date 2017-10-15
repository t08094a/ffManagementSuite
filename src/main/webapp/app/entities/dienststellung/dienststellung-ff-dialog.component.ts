import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DienststellungFf } from './dienststellung-ff.model';
import { DienststellungFfPopupService } from './dienststellung-ff-popup.service';
import { DienststellungFfService } from './dienststellung-ff.service';

@Component({
    selector: 'jhi-dienststellung-ff-dialog',
    templateUrl: './dienststellung-ff-dialog.component.html'
})
export class DienststellungFfDialogComponent implements OnInit {

    dienststellung: DienststellungFf;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private dienststellungService: DienststellungFfService,
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
        if (this.dienststellung.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dienststellungService.update(this.dienststellung));
        } else {
            this.subscribeToSaveResponse(
                this.dienststellungService.create(this.dienststellung));
        }
    }

    private subscribeToSaveResponse(result: Observable<DienststellungFf>) {
        result.subscribe((res: DienststellungFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: DienststellungFf) {
        this.eventManager.broadcast({ name: 'dienststellungListModification', content: 'OK'});
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
    selector: 'jhi-dienststellung-ff-popup',
    template: ''
})
export class DienststellungFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dienststellungPopupService: DienststellungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dienststellungPopupService
                    .open(DienststellungFfDialogComponent as Component, params['id']);
            } else {
                this.dienststellungPopupService
                    .open(DienststellungFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
