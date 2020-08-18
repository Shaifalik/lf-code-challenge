import { HttpClient } from '@angular/common/http';
import { throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { retry, catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { GreetingData } from './model/greeting';

@Injectable()
export class HelloWorldService {
  private _URL = "http://localhost:8080/hello";
  constructor(private http: HttpClient) {}

  
  getGreetingData(id: string):any{
    if(!id)
    return this.http.get(this._URL)
    else
    return this.http.get(this._URL+"/"+id).pipe(
      catchError(this.handleError)
    );
  }

  createGreetingData(data: GreetingData):any{
    return this.http.post(this._URL+"/create",data).pipe(
      catchError(this.handleError)
    );
  }

  updateGreetingData(data: GreetingData):any{
    return this.http.put(this._URL+"/update/"+data.id,data).pipe(
      catchError(this.handleError)
    );
  }

private handleError(error) {
  if(error.status === '422'|| error.status=== '400'){
    return throwError(error.error.validationErrors[0].field +" "+ error.error.validationErrors[0].message);  
  }
return throwError(error.error.message);
  
}
  
}