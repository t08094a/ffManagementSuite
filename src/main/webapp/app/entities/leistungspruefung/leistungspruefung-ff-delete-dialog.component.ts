import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LeistungspruefungFf } from './leistungspruefung-ff.model';
import { LeistungspruefungFfPopupService } from './leistungspruefung-ff-popup.service';
import { LeistungspruefungFfService } from './leistungspruefung-ff.service';

@Component({
    selector: 'jhi-leistungspruefung-ff-delete-dialog',
    templateUrl: './leistungspruefung-ff-delete-dialog.component.html'
})
export class LeistungspruefungFfDeleteDialogComponent {

    leistungspruefung: LeistungspruefungFf;

    constructor(
        private leistungspruefungService: LeistungspruefungFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.leistungspruefungService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'leistungspruefungListModification',
                content: 'Deleted an leistungspruefung'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-leistungspruefung-ff-delete-popup',
    template: ''
})
export class LeistungspruefungFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private leistungspruefungPopupService: LeistungspruefungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.leistungspruefungPopupService
                .open(LeistungspruefungFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
