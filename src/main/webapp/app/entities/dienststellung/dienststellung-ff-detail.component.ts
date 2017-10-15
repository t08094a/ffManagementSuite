import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { DienststellungFf } from './dienststellung-ff.model';
import { DienststellungFfService } from './dienststellung-ff.service';

@Component({
    selector: 'jhi-dienststellung-ff-detail',
    templateUrl: './dienststellung-ff-detail.component.html'
})
export class DienststellungFfDetailComponent implements OnInit, OnDestroy {

    dienststellung: DienststellungFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dienststellungService: DienststellungFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDienststellungs();
    }

    load(id) {
        this.dienststellungService.find(id).subscribe((dienststellung) => {
            this.dienststellung = dienststellung;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDienststellungs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dienststellungListModification',
            (response) => this.load(this.dienststellung.id)
        );
    }
}
