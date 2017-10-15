import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SchutzausruestungFf } from './schutzausruestung-ff.model';
import { SchutzausruestungFfService } from './schutzausruestung-ff.service';

@Component({
    selector: 'jhi-schutzausruestung-ff-detail',
    templateUrl: './schutzausruestung-ff-detail.component.html'
})
export class SchutzausruestungFfDetailComponent implements OnInit, OnDestroy {

    schutzausruestung: SchutzausruestungFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private schutzausruestungService: SchutzausruestungFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSchutzausruestungs();
    }

    load(id) {
        this.schutzausruestungService.find(id).subscribe((schutzausruestung) => {
            this.schutzausruestung = schutzausruestung;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSchutzausruestungs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'schutzausruestungListModification',
            (response) => this.load(this.schutzausruestung.id)
        );
    }
}
