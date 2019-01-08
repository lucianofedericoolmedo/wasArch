import { Injectable } from '@angular/core';
import { NgbDateParserFormatter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

@Injectable()
export class NgbDateCustomParserFormatter extends NgbDateParserFormatter {
  isNumber(possibleNumber: any): boolean {
      return !isNaN(Number(possibleNumber));
  }

  padNumber(_number: number): string {
      let numberInString = _number.toString();
      numberInString = '00' + numberInString;
      numberInString = numberInString.slice(-2);
      return numberInString;
  }

  parse(value: string): NgbDateStruct {
    if (value) {
      const dateParts = value.trim().split('-');
      if (dateParts.length === 1 && this.isNumber(dateParts[0])) {
        return {day: Number(dateParts[0]), month: null, year: null};
      } else if (dateParts.length === 2 && this.isNumber(dateParts[0]) && this.isNumber(dateParts[1])) {
        return {day: Number(dateParts[0]), month: Number(dateParts[1]), year: null};
      } else if (dateParts.length === 3 && this.isNumber(dateParts[0]) && this.isNumber(dateParts[1]) && this.isNumber(dateParts[2])) {
        return {day: Number(dateParts[0]), month: Number(dateParts[1]), year: Number(dateParts[2])};
      }
    }
    return null;
  }

  format(date: NgbDateStruct): string {
    if (date) {
      const dateDay = this.isNumber(date.day) ? this.padNumber(date.day) : '';
      const dateMonth = this.isNumber(date.month) ? this.padNumber(date.month) : '';
      const dateYear = date.year;
      return `${dateDay}/${dateMonth}/${dateYear}`;
    } else {
      return '';
    }
  }

}
