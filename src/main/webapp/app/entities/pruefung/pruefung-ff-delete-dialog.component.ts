import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PruefungFf } from './pruefung-ff.model';
import { PruefungFfPopupService } from './pruefung-ff-popup.service';
import { PruefungFfService } from './pruefung-ff.service';

@Component({
    selector: 'jhi-pruefung-ff-delete-dialog',
    templateUrl: './pruefung-ff-delete-dialog.component.html'
})
export class PruefungFfDeleteDialogComponent {

    pruefung: PruefungFf;

    constructor(
        private pruefungService: PruefungFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pruefungService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pruefungListModification',
                content: 'Deleted an pruefung'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pruefung-ff-delete-popup',
    template: ''
})
export class PruefungFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pruefungPopupService: PruefungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pruefungPopupService
                .open(PruefungFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
