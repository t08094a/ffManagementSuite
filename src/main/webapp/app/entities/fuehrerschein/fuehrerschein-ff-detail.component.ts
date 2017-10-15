import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FuehrerscheinFf } from './fuehrerschein-ff.model';
import { FuehrerscheinFfService } from './fuehrerschein-ff.service';

@Component({
    selector: 'jhi-fuehrerschein-ff-detail',
    templateUrl: './fuehrerschein-ff-detail.component.html'
})
export class FuehrerscheinFfDetailComponent implements OnInit, OnDestroy {

    fuehrerschein: FuehrerscheinFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private fuehrerscheinService: FuehrerscheinFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFuehrerscheins();
    }

    load(id) {
        this.fuehrerscheinService.find(id).subscribe((fuehrerschein) => {
            this.fuehrerschein = fuehrerschein;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFuehrerscheins() {
        this.eventSubscriber = this.eventManager.subscribe(
            'fuehrerscheinListModification',
            (response) => this.load(this.fuehrerschein.id)
        );
    }
}
