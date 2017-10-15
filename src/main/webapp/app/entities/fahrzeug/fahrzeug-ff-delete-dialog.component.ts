import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FahrzeugFf } from './fahrzeug-ff.model';
import { FahrzeugFfPopupService } from './fahrzeug-ff-popup.service';
import { FahrzeugFfService } from './fahrzeug-ff.service';

@Component({
    selector: 'jhi-fahrzeug-ff-delete-dialog',
    templateUrl: './fahrzeug-ff-delete-dialog.component.html'
})
export class FahrzeugFfDeleteDialogComponent {

    fahrzeug: FahrzeugFf;

    constructor(
        private fahrzeugService: FahrzeugFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fahrzeugService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fahrzeugListModification',
                content: 'Deleted an fahrzeug'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fahrzeug-ff-delete-popup',
    template: ''
})
export class FahrzeugFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fahrzeugPopupService: FahrzeugFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.fahrzeugPopupService
                .open(FahrzeugFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
