import { NgbDatepickerI18n, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

export class NgbDateCustomLanguage extends NgbDatepickerI18n {

  getDayAriaLabel(date: NgbDateStruct): string {
    return `${date.day}/${date.month}/${date.year}`;
  }

  getWeekdayShortName(weekday: number): string {
    return ['Lun',
            'Mar',
            'Mie',
            'Jue',
            'Vie',
            'Sáb',
            'Dom'][weekday - 1];
  }

  getMonthShortName(month: number, year?: number): string {
    return ['Ene',
            'Feb',
            'Mar',
            'Abr',
            'May',
            'Jun',
            'Jul',
            'Ago',
            'Sep',
            'Oct',
            'Nov',
            'Dic'][month - 1];
  }

  getMonthFullName(month: number, year?: number): string {
    return ['Enero',
            'Febrero',
            'Marzo',
            'Abril',
            'Mayo',
            'Junio',
            'Julio',
            'Agosto',
            'Septiembre',
            'Octubre',
            'Noviembre',
            'Diciembre'][month - 1];
  }

}
