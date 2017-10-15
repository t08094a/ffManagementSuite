import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { VerfuegbarkeitFf } from './verfuegbarkeit-ff.model';
import { VerfuegbarkeitFfService } from './verfuegbarkeit-ff.service';

@Component({
    selector: 'jhi-verfuegbarkeit-ff-detail',
    templateUrl: './verfuegbarkeit-ff-detail.component.html'
})
export class VerfuegbarkeitFfDetailComponent implements OnInit, OnDestroy {

    verfuegbarkeit: VerfuegbarkeitFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private verfuegbarkeitService: VerfuegbarkeitFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVerfuegbarkeits();
    }

    load(id) {
        this.verfuegbarkeitService.find(id).subscribe((verfuegbarkeit) => {
            this.verfuegbarkeit = verfuegbarkeit;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVerfuegbarkeits() {
        this.eventSubscriber = this.eventManager.subscribe(
            'verfuegbarkeitListModification',
            (response) => this.load(this.verfuegbarkeit.id)
        );
    }
}
