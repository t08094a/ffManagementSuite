/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DienststellungFfDetailComponent } from '../../../../../../main/webapp/app/entities/dienststellung/dienststellung-ff-detail.component';
import { DienststellungFfService } from '../../../../../../main/webapp/app/entities/dienststellung/dienststellung-ff.service';
import { DienststellungFf } from '../../../../../../main/webapp/app/entities/dienststellung/dienststellung-ff.model';

describe('Component Tests', () => {

    describe('DienststellungFf Management Detail Component', () => {
        let comp: DienststellungFfDetailComponent;
        let fixture: ComponentFixture<DienststellungFfDetailComponent>;
        let service: DienststellungFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [DienststellungFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DienststellungFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(DienststellungFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DienststellungFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DienststellungFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DienststellungFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.dienststellung).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
