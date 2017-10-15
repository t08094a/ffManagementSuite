import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SchutzausruestungFf } from './schutzausruestung-ff.model';
import { SchutzausruestungFfPopupService } from './schutzausruestung-ff-popup.service';
import { SchutzausruestungFfService } from './schutzausruestung-ff.service';

@Component({
    selector: 'jhi-schutzausruestung-ff-delete-dialog',
    templateUrl: './schutzausruestung-ff-delete-dialog.component.html'
})
export class SchutzausruestungFfDeleteDialogComponent {

    schutzausruestung: SchutzausruestungFf;

    constructor(
        private schutzausruestungService: SchutzausruestungFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.schutzausruestungService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'schutzausruestungListModification',
                content: 'Deleted an schutzausruestung'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-schutzausruestung-ff-delete-popup',
    template: ''
})
export class SchutzausruestungFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private schutzausruestungPopupService: SchutzausruestungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.schutzausruestungPopupService
                .open(SchutzausruestungFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
