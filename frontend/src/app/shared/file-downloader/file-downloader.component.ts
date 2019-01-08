import { Component, OnInit, Input } from '@angular/core';
import { Observable } from 'rxjs';

/**
 * This component is for downloading files from an function which returns and observable with some data.
 * Inputs:
 * * btnLabel: A string that will display in the download button;
 * * restCallFunction: A callback function without parameters that will return an Observable to get the data from
 * * fileName: A string that will be used as the file name once downloaded. **IT SHOULD CONTAINT THE FILE EXTENSION AS WELL**
 * * fileType: A string representing the type of the file to be dowloaded.
 */
@Component({
  selector: 'app-file-downloader',
  templateUrl: './file-downloader.component.html',
  styleUrls: ['./file-downloader.component.scss']
})
export class FileDownloaderComponent implements OnInit {

  constructor() { }

  @Input() infoMsg: string;
  @Input() restCallFunction: () => Observable<any>;
  @Input() fileName: string;
  @Input() fileType: string;

  ngOnInit() {
  }

  download() {
    this.restCallFunction().subscribe(data => this.generateBlob(data));
  }

  generateBlob(data) {
    const blob = new Blob([data], { type: this.fileType });
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.setAttribute('href', url);
    link.setAttribute('download', this.fileName);
    link.click();
  }

}
