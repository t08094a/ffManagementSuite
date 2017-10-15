import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DienstzeitFf } from './dienstzeit-ff.model';
import { DienstzeitFfPopupService } from './dienstzeit-ff-popup.service';
import { DienstzeitFfService } from './dienstzeit-ff.service';
import { PersonFf, PersonFfService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-dienstzeit-ff-dialog',
    templateUrl: './dienstzeit-ff-dialog.component.html'
})
export class DienstzeitFfDialogComponent implements OnInit {

    dienstzeit: DienstzeitFf;
    isSaving: boolean;

    people: PersonFf[];
    beginnDp: any;
    endeDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private dienstzeitService: DienstzeitFfService,
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
        if (this.dienstzeit.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dienstzeitService.update(this.dienstzeit));
        } else {
            this.subscribeToSaveResponse(
                this.dienstzeitService.create(this.dienstzeit));
        }
    }

    private subscribeToSaveResponse(result: Observable<DienstzeitFf>) {
        result.subscribe((res: DienstzeitFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: DienstzeitFf) {
        this.eventManager.broadcast({ name: 'dienstzeitListModification', content: 'OK'});
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
    selector: 'jhi-dienstzeit-ff-popup',
    template: ''
})
export class DienstzeitFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dienstzeitPopupService: DienstzeitFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dienstzeitPopupService
                    .open(DienstzeitFfDialogComponent as Component, params['id']);
            } else {
                this.dienstzeitPopupService
                    .open(DienstzeitFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
