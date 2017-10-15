import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { DurchfuehrungWartungFf } from './durchfuehrung-wartung-ff.model';
import { DurchfuehrungWartungFfService } from './durchfuehrung-wartung-ff.service';

@Component({
    selector: 'jhi-durchfuehrung-wartung-ff-detail',
    templateUrl: './durchfuehrung-wartung-ff-detail.component.html'
})
export class DurchfuehrungWartungFfDetailComponent implements OnInit, OnDestroy {

    durchfuehrungWartung: DurchfuehrungWartungFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private durchfuehrungWartungService: DurchfuehrungWartungFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDurchfuehrungWartungs();
    }

    load(id) {
        this.durchfuehrungWartungService.find(id).subscribe((durchfuehrungWartung) => {
            this.durchfuehrungWartung = durchfuehrungWartung;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDurchfuehrungWartungs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'durchfuehrungWartungListModification',
            (response) => this.load(this.durchfuehrungWartung.id)
        );
    }
}
