import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DienstzeitFf } from './dienstzeit-ff.model';
import { DienstzeitFfPopupService } from './dienstzeit-ff-popup.service';
import { DienstzeitFfService } from './dienstzeit-ff.service';

@Component({
    selector: 'jhi-dienstzeit-ff-delete-dialog',
    templateUrl: './dienstzeit-ff-delete-dialog.component.html'
})
export class DienstzeitFfDeleteDialogComponent {

    dienstzeit: DienstzeitFf;

    constructor(
        private dienstzeitService: DienstzeitFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dienstzeitService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dienstzeitListModification',
                content: 'Deleted an dienstzeit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dienstzeit-ff-delete-popup',
    template: ''
})
export class DienstzeitFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dienstzeitPopupService: DienstzeitFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dienstzeitPopupService
                .open(DienstzeitFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
