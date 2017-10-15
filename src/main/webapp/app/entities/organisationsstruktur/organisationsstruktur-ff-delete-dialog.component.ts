import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OrganisationsstrukturFf } from './organisationsstruktur-ff.model';
import { OrganisationsstrukturFfPopupService } from './organisationsstruktur-ff-popup.service';
import { OrganisationsstrukturFfService } from './organisationsstruktur-ff.service';

@Component({
    selector: 'jhi-organisationsstruktur-ff-delete-dialog',
    templateUrl: './organisationsstruktur-ff-delete-dialog.component.html'
})
export class OrganisationsstrukturFfDeleteDialogComponent {

    organisationsstruktur: OrganisationsstrukturFf;

    constructor(
        private organisationsstrukturService: OrganisationsstrukturFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.organisationsstrukturService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'organisationsstrukturListModification',
                content: 'Deleted an organisationsstruktur'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-organisationsstruktur-ff-delete-popup',
    template: ''
})
export class OrganisationsstrukturFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private organisationsstrukturPopupService: OrganisationsstrukturFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.organisationsstrukturPopupService
                .open(OrganisationsstrukturFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
