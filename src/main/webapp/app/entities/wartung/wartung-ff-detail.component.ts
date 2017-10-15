import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { WartungFf } from './wartung-ff.model';
import { WartungFfService } from './wartung-ff.service';

@Component({
    selector: 'jhi-wartung-ff-detail',
    templateUrl: './wartung-ff-detail.component.html'
})
export class WartungFfDetailComponent implements OnInit, OnDestroy {

    wartung: WartungFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private wartungService: WartungFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWartungs();
    }

    load(id) {
        this.wartungService.find(id).subscribe((wartung) => {
            this.wartung = wartung;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWartungs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'wartungListModification',
            (response) => this.load(this.wartung.id)
        );
    }
}
