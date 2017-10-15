import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AtemschutzInventarFf } from './atemschutz-inventar-ff.model';
import { AtemschutzInventarFfService } from './atemschutz-inventar-ff.service';

@Component({
    selector: 'jhi-atemschutz-inventar-ff-detail',
    templateUrl: './atemschutz-inventar-ff-detail.component.html'
})
export class AtemschutzInventarFfDetailComponent implements OnInit, OnDestroy {

    atemschutzInventar: AtemschutzInventarFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private atemschutzInventarService: AtemschutzInventarFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAtemschutzInventars();
    }

    load(id) {
        this.atemschutzInventarService.find(id).subscribe((atemschutzInventar) => {
            this.atemschutzInventar = atemschutzInventar;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAtemschutzInventars() {
        this.eventSubscriber = this.eventManager.subscribe(
            'atemschutzInventarListModification',
            (response) => this.load(this.atemschutzInventar.id)
        );
    }
}
