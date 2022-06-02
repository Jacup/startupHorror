# startupHorror  

## Table of Contents

* [General Info](#general-information)
* [How to play?](#how-to-play)
* [Features](#features--roadmap)
* [Get Started](#get-started)
* [~~Screenshots~~](#screenshots)
* [Contact](#contact)


## General Information

Have you ever wondered how it is to be an owner of IT startup? 
Now, you have an opportunity to experience this wonderful journey in **startupHorror**, 
turn-based simulator, where you play the role of an IT startup owner.
Hire employees, create projects and struggle with the tax office!


## How to play? 

* [Projects](#projects)
* [Clients](#clients)
* [Employees](#employees)
* [Contractors](#contact)
* [Gameplay](#gameplay)
* [How to win?](#how-to-win)
* [When do you lose?](#when-do-you-lose)


### Projects
At the beginning of the game, you have the opportunity to acquire one of three projects to complete.  
Projects are divided into easy, medium and hard. If you don't have employees you can only do easy and medium projects.
Each project is described by a set of technologies to work on:
* frontend
* backend
* database
* mobile
* WordPress
* Prestashop


### Clients
Clients fall into several groups:
* relaxed - 30% chance of delaying the payment by a week, but also a 20% chance of avoiding a penalty for delay if it is not more than a week, handing over a non-functioning project does not cause additional problems
* demanding - 0% chance of delaying payments, 0% chance of avoiding penalties, handing in a non-functioning project means 50% chance of losing the contract without reimbursement
* mthrfckr - 30% chance of delaying the payment by a week, 5% chance of delaying the payment by a month, 0% chance of avoiding a penalty, 100% chance of losing the contract after handing in the non-functioning project, 1% chance of never receiving a payment


### Employees
You can hire three types of workers:
* developers with knowledge of specific technologies who complete projects with a certain degree of accuracy and punctuality
* salesman, who looks for new clients - every 5 working days adds a new project to the pool of projects available for execution
* tester, having at least one tester for three programmers guarantees that you will not give the client a non-functioning project

As in life, hiring an employee costs money, keeping an employee costs money, firing an employee costs money. In addition to paying the employee, you have to pay for the cost of the job, health insurance, tax, etc.  
There is some small chance that one of them will get sick and not come to work.


### Contractors
You have three college friends to whom you can subcontract work:
* the best student - the most expensive, but does it on time and without mistakes
* average student - does it on time, but there is a 10% risk that you will have to correct it after him
* the guy who knows everything best - the cheapest, 20% chance that will not come to work and there is a 20% risk that you will have to correct it after him.


### Gameplay
You start with limited amount of money.  
Each day is one turn. If it's a weekend you can work independently, if it's a workday you can work independently, your subcontractors and employees work as well.

Each day you can do one of the following:
* sign a contract for one of the available projects
* spend a day looking for clients (every 5 days is one new available project)
* spend a day on programming
* spend one day testing (you can test your own code, subcontractors' code and employees' code)
* hand over the finished project to the client
* hire a new employee
* fire an employee
* spend a day for settlements in tax office (if you don't dedicate 2 days in a month, tax office will come with such an inspection that you will close the company with debts)


### How to win? 
Get paid for 3 projects, but with specific conditions: 
* you cannot work on any of these 3 projects,
* all 3 projects have to be completed and returned to client without any delay,
* all 3 projects have to be fully working (no bugs allowed!),
* at least one of completed projects must be found by salesman,
* at the end, you need to have more money than on the start.


### When do you lose? 
* employee or subcontractor don't get paid at the end of the month
* you don't have money to pay taxes at the end of the month
* you don't spend 2 days for settlements in tax office in a month


## Features / Roadmap

#### Randomized data:
- [x] Generate amount of cash
- [x] Generate set of available employees with randomized attributes
- [x] Generate set of available projects with randomized attributes
- [x] Generate set of clients with randomized attributes

#### Employees, subcontractors:
- [x] Developers 
- [x] Sales
- [x] Testers
- [ ] Subcontractors
- [ ] Employee costs (tax, insurance etc.)

#### Projects: 
- [x] Sign new project
- [x] Programming 
- [x] Testing
- [x] Returning/hand over

#### Gameplay:
- [x] Monthly taxes
- [x] Tax office support
- [x] Win/lose conditions
- [x] Daily/monthly operations

#### Owner's actions: 
- [x] Sign new project
- [x] Search new client/project
- [x] Go programming
- [x] Go testing
- [x] Return finished project
- [x] Hire employee
- [x] Fire employee
- [x] Do not take action 


#### Environment:
- [x] Add readme
- [ ] Automate build process:
    - [ ] Build on PR push
    - [ ] Automatic releases


#### Plans for the future: 
- [ ] Support for projects that have multiple stages - more often you have to spend time turning in work, but more often you get paid. 
- [ ] Two-player capability - players compete for the same pool of available projects and the same employees to hire. Even worse, sales and hr activities increase the pool of available projects and employees for both players regardless of who is doing the spending. 
- [ ] Possibility to play for any number of players - at the beginning of the game we give the number of players and the names of each of them.


## Get Started


### Running a game


Download executable .jar file and run following command: 

```cmd
java -jar startupHorror.jar
```


## Screenshots

> TBD


## Contact

Created by [Jakub Gramburg (@Jacup)](https://github.com/Jacup) - feel free to contact me!
