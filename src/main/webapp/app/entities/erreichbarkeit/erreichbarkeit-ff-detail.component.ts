import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ErreichbarkeitFf } from './erreichbarkeit-ff.model';
import { ErreichbarkeitFfService } from './erreichbarkeit-ff.service';

@Component({
    selector: 'jhi-erreichbarkeit-ff-detail',
    templateUrl: './erreichbarkeit-ff-detail.component.html'
})
export class ErreichbarkeitFfDetailComponent implements OnInit, OnDestroy {

    erreichbarkeit: ErreichbarkeitFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private erreichbarkeitService: ErreichbarkeitFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInErreichbarkeits();
    }

    load(id) {
        this.erreichbarkeitService.find(id).subscribe((erreichbarkeit) => {
            this.erreichbarkeit = erreichbarkeit;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInErreichbarkeits() {
        this.eventSubscriber = this.eventManager.subscribe(
            'erreichbarkeitListModification',
            (response) => this.load(this.erreichbarkeit.id)
        );
    }
}
