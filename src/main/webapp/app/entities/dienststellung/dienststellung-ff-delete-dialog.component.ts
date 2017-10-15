import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DienststellungFf } from './dienststellung-ff.model';
import { DienststellungFfPopupService } from './dienststellung-ff-popup.service';
import { DienststellungFfService } from './dienststellung-ff.service';

@Component({
    selector: 'jhi-dienststellung-ff-delete-dialog',
    templateUrl: './dienststellung-ff-delete-dialog.component.html'
})
export class DienststellungFfDeleteDialogComponent {

    dienststellung: DienststellungFf;

    constructor(
        private dienststellungService: DienststellungFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dienststellungService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dienststellungListModification',
                content: 'Deleted an dienststellung'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dienststellung-ff-delete-popup',
    template: ''
})
export class DienststellungFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dienststellungPopupService: DienststellungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dienststellungPopupService
                .open(DienststellungFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
