/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ErreichbarkeitFfDetailComponent } from '../../../../../../main/webapp/app/entities/erreichbarkeit/erreichbarkeit-ff-detail.component';
import { ErreichbarkeitFfService } from '../../../../../../main/webapp/app/entities/erreichbarkeit/erreichbarkeit-ff.service';
import { ErreichbarkeitFf } from '../../../../../../main/webapp/app/entities/erreichbarkeit/erreichbarkeit-ff.model';

describe('Component Tests', () => {

    describe('ErreichbarkeitFf Management Detail Component', () => {
        let comp: ErreichbarkeitFfDetailComponent;
        let fixture: ComponentFixture<ErreichbarkeitFfDetailComponent>;
        let service: ErreichbarkeitFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [ErreichbarkeitFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ErreichbarkeitFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(ErreichbarkeitFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ErreichbarkeitFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ErreichbarkeitFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ErreichbarkeitFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.erreichbarkeit).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
