import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FahrzeugFf } from './fahrzeug-ff.model';
import { FahrzeugFfService } from './fahrzeug-ff.service';

@Component({
    selector: 'jhi-fahrzeug-ff-detail',
    templateUrl: './fahrzeug-ff-detail.component.html'
})
export class FahrzeugFfDetailComponent implements OnInit, OnDestroy {

    fahrzeug: FahrzeugFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private fahrzeugService: FahrzeugFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFahrzeugs();
    }

    load(id) {
        this.fahrzeugService.find(id).subscribe((fahrzeug) => {
            this.fahrzeug = fahrzeug;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFahrzeugs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'fahrzeugListModification',
            (response) => this.load(this.fahrzeug.id)
        );
    }
}
