import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PersonFf } from './person-ff.model';
import { PersonFfPopupService } from './person-ff-popup.service';
import { PersonFfService } from './person-ff.service';
import { OrganisationsstrukturFf, OrganisationsstrukturFfService } from '../organisationsstruktur';
import { DienststellungFf, DienststellungFfService } from '../dienststellung';
import { FuehrerscheinFf, FuehrerscheinFfService } from '../fuehrerschein';
import { VerfuegbarkeitFf, VerfuegbarkeitFfService } from '../verfuegbarkeit';
import { LeistungspruefungFf, LeistungspruefungFfService } from '../leistungspruefung';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-person-ff-dialog',
    templateUrl: './person-ff-dialog.component.html'
})
export class PersonFfDialogComponent implements OnInit {

    person: PersonFf;
    isSaving: boolean;

    zugehoerigkeits: OrganisationsstrukturFf[];

    dienststellungs: DienststellungFf[];

    fuehrerscheins: FuehrerscheinFf[];

    verfuegbarkeits: VerfuegbarkeitFf[];

    leistungspruefungs: LeistungspruefungFf[];
    geburtsdatumDp: any;
    ehrung25JahreDp: any;
    ehrung40JahreDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private personService: PersonFfService,
        private organisationsstrukturService: OrganisationsstrukturFfService,
        private dienststellungService: DienststellungFfService,
        private fuehrerscheinService: FuehrerscheinFfService,
        private verfuegbarkeitService: VerfuegbarkeitFfService,
        private leistungspruefungService: LeistungspruefungFfService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.organisationsstrukturService
            .query({filter: 'person-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.person.zugehoerigkeit || !this.person.zugehoerigkeit.id) {
                    this.zugehoerigkeits = res.json;
                } else {
                    this.organisationsstrukturService
                        .find(this.person.zugehoerigkeit.id)
                        .subscribe((subRes: OrganisationsstrukturFf) => {
                            this.zugehoerigkeits = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.dienststellungService.query()
            .subscribe((res: ResponseWrapper) => { this.dienststellungs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.fuehrerscheinService.query()
            .subscribe((res: ResponseWrapper) => { this.fuehrerscheins = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.verfuegbarkeitService.query()
            .subscribe((res: ResponseWrapper) => { this.verfuegbarkeits = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.leistungspruefungService.query()
            .subscribe((res: ResponseWrapper) => { this.leistungspruefungs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.person.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personService.update(this.person));
        } else {
            this.subscribeToSaveResponse(
                this.personService.create(this.person));
        }
    }

    private subscribeToSaveResponse(result: Observable<PersonFf>) {
        result.subscribe((res: PersonFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PersonFf) {
        this.eventManager.broadcast({ name: 'personListModification', content: 'OK'});
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

    trackDienststellungById(index: number, item: DienststellungFf) {
        return item.id;
    }

    trackFuehrerscheinById(index: number, item: FuehrerscheinFf) {
        return item.id;
    }

    trackVerfuegbarkeitById(index: number, item: VerfuegbarkeitFf) {
        return item.id;
    }

    trackLeistungspruefungById(index: number, item: LeistungspruefungFf) {
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
    selector: 'jhi-person-ff-popup',
    template: ''
})
export class PersonFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personPopupService: PersonFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.personPopupService
                    .open(PersonFfDialogComponent as Component, params['id']);
            } else {
                this.personPopupService
                    .open(PersonFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
