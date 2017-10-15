import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ReinigungFf } from './reinigung-ff.model';
import { ReinigungFfPopupService } from './reinigung-ff-popup.service';
import { ReinigungFfService } from './reinigung-ff.service';

@Component({
    selector: 'jhi-reinigung-ff-dialog',
    templateUrl: './reinigung-ff-dialog.component.html'
})
export class ReinigungFfDialogComponent implements OnInit {

    reinigung: ReinigungFf;
    isSaving: boolean;
    durchfuehrungDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private reinigungService: ReinigungFfService,
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
        if (this.reinigung.id !== undefined) {
            this.subscribeToSaveResponse(
                this.reinigungService.update(this.reinigung));
        } else {
            this.subscribeToSaveResponse(
                this.reinigungService.create(this.reinigung));
        }
    }

    private subscribeToSaveResponse(result: Observable<ReinigungFf>) {
        result.subscribe((res: ReinigungFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ReinigungFf) {
        this.eventManager.broadcast({ name: 'reinigungListModification', content: 'OK'});
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
    selector: 'jhi-reinigung-ff-popup',
    template: ''
})
export class ReinigungFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reinigungPopupService: ReinigungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.reinigungPopupService
                    .open(ReinigungFfDialogComponent as Component, params['id']);
            } else {
                this.reinigungPopupService
                    .open(ReinigungFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
