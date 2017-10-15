/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LeistungspruefungFfDetailComponent } from '../../../../../../main/webapp/app/entities/leistungspruefung/leistungspruefung-ff-detail.component';
import { LeistungspruefungFfService } from '../../../../../../main/webapp/app/entities/leistungspruefung/leistungspruefung-ff.service';
import { LeistungspruefungFf } from '../../../../../../main/webapp/app/entities/leistungspruefung/leistungspruefung-ff.model';

describe('Component Tests', () => {

    describe('LeistungspruefungFf Management Detail Component', () => {
        let comp: LeistungspruefungFfDetailComponent;
        let fixture: ComponentFixture<LeistungspruefungFfDetailComponent>;
        let service: LeistungspruefungFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [LeistungspruefungFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LeistungspruefungFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(LeistungspruefungFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LeistungspruefungFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LeistungspruefungFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LeistungspruefungFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.leistungspruefung).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
