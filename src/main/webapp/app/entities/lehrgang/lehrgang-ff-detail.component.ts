import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LehrgangFf } from './lehrgang-ff.model';
import { LehrgangFfService } from './lehrgang-ff.service';

@Component({
    selector: 'jhi-lehrgang-ff-detail',
    templateUrl: './lehrgang-ff-detail.component.html'
})
export class LehrgangFfDetailComponent implements OnInit, OnDestroy {

    lehrgang: LehrgangFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private lehrgangService: LehrgangFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLehrgangs();
    }

    load(id) {
        this.lehrgangService.find(id).subscribe((lehrgang) => {
            this.lehrgang = lehrgang;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLehrgangs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lehrgangListModification',
            (response) => this.load(this.lehrgang.id)
        );
    }
}
