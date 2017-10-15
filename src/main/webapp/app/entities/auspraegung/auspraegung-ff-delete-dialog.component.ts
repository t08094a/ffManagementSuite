import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuspraegungFf } from './auspraegung-ff.model';
import { AuspraegungFfPopupService } from './auspraegung-ff-popup.service';
import { AuspraegungFfService } from './auspraegung-ff.service';

@Component({
    selector: 'jhi-auspraegung-ff-delete-dialog',
    templateUrl: './auspraegung-ff-delete-dialog.component.html'
})
export class AuspraegungFfDeleteDialogComponent {

    auspraegung: AuspraegungFf;

    constructor(
        private auspraegungService: AuspraegungFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auspraegungService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auspraegungListModification',
                content: 'Deleted an auspraegung'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-auspraegung-ff-delete-popup',
    template: ''
})
export class AuspraegungFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auspraegungPopupService: AuspraegungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.auspraegungPopupService
                .open(AuspraegungFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
