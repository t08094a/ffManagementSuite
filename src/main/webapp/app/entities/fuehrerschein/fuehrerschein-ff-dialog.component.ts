import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FuehrerscheinFf } from './fuehrerschein-ff.model';
import { FuehrerscheinFfPopupService } from './fuehrerschein-ff-popup.service';
import { FuehrerscheinFfService } from './fuehrerschein-ff.service';

@Component({
    selector: 'jhi-fuehrerschein-ff-dialog',
    templateUrl: './fuehrerschein-ff-dialog.component.html'
})
export class FuehrerscheinFfDialogComponent implements OnInit {

    fuehrerschein: FuehrerscheinFf;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private fuehrerscheinService: FuehrerscheinFfService,
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
        if (this.fuehrerschein.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fuehrerscheinService.update(this.fuehrerschein));
        } else {
            this.subscribeToSaveResponse(
                this.fuehrerscheinService.create(this.fuehrerschein));
        }
    }

    private subscribeToSaveResponse(result: Observable<FuehrerscheinFf>) {
        result.subscribe((res: FuehrerscheinFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FuehrerscheinFf) {
        this.eventManager.broadcast({ name: 'fuehrerscheinListModification', content: 'OK'});
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
    selector: 'jhi-fuehrerschein-ff-popup',
    template: ''
})
export class FuehrerscheinFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fuehrerscheinPopupService: FuehrerscheinFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.fuehrerscheinPopupService
                    .open(FuehrerscheinFfDialogComponent as Component, params['id']);
            } else {
                this.fuehrerscheinPopupService
                    .open(FuehrerscheinFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
