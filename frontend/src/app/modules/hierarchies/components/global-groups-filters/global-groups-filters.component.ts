import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { HierarchyService } from 'src/app/providers/http/hierarchy.service';

@Component({
  selector: 'app-global-groups-filters',
  templateUrl: './global-groups-filters.component.html',
  styleUrls: ['./global-groups-filters.component.scss']
})
export class GlobalGroupsFiltersComponent implements OnInit {
  form: FormGroup;
  dimensionsCodes: string[];
  hierarchiesCodes: string[];

  constructor(
    private formBuilder: FormBuilder,
    private hierarchyService: HierarchyService
  ) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      dimension: ['', [Validators.required]],
      jerarquia: ['', [Validators.required]],
      nivel: ['', []]
    });

    this.getDimensionsCodes();
    this.getHierarchiesCodes();
  }

  search() {
    const queryObject = { ...this.form.value };
  }

  getDimensionsCodes() {
    this.hierarchyService.getCodigosDimensiones().subscribe((data: string[]) => this.dimensionsCodes = data);
  }

  getHierarchiesCodes() {
    this.hierarchyService.getCodigosJerarquias().subscribe((data: string[]) => this.hierarchiesCodes = data);
  }

}
