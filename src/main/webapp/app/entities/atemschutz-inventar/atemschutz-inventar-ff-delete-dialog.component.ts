import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AtemschutzInventarFf } from './atemschutz-inventar-ff.model';
import { AtemschutzInventarFfPopupService } from './atemschutz-inventar-ff-popup.service';
import { AtemschutzInventarFfService } from './atemschutz-inventar-ff.service';

@Component({
    selector: 'jhi-atemschutz-inventar-ff-delete-dialog',
    templateUrl: './atemschutz-inventar-ff-delete-dialog.component.html'
})
export class AtemschutzInventarFfDeleteDialogComponent {

    atemschutzInventar: AtemschutzInventarFf;

    constructor(
        private atemschutzInventarService: AtemschutzInventarFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.atemschutzInventarService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'atemschutzInventarListModification',
                content: 'Deleted an atemschutzInventar'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-atemschutz-inventar-ff-delete-popup',
    template: ''
})
export class AtemschutzInventarFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private atemschutzInventarPopupService: AtemschutzInventarFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.atemschutzInventarPopupService
                .open(AtemschutzInventarFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
