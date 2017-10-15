/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { WartungFfDetailComponent } from '../../../../../../main/webapp/app/entities/wartung/wartung-ff-detail.component';
import { WartungFfService } from '../../../../../../main/webapp/app/entities/wartung/wartung-ff.service';
import { WartungFf } from '../../../../../../main/webapp/app/entities/wartung/wartung-ff.model';

describe('Component Tests', () => {

    describe('WartungFf Management Detail Component', () => {
        let comp: WartungFfDetailComponent;
        let fixture: ComponentFixture<WartungFfDetailComponent>;
        let service: WartungFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [WartungFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    WartungFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(WartungFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WartungFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WartungFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new WartungFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.wartung).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
