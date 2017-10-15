import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ErreichbarkeitFf } from './erreichbarkeit-ff.model';
import { ErreichbarkeitFfPopupService } from './erreichbarkeit-ff-popup.service';
import { ErreichbarkeitFfService } from './erreichbarkeit-ff.service';
import { PersonFf, PersonFfService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-erreichbarkeit-ff-dialog',
    templateUrl: './erreichbarkeit-ff-dialog.component.html'
})
export class ErreichbarkeitFfDialogComponent implements OnInit {

    erreichbarkeit: ErreichbarkeitFf;
    isSaving: boolean;

    people: PersonFf[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private erreichbarkeitService: ErreichbarkeitFfService,
        private personService: PersonFfService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.erreichbarkeit.id !== undefined) {
            this.subscribeToSaveResponse(
                this.erreichbarkeitService.update(this.erreichbarkeit));
        } else {
            this.subscribeToSaveResponse(
                this.erreichbarkeitService.create(this.erreichbarkeit));
        }
    }

    private subscribeToSaveResponse(result: Observable<ErreichbarkeitFf>) {
        result.subscribe((res: ErreichbarkeitFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ErreichbarkeitFf) {
        this.eventManager.broadcast({ name: 'erreichbarkeitListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPersonById(index: number, item: PersonFf) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-erreichbarkeit-ff-popup',
    template: ''
})
export class ErreichbarkeitFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private erreichbarkeitPopupService: ErreichbarkeitFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.erreichbarkeitPopupService
                    .open(ErreichbarkeitFfDialogComponent as Component, params['id']);
            } else {
                this.erreichbarkeitPopupService
                    .open(ErreichbarkeitFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
