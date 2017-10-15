import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LeistungspruefungFf } from './leistungspruefung-ff.model';
import { LeistungspruefungFfService } from './leistungspruefung-ff.service';

@Component({
    selector: 'jhi-leistungspruefung-ff-detail',
    templateUrl: './leistungspruefung-ff-detail.component.html'
})
export class LeistungspruefungFfDetailComponent implements OnInit, OnDestroy {

    leistungspruefung: LeistungspruefungFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private leistungspruefungService: LeistungspruefungFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLeistungspruefungs();
    }

    load(id) {
        this.leistungspruefungService.find(id).subscribe((leistungspruefung) => {
            this.leistungspruefung = leistungspruefung;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLeistungspruefungs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'leistungspruefungListModification',
            (response) => this.load(this.leistungspruefung.id)
        );
    }
}
