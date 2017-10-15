import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { InventarKategorieFf } from './inventar-kategorie-ff.model';
import { InventarKategorieFfService } from './inventar-kategorie-ff.service';

@Component({
    selector: 'jhi-inventar-kategorie-ff-detail',
    templateUrl: './inventar-kategorie-ff-detail.component.html'
})
export class InventarKategorieFfDetailComponent implements OnInit, OnDestroy {

    inventarKategorie: InventarKategorieFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private inventarKategorieService: InventarKategorieFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInventarKategories();
    }

    load(id) {
        this.inventarKategorieService.find(id).subscribe((inventarKategorie) => {
            this.inventarKategorie = inventarKategorie;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInventarKategories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'inventarKategorieListModification',
            (response) => this.load(this.inventarKategorie.id)
        );
    }
}
