import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LehrgangFf } from './lehrgang-ff.model';
import { LehrgangFfPopupService } from './lehrgang-ff-popup.service';
import { LehrgangFfService } from './lehrgang-ff.service';

@Component({
    selector: 'jhi-lehrgang-ff-delete-dialog',
    templateUrl: './lehrgang-ff-delete-dialog.component.html'
})
export class LehrgangFfDeleteDialogComponent {

    lehrgang: LehrgangFf;

    constructor(
        private lehrgangService: LehrgangFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lehrgangService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lehrgangListModification',
                content: 'Deleted an lehrgang'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lehrgang-ff-delete-popup',
    template: ''
})
export class LehrgangFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lehrgangPopupService: LehrgangFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.lehrgangPopupService
                .open(LehrgangFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
