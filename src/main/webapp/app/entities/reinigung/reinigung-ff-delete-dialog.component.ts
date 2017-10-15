import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ReinigungFf } from './reinigung-ff.model';
import { ReinigungFfPopupService } from './reinigung-ff-popup.service';
import { ReinigungFfService } from './reinigung-ff.service';

@Component({
    selector: 'jhi-reinigung-ff-delete-dialog',
    templateUrl: './reinigung-ff-delete-dialog.component.html'
})
export class ReinigungFfDeleteDialogComponent {

    reinigung: ReinigungFf;

    constructor(
        private reinigungService: ReinigungFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reinigungService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'reinigungListModification',
                content: 'Deleted an reinigung'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-reinigung-ff-delete-popup',
    template: ''
})
export class ReinigungFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reinigungPopupService: ReinigungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.reinigungPopupService
                .open(ReinigungFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
