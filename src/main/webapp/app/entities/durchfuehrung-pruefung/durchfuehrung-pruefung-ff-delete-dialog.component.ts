import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DurchfuehrungPruefungFf } from './durchfuehrung-pruefung-ff.model';
import { DurchfuehrungPruefungFfPopupService } from './durchfuehrung-pruefung-ff-popup.service';
import { DurchfuehrungPruefungFfService } from './durchfuehrung-pruefung-ff.service';

@Component({
    selector: 'jhi-durchfuehrung-pruefung-ff-delete-dialog',
    templateUrl: './durchfuehrung-pruefung-ff-delete-dialog.component.html'
})
export class DurchfuehrungPruefungFfDeleteDialogComponent {

    durchfuehrungPruefung: DurchfuehrungPruefungFf;

    constructor(
        private durchfuehrungPruefungService: DurchfuehrungPruefungFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.durchfuehrungPruefungService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'durchfuehrungPruefungListModification',
                content: 'Deleted an durchfuehrungPruefung'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-durchfuehrung-pruefung-ff-delete-popup',
    template: ''
})
export class DurchfuehrungPruefungFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private durchfuehrungPruefungPopupService: DurchfuehrungPruefungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.durchfuehrungPruefungPopupService
                .open(DurchfuehrungPruefungFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
