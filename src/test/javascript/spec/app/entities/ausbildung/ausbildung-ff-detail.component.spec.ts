/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AusbildungFfDetailComponent } from '../../../../../../main/webapp/app/entities/ausbildung/ausbildung-ff-detail.component';
import { AusbildungFfService } from '../../../../../../main/webapp/app/entities/ausbildung/ausbildung-ff.service';
import { AusbildungFf } from '../../../../../../main/webapp/app/entities/ausbildung/ausbildung-ff.model';

describe('Component Tests', () => {

    describe('AusbildungFf Management Detail Component', () => {
        let comp: AusbildungFfDetailComponent;
        let fixture: ComponentFixture<AusbildungFfDetailComponent>;
        let service: AusbildungFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [AusbildungFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AusbildungFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(AusbildungFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AusbildungFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AusbildungFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AusbildungFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ausbildung).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
