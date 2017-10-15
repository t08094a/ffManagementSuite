import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ErreichbarkeitFf } from './erreichbarkeit-ff.model';
import { ErreichbarkeitFfPopupService } from './erreichbarkeit-ff-popup.service';
import { ErreichbarkeitFfService } from './erreichbarkeit-ff.service';

@Component({
    selector: 'jhi-erreichbarkeit-ff-delete-dialog',
    templateUrl: './erreichbarkeit-ff-delete-dialog.component.html'
})
export class ErreichbarkeitFfDeleteDialogComponent {

    erreichbarkeit: ErreichbarkeitFf;

    constructor(
        private erreichbarkeitService: ErreichbarkeitFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.erreichbarkeitService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'erreichbarkeitListModification',
                content: 'Deleted an erreichbarkeit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-erreichbarkeit-ff-delete-popup',
    template: ''
})
export class ErreichbarkeitFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private erreichbarkeitPopupService: ErreichbarkeitFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.erreichbarkeitPopupService
                .open(ErreichbarkeitFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
