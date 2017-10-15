import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ReinigungFf } from './reinigung-ff.model';
import { ReinigungFfService } from './reinigung-ff.service';

@Component({
    selector: 'jhi-reinigung-ff-detail',
    templateUrl: './reinigung-ff-detail.component.html'
})
export class ReinigungFfDetailComponent implements OnInit, OnDestroy {

    reinigung: ReinigungFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private reinigungService: ReinigungFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReinigungs();
    }

    load(id) {
        this.reinigungService.find(id).subscribe((reinigung) => {
            this.reinigung = reinigung;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInReinigungs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'reinigungListModification',
            (response) => this.load(this.reinigung.id)
        );
    }
}
