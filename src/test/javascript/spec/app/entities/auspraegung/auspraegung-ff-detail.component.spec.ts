/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuspraegungFfDetailComponent } from '../../../../../../main/webapp/app/entities/auspraegung/auspraegung-ff-detail.component';
import { AuspraegungFfService } from '../../../../../../main/webapp/app/entities/auspraegung/auspraegung-ff.service';
import { AuspraegungFf } from '../../../../../../main/webapp/app/entities/auspraegung/auspraegung-ff.model';

describe('Component Tests', () => {

    describe('AuspraegungFf Management Detail Component', () => {
        let comp: AuspraegungFfDetailComponent;
        let fixture: ComponentFixture<AuspraegungFfDetailComponent>;
        let service: AuspraegungFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [AuspraegungFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuspraegungFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuspraegungFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuspraegungFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuspraegungFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuspraegungFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auspraegung).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
