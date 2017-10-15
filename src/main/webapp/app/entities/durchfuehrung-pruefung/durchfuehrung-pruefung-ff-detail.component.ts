import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { DurchfuehrungPruefungFf } from './durchfuehrung-pruefung-ff.model';
import { DurchfuehrungPruefungFfService } from './durchfuehrung-pruefung-ff.service';

@Component({
    selector: 'jhi-durchfuehrung-pruefung-ff-detail',
    templateUrl: './durchfuehrung-pruefung-ff-detail.component.html'
})
export class DurchfuehrungPruefungFfDetailComponent implements OnInit, OnDestroy {

    durchfuehrungPruefung: DurchfuehrungPruefungFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private durchfuehrungPruefungService: DurchfuehrungPruefungFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDurchfuehrungPruefungs();
    }

    load(id) {
        this.durchfuehrungPruefungService.find(id).subscribe((durchfuehrungPruefung) => {
            this.durchfuehrungPruefung = durchfuehrungPruefung;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDurchfuehrungPruefungs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'durchfuehrungPruefungListModification',
            (response) => this.load(this.durchfuehrungPruefung.id)
        );
    }
}
