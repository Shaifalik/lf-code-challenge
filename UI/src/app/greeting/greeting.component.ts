import { Component, OnInit } from '@angular/core';
import { GreetingData } from '././model/greeting';
import { HelloWorldService } from '../greeting/HelloWorldService';
import { AuthenticationService } from '../login/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ThrowStmt } from '@angular/compiler';

@Component({
  selector: 'app-greeting',
  templateUrl: './greeting.component.html',
  styleUrls: ['./greeting.component.css'],
  providers: [HelloWorldService, AuthenticationService]
})
export class GreetingComponent implements OnInit {

  message: string;
  error: boolean = false;
  success: boolean = false;
  newerror:boolean = false;
  newsuccess:boolean = false;
  editerror:boolean = false;
  editsuccess:boolean = false;
  greeting = new GreetingData();
  greetingID: string;
  errorMessage: string;
  newId:string;
  newMessage:string
  isLoggedIn = false
  editId:string;
  editMessage:string;
  constructor(private route: ActivatedRoute,
    private router: Router,
    private helloWorldService: HelloWorldService, private authenticationService: AuthenticationService) {

  }

  ngOnInit() {
    this.isLoggedIn = this.authenticationService.isUserLoggedIn();
    console.log('menu ->' + this.isLoggedIn);
    if(!this.isLoggedIn){
     this.router.navigate(['/logout']);
    }

    this.helloWorldService.getGreetingData("").subscribe(
      data => {
        this.message = data.message;
        this.error = false;
      },
      error => {
        this.errorMessage = error;
      }
    );
  }

  resetVariables(){
    this.error=false;
    this.success=false;
    this.newerror=false;
    this.newsuccess=false;
    this.editerror=false;
    this.editsuccess=false;
  }

  showMessage() {
    this.resetVariables();
    this.helloWorldService.getGreetingData(this.greetingID).subscribe(
      data => {
        this.success = true;
        this.error=false;
        this.greeting.id = data.id;
        this.greeting.message = data.message;
      },
      error => {
        this.error = true;
        this.success=false;
        this.errorMessage = error;
      }
    )
  }

  addMessage() {
    this.resetVariables();
    this.greeting.id=this.newId;
    this.greeting.message=this.newMessage;
    this.helloWorldService.createGreetingData(this.greeting).subscribe(
      data => {
        this.newsuccess = true;
        this.greeting.message = "Successfully created with "+ data.id;
      },
      error => {
        this.newerror = true;
        this.errorMessage = error;
      }
    )
  }

  changeMessage() {
    this.resetVariables();
    this.greeting.id=this.editId;
    this.greeting.message=this.editMessage;
    this.helloWorldService.updateGreetingData(this.greeting).subscribe(
      data => {
        this.editsuccess = true;
        this.greeting.message = "Message successfully updated with  "+ data.message;
      },
      error => {
        this.editerror = true;
        this.errorMessage = error;
      }
    )
}

  handleLogout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }
}
