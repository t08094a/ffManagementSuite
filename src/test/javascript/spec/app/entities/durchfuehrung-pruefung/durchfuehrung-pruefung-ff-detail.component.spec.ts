/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DurchfuehrungPruefungFfDetailComponent } from '../../../../../../main/webapp/app/entities/durchfuehrung-pruefung/durchfuehrung-pruefung-ff-detail.component';
import { DurchfuehrungPruefungFfService } from '../../../../../../main/webapp/app/entities/durchfuehrung-pruefung/durchfuehrung-pruefung-ff.service';
import { DurchfuehrungPruefungFf } from '../../../../../../main/webapp/app/entities/durchfuehrung-pruefung/durchfuehrung-pruefung-ff.model';

describe('Component Tests', () => {

    describe('DurchfuehrungPruefungFf Management Detail Component', () => {
        let comp: DurchfuehrungPruefungFfDetailComponent;
        let fixture: ComponentFixture<DurchfuehrungPruefungFfDetailComponent>;
        let service: DurchfuehrungPruefungFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [DurchfuehrungPruefungFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DurchfuehrungPruefungFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(DurchfuehrungPruefungFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DurchfuehrungPruefungFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DurchfuehrungPruefungFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DurchfuehrungPruefungFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.durchfuehrungPruefung).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
