import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VerfuegbarkeitFf } from './verfuegbarkeit-ff.model';
import { VerfuegbarkeitFfPopupService } from './verfuegbarkeit-ff-popup.service';
import { VerfuegbarkeitFfService } from './verfuegbarkeit-ff.service';

@Component({
    selector: 'jhi-verfuegbarkeit-ff-delete-dialog',
    templateUrl: './verfuegbarkeit-ff-delete-dialog.component.html'
})
export class VerfuegbarkeitFfDeleteDialogComponent {

    verfuegbarkeit: VerfuegbarkeitFf;

    constructor(
        private verfuegbarkeitService: VerfuegbarkeitFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.verfuegbarkeitService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'verfuegbarkeitListModification',
                content: 'Deleted an verfuegbarkeit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-verfuegbarkeit-ff-delete-popup',
    template: ''
})
export class VerfuegbarkeitFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private verfuegbarkeitPopupService: VerfuegbarkeitFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.verfuegbarkeitPopupService
                .open(VerfuegbarkeitFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
