import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReportUsersContext } from 'src/app/shared/pagination-contexts/reports-users-context.class';
import { Router } from '@angular/router';
import { DataSharing } from 'src/app/shared/data-sharing.service';
import { ReportUsersService } from 'src/app/providers/http/reports-users.service';
import { NotificationModal } from 'src/app/shared/notification-modal/notification-modal.service';
import { PaginatedTableComponent } from 'src/app/shared/paginated-table/paginated-table.component';
import { ReportsUser } from 'src/app/interfaces/reports-user.interface';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-reports-users',
  templateUrl: './reports-users.component.html',
  styleUrls: ['./reports-users.component.scss']
})
export class ReportsUsersComponent implements OnInit {

  public form: FormGroup;
  public users: ReportsUser[];
  public format: string;
  userTableDataFieldsName: {displayData?: any[], inputData?: any[]};
  userTableDataFieldsColumns: any[];
  queryObject: any;
  userosOperacional: any[];
  subuserosOperacional: any[];
  codigosSistemaOrigen: any[];

  constructor(
    private formBuilder: FormBuilder,
    private usersContext: ReportUsersContext,
    private _router: Router,
    private dataSharing: DataSharing,
    private usersService: ReportUsersService,
    private datePipe: DatePipe
  ) { }

  @ViewChild(PaginatedTableComponent)
  paginatedTable: PaginatedTableComponent;

  ngOnInit() {
    this.buildForm();
    this.format = 'dd/MM/yyyy';

    // CHECK

    this.userTableDataFieldsName = {
      displayData: [
        {fieldName:   'identificador'},
        {fieldName:   'codigoEntidad'},
        {fieldName:   'numeroPersona'},
        {fieldName:   'codigoPais'},
        {fieldName:   'tip_DOC_ID'},
        {fieldName:   'numeroDocumento'},
        {fieldName:   'sec_NUM_DOC'},
        {fieldName:   'nombre'},
        {fieldName:   'apellidoPaterno'},
        {fieldName:   'apellidoMaterno'},
        {fieldName:   'pais'},
        {fieldName:   'tipoPersona'},
        {fieldName:   'codCondicionesCli'},
        {fieldName:   'codSituacionCli'},
        {fieldName:   'estadoCliente'},
        {fieldName:   'codVinculaCliente'},
        {fieldName:   'codCentroContable'},
        {fieldName:   'codOficinaCliente'},
        {fieldName:   'sexo'},
        {fieldName:   'codigoSegmento'},
        {fieldName:   'codigoSegmentoNegocio'},
        {fieldName:   'codigoSegmentoRiesgo'},
        {fieldName:   'codSectorContable'},
        {fieldName:   'codSectorActividad'},
        {fieldName:   'indEmpleado'},
        {fieldName:   'indBanca'},
        {fieldName:   'indConfidencialidad'},
        {fieldName:   'indCarterizado'},
        {fieldName:   'fechaAlta'},
        {fieldName:   'fechaBaja'},
        {fieldName:   'fechaData'},
        {fieldName:   'fechaAltaODS'},
        {fieldName:   'codigoProceso'},
        {fieldName:   'usuario'},
        {fieldName:   'timest_UMO'},
        {fieldName:   'codigoCartera'}
      ]
    };
    this.userTableDataFieldsColumns = [
      {value: 'Identificador'},
      {value: 'Código Entidad'},
      {value: 'Numero Persona'},
      {value: 'Código Pais'},
      {value: 'TIP_DOC_ID'},
      {value: 'Numero Documento'},
      {value: 'SEC_NUM_DOC'},
      {value: 'Nombre'},
      {value: 'Apellido Paterno'},
      {value: 'Apellido Materno'},
      {value: 'Pais'},
      {value: 'Tipo Persona'},
      {value: 'Cód. Condiciones Cli'},
      {value: 'Cód. Situaciones Cli'},
      {value: 'Estado Cliente'},
      {value: 'Cód. Vincula Cliente'},
      {value: 'Cód. Centro Contable'},
      {value: 'Cód. Oficina Cliente'},
      {value: 'Sexo'},
      {value: 'Cód. Segmento'},
      {value: 'Cód. Segmento Negocio'},
      {value: 'Cód. Segmento Riesgo'},
      {value: 'Cód. Sector Contable'},
      {value: 'Cód. Sector Actividad'},
      {value: 'indEmpleado'},
      {value: 'indBanca'},
      {value: 'indConfidencialidad'},
      {value: 'indCarterizado'},
      {value: 'Fecha Alta'},
      {value: 'Fecha Baja'},
      {value: 'Fecha Data'},
      {value: 'Fecha AltaODS'},
      {value: 'Cód. Proceso'},
      {value: 'Usuario'},
      {value: 'TIMEST_UMO'},
      {value: 'Cód. Cartera'}
    ];
    this.queryObject = { };
    this.search();
  }

  buildForm() {
    this.form = this.formBuilder.group({
      numeroPersona: ['', [Validators.required]],
      codigoEntidad: ['', []]
    });
  }

  // MAY BE DELETE
  editUser(user) {
    this.dataSharing.store('user', user);
    this._router.navigate(['/users/edit']);
  }

  search() {
    const queryObject = { ...this.queryObject, ...this.form.value };
    this.paginatedTable.notifyCurrentPage(queryObject);
  }

  getUsersPaginationContext() {
    return this.usersContext;
  }

  getRestCallFunction() {
    return this.usersService.getExcelReport.bind(this.usersService);
  }

  getApplicationProductSourceFileName() {
    const formattedDate = this.datePipe.transform(new Date(), this.format);
    return `informe-persona_${ formattedDate }.xlsx`;
  }

  getDownloadedFileType() {
    return 'application/vnd.ms-excel';
  }

}
