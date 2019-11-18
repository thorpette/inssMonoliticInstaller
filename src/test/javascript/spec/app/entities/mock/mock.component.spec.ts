import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { InssMonoliticInstallerTestModule } from '../../../test.module';
import { MockComponent } from 'app/entities/mock/mock.component';
import { MockService } from 'app/entities/mock/mock.service';
import { Mock } from 'app/shared/model/mock.model';

describe('Component Tests', () => {
  describe('Mock Management Component', () => {
    let comp: MockComponent;
    let fixture: ComponentFixture<MockComponent>;
    let service: MockService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InssMonoliticInstallerTestModule],
        declarations: [MockComponent],
        providers: []
      })
        .overrideTemplate(MockComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MockComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MockService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Mock(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.mocks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
