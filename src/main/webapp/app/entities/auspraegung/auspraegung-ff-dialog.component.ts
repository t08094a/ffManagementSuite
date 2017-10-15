import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuspraegungFf } from './auspraegung-ff.model';
import { AuspraegungFfPopupService } from './auspraegung-ff-popup.service';
import { AuspraegungFfService } from './auspraegung-ff.service';

@Component({
    selector: 'jhi-auspraegung-ff-dialog',
    templateUrl: './auspraegung-ff-dialog.component.html'
})
export class AuspraegungFfDialogComponent implements OnInit {

    auspraegung: AuspraegungFf;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auspraegungService: AuspraegungFfService,
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
        if (this.auspraegung.id !== undefined) {
            this.subscribeToSaveResponse(
                this.auspraegungService.update(this.auspraegung));
        } else {
            this.subscribeToSaveResponse(
                this.auspraegungService.create(this.auspraegung));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuspraegungFf>) {
        result.subscribe((res: AuspraegungFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuspraegungFf) {
        this.eventManager.broadcast({ name: 'auspraegungListModification', content: 'OK'});
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
    selector: 'jhi-auspraegung-ff-popup',
    template: ''
})
export class AuspraegungFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auspraegungPopupService: AuspraegungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.auspraegungPopupService
                    .open(AuspraegungFfDialogComponent as Component, params['id']);
            } else {
                this.auspraegungPopupService
                    .open(AuspraegungFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
