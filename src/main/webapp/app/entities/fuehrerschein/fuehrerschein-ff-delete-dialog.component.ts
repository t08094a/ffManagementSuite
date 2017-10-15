import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FuehrerscheinFf } from './fuehrerschein-ff.model';
import { FuehrerscheinFfPopupService } from './fuehrerschein-ff-popup.service';
import { FuehrerscheinFfService } from './fuehrerschein-ff.service';

@Component({
    selector: 'jhi-fuehrerschein-ff-delete-dialog',
    templateUrl: './fuehrerschein-ff-delete-dialog.component.html'
})
export class FuehrerscheinFfDeleteDialogComponent {

    fuehrerschein: FuehrerscheinFf;

    constructor(
        private fuehrerscheinService: FuehrerscheinFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fuehrerscheinService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fuehrerscheinListModification',
                content: 'Deleted an fuehrerschein'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fuehrerschein-ff-delete-popup',
    template: ''
})
export class FuehrerscheinFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fuehrerscheinPopupService: FuehrerscheinFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.fuehrerscheinPopupService
                .open(FuehrerscheinFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
