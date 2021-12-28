# HyperMetro
Have you ever been worried about getting lost in the subway? Do you need to find a faster route to your destination? Write an application that will help you better navigate the complicated metro system.

## About
This project is the implementation of the project HyperMetro from JetBrains Academy. The program loads the metro map (the file with the scheme is specified when the program starts). After loading the user through the command menu can execute the following commands:

- add-head line station
- append line station
- remove line station
- connect line1 station1 line2 station2
- output line
- route line1 station1 line2 station2
- fastest-route line1 station1 line2 station2
- shortest-route line1 station1 line2 station2

## Used Technologies

The project is written in Java 17. For parsing JSON file with metro map used library Google Gson. Additionally I used Spring Core and Lombock libraries. 

## Tests

### Functional Test
The project includes one functional test from JetBrains Academy. This test is used to check the solution and accept the project. 

### Unit Test
In addition to the functional test, I wrote unit tests using Spock Framework 2. 
