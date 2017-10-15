import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { AusbildungFf } from './ausbildung-ff.model';
import { AusbildungFfPopupService } from './ausbildung-ff-popup.service';
import { AusbildungFfService } from './ausbildung-ff.service';
import { PersonFf, PersonFfService } from '../person';
import { LehrgangFf, LehrgangFfService } from '../lehrgang';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ausbildung-ff-dialog',
    templateUrl: './ausbildung-ff-dialog.component.html'
})
export class AusbildungFfDialogComponent implements OnInit {

    ausbildung: AusbildungFf;
    isSaving: boolean;

    people: PersonFf[];

    lehrgangs: LehrgangFf[];
    beginnDp: any;
    endeDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private ausbildungService: AusbildungFfService,
        private personService: PersonFfService,
        private lehrgangService: LehrgangFfService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.lehrgangService.query()
            .subscribe((res: ResponseWrapper) => { this.lehrgangs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.ausbildung, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ausbildung.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ausbildungService.update(this.ausbildung));
        } else {
            this.subscribeToSaveResponse(
                this.ausbildungService.create(this.ausbildung));
        }
    }

    private subscribeToSaveResponse(result: Observable<AusbildungFf>) {
        result.subscribe((res: AusbildungFf) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AusbildungFf) {
        this.eventManager.broadcast({ name: 'ausbildungListModification', content: 'OK'});
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

    trackLehrgangById(index: number, item: LehrgangFf) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ausbildung-ff-popup',
    template: ''
})
export class AusbildungFfPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ausbildungPopupService: AusbildungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ausbildungPopupService
                    .open(AusbildungFfDialogComponent as Component, params['id']);
            } else {
                this.ausbildungPopupService
                    .open(AusbildungFfDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
