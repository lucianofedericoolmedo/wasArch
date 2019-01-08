import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class DataSharing {

    private data: any;

    constructor() {
        this.data = {};
    }

    public store(dataname: string, data: any) {
        this.data[dataname] = data;
    }

    public getData(dataname: string) {
        return this.data[dataname];
    }

    public clearData(dataname: string) {
        delete this.data[dataname];
    }

}
