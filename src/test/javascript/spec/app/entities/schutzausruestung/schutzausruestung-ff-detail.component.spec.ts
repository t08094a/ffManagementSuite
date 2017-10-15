/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SchutzausruestungFfDetailComponent } from '../../../../../../main/webapp/app/entities/schutzausruestung/schutzausruestung-ff-detail.component';
import { SchutzausruestungFfService } from '../../../../../../main/webapp/app/entities/schutzausruestung/schutzausruestung-ff.service';
import { SchutzausruestungFf } from '../../../../../../main/webapp/app/entities/schutzausruestung/schutzausruestung-ff.model';

describe('Component Tests', () => {

    describe('SchutzausruestungFf Management Detail Component', () => {
        let comp: SchutzausruestungFfDetailComponent;
        let fixture: ComponentFixture<SchutzausruestungFfDetailComponent>;
        let service: SchutzausruestungFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [SchutzausruestungFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SchutzausruestungFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(SchutzausruestungFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SchutzausruestungFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchutzausruestungFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SchutzausruestungFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.schutzausruestung).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
