/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OrganisationsstrukturFfDetailComponent } from '../../../../../../main/webapp/app/entities/organisationsstruktur/organisationsstruktur-ff-detail.component';
import { OrganisationsstrukturFfService } from '../../../../../../main/webapp/app/entities/organisationsstruktur/organisationsstruktur-ff.service';
import { OrganisationsstrukturFf } from '../../../../../../main/webapp/app/entities/organisationsstruktur/organisationsstruktur-ff.model';

describe('Component Tests', () => {

    describe('OrganisationsstrukturFf Management Detail Component', () => {
        let comp: OrganisationsstrukturFfDetailComponent;
        let fixture: ComponentFixture<OrganisationsstrukturFfDetailComponent>;
        let service: OrganisationsstrukturFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [OrganisationsstrukturFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OrganisationsstrukturFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(OrganisationsstrukturFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrganisationsstrukturFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrganisationsstrukturFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OrganisationsstrukturFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.organisationsstruktur).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
