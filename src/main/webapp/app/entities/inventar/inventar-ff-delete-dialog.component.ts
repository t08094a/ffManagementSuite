import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InventarFf } from './inventar-ff.model';
import { InventarFfPopupService } from './inventar-ff-popup.service';
import { InventarFfService } from './inventar-ff.service';

@Component({
    selector: 'jhi-inventar-ff-delete-dialog',
    templateUrl: './inventar-ff-delete-dialog.component.html'
})
export class InventarFfDeleteDialogComponent {

    inventar: InventarFf;

    constructor(
        private inventarService: InventarFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inventarService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'inventarListModification',
                content: 'Deleted an inventar'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-inventar-ff-delete-popup',
    template: ''
})
export class InventarFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inventarPopupService: InventarFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.inventarPopupService
                .open(InventarFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
