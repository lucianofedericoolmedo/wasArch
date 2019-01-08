import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HierarchyService } from 'src/app/providers/http/hierarchy.service';

@Component({
  selector: 'app-global-groups-create-update',
  templateUrl: './global-groups-create-update.component.html',
  styleUrls: ['./global-groups-create-update.component.scss']
})
export class GlobalGroupsCreateUpdateComponent implements OnInit {
  form: FormGroup;
  dimensionsCodes: string[];
  hierarchiesCodes: string[];
  parentsLevels: string[];
  parentsValuesCodes: string[];
  editing = false;

  constructor(
    private formBuilder: FormBuilder,
    private hierarchyService: HierarchyService
  ) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      codigoDimension: ['', [Validators.required]],
      codigoJerarquia: ['', [Validators.required]],
      nivelPadre:      ['', [Validators.required]],
      valorPadre:      ['', [Validators.required]],
      valorNodo:       ['', [Validators.required]],
      descripcion:     ['', [Validators.required]],
    });

    this.getDimensionsCodes();
    this.getHierarchiesCodes();
    this.getParentsLevels();
    this.getParentsValuesCodes();
  }

  createOrUpdate() {
    if (this.form.valid) {
      const nodeToCreateOrUpdate = this.form.value;
      this.hierarchyService.postNodo(nodeToCreateOrUpdate);
      console.log('Create global group ', nodeToCreateOrUpdate);
    } else {
      console.log('Invalid form');
    }
  }

  getDimensionsCodes() {
    this.hierarchyService.getCodigosDimensiones().subscribe((data: string[]) => this.dimensionsCodes = data);
  }

  getHierarchiesCodes() {
    this.hierarchyService.getCodigosJerarquias().subscribe((data: string[]) => this.hierarchiesCodes = data);
  }

  getParentsLevels() {
    this.hierarchyService.getNivelesPadres().subscribe((data: string[]) => this.parentsLevels = data);
  }

  getParentsValuesCodes() {
    this.hierarchyService.getCodigosValoresPadres().subscribe((data: string[]) => this.parentsValuesCodes = data);
  }

}
