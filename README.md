# Labforward Code Challenge for Backend Engineer Candidate

This is a simple Hello World API for recruiting purposes. You, as a candidate, should work on the challenge on your own account. Please clone the repo to your account and create a PR with your solution. 

## Introduction

You can run the application by typing

	./gradlew bootRun

This will start up a Spring Boot application with Tomcat server running on 8080.

Show all other possible tasks:

	./gradlew tasks
	
## Your Task	

You need to add a new endpoint to the API to allow users to *update the greetings they created*. 

## Acceptance Criteria

This task is purposefully open-ended. You are free to come up with your own implementation based on your assumptions. You are also welcome to improve the existing code by refactoring, cleaning up, etc. where necessary. Hint: there is a missing core piece in the application :) 

Extra points for describing a user interface which utilizes the API with the new endpoint. This can be a text document, simple mock-ups, or even an interactive HTML proof-of-concept. Be creative and show us how you approach UI problems.

We understand that not everyone has the same amount of "extra" time. It is also up to you to determine the amount of time you spend on the exercise. So that the reviewer understands how you are defining the scope of work, please clearly indicate your own “Definition of Done” for the task in a README file along with any other pertinent information.

Regardless of how far you take the solution towards completion, please assume you are writing production code. Your solution should clearly communicate your development style, abilities, and approach to problem solving. 

Let us know if you have any questions, and we look forward to seeing your approach.

Good Luck!

Let us know if you have any questions, and we look forward to seeing your approach.

Good Luck!

## Solution Completed

Definition of Done
1. Code completed with quality i.e both Frontend and Backend.
2. Code tested with good code coverage.
3. API documented (OPEN API 3.0).
4. URL exposed with authentication.

#Backend:
Core Pieces:( as per my understanding)
- Added Repository layer that interacts with Database i.e. HelloWorldRepository (must part of Micro service)
- Added DTO layer as exposing directly DO object is a big security concern
- Added Basic Authentication spring security to allow only authorized user to access the endpoints.

Other things:
- Added update greeting endpoint to update an existing greeting.
- Added Open API 3.0 for endpoint documentation (http://localhost:8080/swagger-ui.html)
- Refactored the code and added more validation logic.
- Added and modified test cases to increase the code coverage.
- Added CORS configuration with Spring security.
  Technologies used- JAVA 8 ,SPRING BOOT FRAMEWORK
  
#UI 
-Added Simpler Angular UI for endpoints.
-Added Login page to support authorization (http://localhost:4200/login)
Credentials:
USERNAME: user
PASSWORD: password
 Technologies used: ANGULAR 10, NODE JS 12.11,BOOTSTRAP


#Steps to run for running Backend app
1. Import the project in Intellij or eclipse.
2. Build the project.
3. Run the project from SpringBootApplication class.
Project will be available on http://localhost:8080
Tool- Intellij/Eclipse

#Steps to run for running UI app
1. Install node js and then download angular.
2. Run ng build and then ng serve
3. Project will be available on http://localhost:4200
Tool- Visual Studio

As both frontend and backend is up, you can use UI to make the calls.

Thank you.