import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { OrganisationsstrukturFf } from './organisationsstruktur-ff.model';
import { OrganisationsstrukturFfService } from './organisationsstruktur-ff.service';

@Component({
    selector: 'jhi-organisationsstruktur-ff-detail',
    templateUrl: './organisationsstruktur-ff-detail.component.html'
})
export class OrganisationsstrukturFfDetailComponent implements OnInit, OnDestroy {

    organisationsstruktur: OrganisationsstrukturFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private organisationsstrukturService: OrganisationsstrukturFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrganisationsstrukturs();
    }

    load(id) {
        this.organisationsstrukturService.find(id).subscribe((organisationsstruktur) => {
            this.organisationsstruktur = organisationsstruktur;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrganisationsstrukturs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'organisationsstrukturListModification',
            (response) => this.load(this.organisationsstruktur.id)
        );
    }
}
