import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BerichtselementFf } from './berichtselement-ff.model';
import { BerichtselementFfPopupService } from './berichtselement-ff-popup.service';
import { BerichtselementFfService } from './berichtselement-ff.service';

@Component({
    selector: 'jhi-berichtselement-ff-dialog',
    templateUrl: './berichtselement-ff-dialog.component.html'
})
export class BerichtselementFfDialogComponent implements OnInit {

    berichtselement: BerichtselementFf;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private berichtselementService: BerichtselementFfService,
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
        if (this.berichtselement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.berichtselementService.update(this.berichtselement));
        } else {
            this.subscribeToSaveResponse(
                this.berichtselementService.create(this.berichtselement));
        }
    }

    private subscribeToSaveResponse(result: Observable<BerichtselementFf>) {
        result.subscribe((res: BerichtselementFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: BerichtselementFf) {
        this.eventManager.broadcast({ name: 'berichtselementListModification', content: 'OK'});
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
    selector: 'jhi-berichtselement-ff-popup',
    template: ''
})
export class BerichtselementFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private berichtselementPopupService: BerichtselementFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.berichtselementPopupService
                    .open(BerichtselementFfDialogComponent as Component, params['id']);
            } else {
                this.berichtselementPopupService
                    .open(BerichtselementFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
