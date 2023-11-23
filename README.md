# Front-end Page repository:

https://github.com/Salza99/F-Build-week-2

# END-POINTS legend for Postman:

BaseAPI: http://localhost:3002

BillsPAID: {{baseAPI}}/bills/billpaidunpaid?state=PAID

BillsUNPAID: {{baseAPI}}/bills/billpaidunpaid?state=UNPAID

BillsPerDates: {{baseAPI}}/bills/billperdate?

BillsPerYear: {{baseAPI}}/bills/billperyears?year=

BillsPerAmounts: {{baseAPI}}/bills/billsperamounts?

BillsPerCompany: {{baseAPI}}/bills/billspercompany?name=

BillsListByClient: {{baseAPI}}/bills/clientbill/:id

AllClientPage: {{baseAPI}}/clients/:id

ClientsperIdClient: {{baseAPI}}/clients/:id

DeleteClientperId: {{baseAPI}}/clients/:id

ChangeClientperId: {{baseAPI}}/clients/changeclientinfo/:id

ClientsperTurnHover: {{baseAPI}}/clients/turnhover?

ClientsperData: {{baseAPI}}/clients/date?

ClientperDateLastContract: {{baseAPI}}/clients/lastcontract?

ClientsperCompany: {{baseAPI}}/clients/companyname?name=

AllUserPage: {{baseAPI}}/users

UsersperIdUsers: {{baseAPI}}/users/:id

DeleteUserperid: {{baseAPI}}/users/:id

CurrentUser: {{baseAPI}}/users/me

## Get Method

{{BillsPAID}} = Request that returns all paid bills.

{{BillsUNPAID}} = Request that returns all unpaid bills.

{{BillsperData}} = Request that returns all bills within that time period. NOTE: Include startDate=(YYYY-MM-DD)
&endDate=(YYYY-MM-DD).

{{BillsperYears}} = Request that returns all bills for the requested year. NOTE: Include (YYYY) .

{{BillsperAmounts}} = Request that retrieves all bills within a specified amount range. NOTE: Please include minAmount=(
ex. 0) and maxAmount=(ex. 100000).

{{BillsperCompany}} = Request that retrieves all bills from the desired company. NOTE: Please include the company name,
ex. Franco Rossi SPA.

{{BillsperIdClients}} = Request that retrieves all bills from the desired id_client. NOTE: Please include the id_client
number, ex. 1.

{{AllClientPage}} = Request that retrieves all Clients.

{{ClientsperIdClient}} = Request that retrieves all Clients from the desired id_client. NOTE: Please include the
id_client number, ex. 1.

{{ClientsperTurnHover}} = Request that retrieves all Clients within a specified TurnHover range. NOTE: Please include
minAmount=(ex. 0) and maxAmount=(ex. 100000).

{{ClientsperData}} = Request that returns all Clients filtered by insert date. NOTE: Include startDate=(YYYY-MM-DD)
&endDate=(YYYY-MM-DD).

{{ClientperDateLastContract}} = Request that returns all Clients filtered by last contract date. NOTE: Include
startDate=(YYYY-MM-DD)&endDate=(YYYY-MM-DD).

{{ClientsperCompany}} = Request that retrieves all Clients from the desired company. NOTE: Please include the company
name, ex. Franco Rossi SPA.

{{AllUserPage}} = Request that retrieves all User.

{{UsersperIdUsers}} = Request that retrieves all Users from the desired id_user. NOTE: Please include the id_user
number, ex. 1.

{{CurrentUser}} = Request that retrieves the Current User.

## Put Method

{{ChangeClientperId}} = Change all Clients from the desired id_client. NOTE: Please include the id_client number, ex. 1.

## Delete Method

{{DeleteClientperId}} = Delete all Clients from the desired id_client. NOTE: Please include the id_client number, ex. 1.

{{DeleteUserperid}} = Delete all Users from the desired id_user. NOTE: Please include the id_user number, ex. 1.

