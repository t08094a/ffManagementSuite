import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DurchfuehrungWartungFf } from './durchfuehrung-wartung-ff.model';
import { DurchfuehrungWartungFfPopupService } from './durchfuehrung-wartung-ff-popup.service';
import { DurchfuehrungWartungFfService } from './durchfuehrung-wartung-ff.service';

@Component({
    selector: 'jhi-durchfuehrung-wartung-ff-delete-dialog',
    templateUrl: './durchfuehrung-wartung-ff-delete-dialog.component.html'
})
export class DurchfuehrungWartungFfDeleteDialogComponent {

    durchfuehrungWartung: DurchfuehrungWartungFf;

    constructor(
        private durchfuehrungWartungService: DurchfuehrungWartungFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.durchfuehrungWartungService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'durchfuehrungWartungListModification',
                content: 'Deleted an durchfuehrungWartung'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-durchfuehrung-wartung-ff-delete-popup',
    template: ''
})
export class DurchfuehrungWartungFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private durchfuehrungWartungPopupService: DurchfuehrungWartungFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.durchfuehrungWartungPopupService
                .open(DurchfuehrungWartungFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
