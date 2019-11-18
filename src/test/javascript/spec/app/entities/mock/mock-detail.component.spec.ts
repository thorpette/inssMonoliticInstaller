import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InssMonoliticInstallerTestModule } from '../../../test.module';
import { MockDetailComponent } from 'app/entities/mock/mock-detail.component';
import { Mock } from 'app/shared/model/mock.model';

describe('Component Tests', () => {
  describe('Mock Management Detail Component', () => {
    let comp: MockDetailComponent;
    let fixture: ComponentFixture<MockDetailComponent>;
    const route = ({ data: of({ mock: new Mock(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InssMonoliticInstallerTestModule],
        declarations: [MockDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MockDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MockDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mock).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
