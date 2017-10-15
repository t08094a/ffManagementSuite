import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AusbildungFf } from './ausbildung-ff.model';
import { AusbildungFfPopupService } from './ausbildung-ff-popup.service';
import { AusbildungFfService } from './ausbildung-ff.service';

@Component({
    selector: 'jhi-ausbildung-ff-delete-dialog',
    templateUrl: './ausbildung-ff-delete-dialog.component.html'
})
export class AusbildungFfDeleteDialogComponent {

    ausbildung: AusbildungFf;

    constructor(
        private ausbildungService: AusbildungFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ausbildungService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ausbildungListModification',
                content: 'Deleted an ausbildung'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ausbildung-ff-delete-popup',
    template: ''
})
export class AusbildungFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ausbildungPopupService: AusbildungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ausbildungPopupService
                .open(AusbildungFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
