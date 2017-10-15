import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WartungFf } from './wartung-ff.model';
import { WartungFfPopupService } from './wartung-ff-popup.service';
import { WartungFfService } from './wartung-ff.service';

@Component({
    selector: 'jhi-wartung-ff-delete-dialog',
    templateUrl: './wartung-ff-delete-dialog.component.html'
})
export class WartungFfDeleteDialogComponent {

    wartung: WartungFf;

    constructor(
        private wartungService: WartungFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.wartungService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'wartungListModification',
                content: 'Deleted an wartung'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wartung-ff-delete-popup',
    template: ''
})
export class WartungFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private wartungPopupService: WartungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.wartungPopupService
                .open(WartungFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
