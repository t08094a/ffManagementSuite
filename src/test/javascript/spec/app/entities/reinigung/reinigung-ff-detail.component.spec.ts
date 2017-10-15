/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ReinigungFfDetailComponent } from '../../../../../../main/webapp/app/entities/reinigung/reinigung-ff-detail.component';
import { ReinigungFfService } from '../../../../../../main/webapp/app/entities/reinigung/reinigung-ff.service';
import { ReinigungFf } from '../../../../../../main/webapp/app/entities/reinigung/reinigung-ff.model';

describe('Component Tests', () => {

    describe('ReinigungFf Management Detail Component', () => {
        let comp: ReinigungFfDetailComponent;
        let fixture: ComponentFixture<ReinigungFfDetailComponent>;
        let service: ReinigungFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [ReinigungFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ReinigungFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(ReinigungFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReinigungFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReinigungFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ReinigungFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.reinigung).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
