import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VirtualStageComponent } from './virtual-stage.component';

describe('VirtualStageComponent', () => {
  let component: VirtualStageComponent;
  let fixture: ComponentFixture<VirtualStageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VirtualStageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VirtualStageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
