import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BerichtselementFf } from './berichtselement-ff.model';
import { BerichtselementFfPopupService } from './berichtselement-ff-popup.service';
import { BerichtselementFfService } from './berichtselement-ff.service';

@Component({
    selector: 'jhi-berichtselement-ff-delete-dialog',
    templateUrl: './berichtselement-ff-delete-dialog.component.html'
})
export class BerichtselementFfDeleteDialogComponent {

    berichtselement: BerichtselementFf;

    constructor(
        private berichtselementService: BerichtselementFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.berichtselementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'berichtselementListModification',
                content: 'Deleted an berichtselement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-berichtselement-ff-delete-popup',
    template: ''
})
export class BerichtselementFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private berichtselementPopupService: BerichtselementFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.berichtselementPopupService
                .open(BerichtselementFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
