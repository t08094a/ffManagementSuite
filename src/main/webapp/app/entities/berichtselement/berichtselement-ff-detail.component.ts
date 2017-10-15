import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { BerichtselementFf } from './berichtselement-ff.model';
import { BerichtselementFfService } from './berichtselement-ff.service';

@Component({
    selector: 'jhi-berichtselement-ff-detail',
    templateUrl: './berichtselement-ff-detail.component.html'
})
export class BerichtselementFfDetailComponent implements OnInit, OnDestroy {

    berichtselement: BerichtselementFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private berichtselementService: BerichtselementFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBerichtselements();
    }

    load(id) {
        this.berichtselementService.find(id).subscribe((berichtselement) => {
            this.berichtselement = berichtselement;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBerichtselements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'berichtselementListModification',
            (response) => this.load(this.berichtselement.id)
        );
    }
}
