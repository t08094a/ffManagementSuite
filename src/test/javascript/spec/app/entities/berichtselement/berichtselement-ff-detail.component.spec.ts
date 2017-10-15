/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BerichtselementFfDetailComponent } from '../../../../../../main/webapp/app/entities/berichtselement/berichtselement-ff-detail.component';
import { BerichtselementFfService } from '../../../../../../main/webapp/app/entities/berichtselement/berichtselement-ff.service';
import { BerichtselementFf } from '../../../../../../main/webapp/app/entities/berichtselement/berichtselement-ff.model';

describe('Component Tests', () => {

    describe('BerichtselementFf Management Detail Component', () => {
        let comp: BerichtselementFfDetailComponent;
        let fixture: ComponentFixture<BerichtselementFfDetailComponent>;
        let service: BerichtselementFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [BerichtselementFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BerichtselementFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(BerichtselementFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BerichtselementFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BerichtselementFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BerichtselementFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.berichtselement).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
