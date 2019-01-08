# AppIsban

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 7.0.2.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).

## How to use the paginated-table component

To use the pagination table component, one must define the headers that the table will contain and how will the data for each row show. For making the Http requests, the component must receive the *pagination context* defined for data that we want to use.

In the template we will use the component as:

```
    <app-paginated-table 
        [context]="getProductsPaginationContext()"
        [fields]="dataTableFields"
        [columns]="dataTableColumns"
        [query]="queryObject">
    </app-paginated-table>
```

In the parent component definition we define the objects that indicate how the data will be shown. We define the columns headers:

```
    this.dataTableColumns = [
      'A column displaying data', 'Another column displaying data', 'A column with a button', 'A column with many buttons'
    ];
```

Then we define the objects' fields names that we will obtain from each object in the datasource:

```
    this.dataTableFields = [
      {fieldName:'firstColumnData'},
      {fieldName:'secondColumnData'},
      {buttonLabel:'A BUTTON', buttonAction:this._aFunctionForThisButton.bind(this)},
      {buttons: [
        {buttonLabel:'BTN 1', buttonAction:this._aFunctionForButton1.bind(this)},
        {buttonLabel:'BTN 2', buttonAction:this._aFunctionForButton2.bind(this)},
        {buttonLabel:'BTN 3', buttonAction:this._aFunctionForButton3.bind(this)},
      ]}
    ];

```

Each button created by the table will contain the class `app-btn` to mantain the same style defined for the app.

In the parent component we define a simple object that contains all the fields we want to send as query in the request.

```
    this.queryObject = {
      firstName: 'firstName', // This field will be in the request
      userName: '',           // This field will be in the request
      age: 50                 // This field will be in the request
      email: null             // This field will NOT be in the request
    }
```

To defined a context for the pagination table we need to create a class as follow:

```
    @Injectable({
    providedIn: SharedModule
    })
    export class MyClassContext extends PaginationContext<MyClass> {

      public pageSize: number = 10;

      constructor(public myClassService: MyClassService) {
        super();
      }

      pageChangeSubscription(pageNumber: number, queryData: any): void {
        this.search(pageNumber, queryData);
      }

      private search(pageNumber: number, queryData: any): void {
        this.myClassService.query(pageNumber, this.pageSize, queryData)
          .subscribe((data: PageResponse<MyClass>) => {
            this.subject$.next(data);
          });
      }

    }
```

The `query` method defined for myClassService must be able to receive a parameter for the pageNumber and pageSize with type `number` and an object containing the query data, and return a asynchronous data with the structure defined by the `PageResponse`.

If we need to trigger the search request from the parent component then we need the reference to the PaginatedTableComponent.

```
  @ViewChild(PaginatedTableComponent)
  paginatedTable: PaginatedTableComponent;

  searchTriggeredFromButtonInParentComponent() {
    this.paginatedTable.notifyCurrentPage(this.queryObject);
  }
```
