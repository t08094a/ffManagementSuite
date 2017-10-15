import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { DienstzeitFf } from './dienstzeit-ff.model';
import { DienstzeitFfService } from './dienstzeit-ff.service';

@Component({
    selector: 'jhi-dienstzeit-ff-detail',
    templateUrl: './dienstzeit-ff-detail.component.html'
})
export class DienstzeitFfDetailComponent implements OnInit, OnDestroy {

    dienstzeit: DienstzeitFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dienstzeitService: DienstzeitFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDienstzeits();
    }

    load(id) {
        this.dienstzeitService.find(id).subscribe((dienstzeit) => {
            this.dienstzeit = dienstzeit;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDienstzeits() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dienstzeitListModification',
            (response) => this.load(this.dienstzeit.id)
        );
    }
}
