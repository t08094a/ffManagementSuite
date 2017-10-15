import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PruefungFf } from './pruefung-ff.model';
import { PruefungFfService } from './pruefung-ff.service';

@Component({
    selector: 'jhi-pruefung-ff-detail',
    templateUrl: './pruefung-ff-detail.component.html'
})
export class PruefungFfDetailComponent implements OnInit, OnDestroy {

    pruefung: PruefungFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pruefungService: PruefungFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPruefungs();
    }

    load(id) {
        this.pruefungService.find(id).subscribe((pruefung) => {
            this.pruefung = pruefung;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPruefungs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pruefungListModification',
            (response) => this.load(this.pruefung.id)
        );
    }
}
