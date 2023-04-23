CRM Ticket Hub
A web application for fetching and managing customer support tickets (cases).

Getting Started
To run this project locally, you'll need to have Java 11 and Maven installed.

Clone the repository: git clone https://github.com/elarevzin/crm-ticket-hub-app.git
Navigate to the project directory: cd crm-ticket-hub-app
Build the project: mvn clean install
Run the project: java -jar target/crm-ticket-hub-app.jar
Open a web browser and go to http://localhost:8080

Usage
API:
GET /api/crm-tickets: Returns a list of all CRM tickets, paginated according to the specified page and size parameters. The response is a paginated JSON object that contains information about each CRM ticket.

GET /api/crm-tickets/refresh: Refreshes all CRM tickets and returns the updated list, paginated according to the specified page and size parameters. The response is a paginated JSON object that contains information about each updated CRM ticket.

GET /api/crm-tickets/search: Searches for CRM tickets based on the specified parameters, and returns the results paginated according to the specified page and size parameters. The response is a paginated JSON object that contains information about each matching CRM ticket. The parameters that can be used for the search are:

errorCode (optional): The error code associated with the CRM ticket.
providerName (optional): The name of the provider associated with the CRM ticket.
status (optional): The status of the CRM ticket (OPEN, IN_PROGRESS, or CLOSED).

Built With
This project was built with the following tools and frameworks:

Spring Boot
Spring Data JPA
Maven

Authors
Ela Revzin (ela.revzin@gmail.com)
License
This project is licensed under the MIT License - see the LICENSE.md file for details.
