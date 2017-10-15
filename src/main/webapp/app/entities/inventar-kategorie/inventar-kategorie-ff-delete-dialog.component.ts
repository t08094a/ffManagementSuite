import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InventarKategorieFf } from './inventar-kategorie-ff.model';
import { InventarKategorieFfPopupService } from './inventar-kategorie-ff-popup.service';
import { InventarKategorieFfService } from './inventar-kategorie-ff.service';

@Component({
    selector: 'jhi-inventar-kategorie-ff-delete-dialog',
    templateUrl: './inventar-kategorie-ff-delete-dialog.component.html'
})
export class InventarKategorieFfDeleteDialogComponent {

    inventarKategorie: InventarKategorieFf;

    constructor(
        private inventarKategorieService: InventarKategorieFfService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inventarKategorieService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'inventarKategorieListModification',
                content: 'Deleted an inventarKategorie'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-inventar-kategorie-ff-delete-popup',
    template: ''
})
export class InventarKategorieFfDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inventarKategoriePopupService: InventarKategorieFfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.inventarKategoriePopupService
                .open(InventarKategorieFfDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
