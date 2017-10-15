import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { InventarFf } from './inventar-ff.model';
import { InventarFfService } from './inventar-ff.service';

@Component({
    selector: 'jhi-inventar-ff-detail',
    templateUrl: './inventar-ff-detail.component.html'
})
export class InventarFfDetailComponent implements OnInit, OnDestroy {

    inventar: InventarFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private inventarService: InventarFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInventars();
    }

    load(id) {
        this.inventarService.find(id).subscribe((inventar) => {
            this.inventar = inventar;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInventars() {
        this.eventSubscriber = this.eventManager.subscribe(
            'inventarListModification',
            (response) => this.load(this.inventar.id)
        );
    }
}
