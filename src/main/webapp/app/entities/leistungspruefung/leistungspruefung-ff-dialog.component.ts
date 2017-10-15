import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LeistungspruefungFf } from './leistungspruefung-ff.model';
import { LeistungspruefungFfPopupService } from './leistungspruefung-ff-popup.service';
import { LeistungspruefungFfService } from './leistungspruefung-ff.service';
import { PersonFf, PersonFfService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-leistungspruefung-ff-dialog',
    templateUrl: './leistungspruefung-ff-dialog.component.html'
})
export class LeistungspruefungFfDialogComponent implements OnInit {

    leistungspruefung: LeistungspruefungFf;
    isSaving: boolean;

    people: PersonFf[];
    abgelegtAmDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private leistungspruefungService: LeistungspruefungFfService,
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
        if (this.leistungspruefung.id !== undefined) {
            this.subscribeToSaveResponse(
                this.leistungspruefungService.update(this.leistungspruefung));
        } else {
            this.subscribeToSaveResponse(
                this.leistungspruefungService.create(this.leistungspruefung));
        }
    }

    private subscribeToSaveResponse(result: Observable<LeistungspruefungFf>) {
        result.subscribe((res: LeistungspruefungFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: LeistungspruefungFf) {
        this.eventManager.broadcast({ name: 'leistungspruefungListModification', content: 'OK'});
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

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-leistungspruefung-ff-popup',
    template: ''
})
export class LeistungspruefungFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private leistungspruefungPopupService: LeistungspruefungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.leistungspruefungPopupService
                    .open(LeistungspruefungFfDialogComponent as Component, params['id']);
            } else {
                this.leistungspruefungPopupService
                    .open(LeistungspruefungFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
