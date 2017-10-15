import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { AusbildungFf } from './ausbildung-ff.model';
import { AusbildungFfService } from './ausbildung-ff.service';

@Component({
    selector: 'jhi-ausbildung-ff-detail',
    templateUrl: './ausbildung-ff-detail.component.html'
})
export class AusbildungFfDetailComponent implements OnInit, OnDestroy {

    ausbildung: AusbildungFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private ausbildungService: AusbildungFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAusbildungs();
    }

    load(id) {
        this.ausbildungService.find(id).subscribe((ausbildung) => {
            this.ausbildung = ausbildung;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAusbildungs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ausbildungListModification',
            (response) => this.load(this.ausbildung.id)
        );
    }
}
