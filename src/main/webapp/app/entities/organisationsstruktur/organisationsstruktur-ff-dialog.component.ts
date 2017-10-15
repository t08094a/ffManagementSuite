import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OrganisationsstrukturFf } from './organisationsstruktur-ff.model';
import { OrganisationsstrukturFfPopupService } from './organisationsstruktur-ff-popup.service';
import { OrganisationsstrukturFfService } from './organisationsstruktur-ff.service';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-organisationsstruktur-ff-dialog',
    templateUrl: './organisationsstruktur-ff-dialog.component.html'
})
export class OrganisationsstrukturFfDialogComponent implements OnInit {

    organisationsstruktur: OrganisationsstrukturFf;
    isSaving: boolean;

    organisationsstrukturs: OrganisationsstrukturFf[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private organisationsstrukturService: OrganisationsstrukturFfService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.organisationsstrukturService.query()
            .subscribe((res: ResponseWrapper) => { this.organisationsstrukturs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.organisationsstruktur.id !== undefined) {
            this.subscribeToSaveResponse(
                this.organisationsstrukturService.update(this.organisationsstruktur));
        } else {
            this.subscribeToSaveResponse(
                this.organisationsstrukturService.create(this.organisationsstruktur));
        }
    }

    private subscribeToSaveResponse(result: Observable<OrganisationsstrukturFf>) {
        result.subscribe((res: OrganisationsstrukturFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OrganisationsstrukturFf) {
        this.eventManager.broadcast({ name: 'organisationsstrukturListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOrganisationsstrukturById(index: number, item: OrganisationsstrukturFf) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-organisationsstruktur-ff-popup',
    template: ''
})
export class OrganisationsstrukturFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private organisationsstrukturPopupService: OrganisationsstrukturFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.organisationsstrukturPopupService
                    .open(OrganisationsstrukturFfDialogComponent as Component, params['id']);
            } else {
                this.organisationsstrukturPopupService
                    .open(OrganisationsstrukturFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
