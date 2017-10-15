import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuspraegungFf } from './auspraegung-ff.model';
import { AuspraegungFfService } from './auspraegung-ff.service';

@Component({
    selector: 'jhi-auspraegung-ff-detail',
    templateUrl: './auspraegung-ff-detail.component.html'
})
export class AuspraegungFfDetailComponent implements OnInit, OnDestroy {

    auspraegung: AuspraegungFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auspraegungService: AuspraegungFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuspraegungs();
    }

    load(id) {
        this.auspraegungService.find(id).subscribe((auspraegung) => {
            this.auspraegung = auspraegung;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuspraegungs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'auspraegungListModification',
            (response) => this.load(this.auspraegung.id)
        );
    }
}
