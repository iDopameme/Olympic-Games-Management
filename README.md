[![Generic badge](https://img.shields.io/badge/Version-0.5.9-<COLOR>.svg)](https://shields.io/) 
[![GitHub issues](https://img.shields.io/github/issues/iDopameme/Olympic-Games-Management)](https://github.com/iDopameme/Olympic-Games-Management/issues)

# Olympic Games Project
Tournament Management & Bracket Generator Program with an Olympic Games focus
Program can be adjusted for non-olympic uses

![Command Line Menu](https://imgur.com/V4hb58i.png)

## Table of Contents
* [Overview](#overview)
* [Technologies](#technologies)
* [Setup](#setup)

## Overview
Features: The user will be able to input a lot of data such as (but not limited to): 
•	Participants
•	Tournaments
• Teams
• Countries
• Player specific data (Nationality, age, participating sport)
•	Medal Leaderboard
•	Events/Sports (Timetables, who is participating, tournament name and ID, etc.)
•	Inserting/Removing/Changing who is participating (Name, Age, Country, etc.)
•	Score tracking of each game
•	Results overall => determines who gets the medal
•	Database Relational Mangement usage

This application will work as how an Olympic Games is organized. The general purpose of this program is to input specific data and have that data be used in methods that will compile them into one section. For example, 
the scores of the game will determine who will get the gold,silver, and 
bronze medal. The data will automatically compile things such as 
participants that are playing in the same event, the winners of each event, 
and the overall scores. In the end, it would display everything that was inputted. 

## Usage
Currently can only be run using java but there are future plans to extend this program to run on other platforms/languages.
Java is required to run & compile this program.

## Technologies
Project is designed with:
* Java ver. 15.0.1
* MySQL Connector/J ver. 8.0.21
* Amazon RDS

## Setup 

### Database setup
A MySQL database is required to use this program on your machine.
After creating a database schema you must change two lines of code in Connect.java
Line 12: String url must be changed to your hosting address or to localhost if you're only running it on your local machine.
Line 13: String path must to changed to your local path file name.
![Url and Path changes](https://imgur.com/OkoSR8p.png)

JDBC must also be properly setup on your IDE to use this database with java.

### SQL Queries
![Database Table UML](https://imgur.com/vubki3H.png)

Next, all the SQL schemas must be run based on the UML order shown in the image above.
All schema files can be found in the Connect folder

### CSV files
CSV Files are great way to add the required data for countries and participants needed for this program to function.
This repo already has prefilled csv files with every country in the world and random human players as well.
File paths must be changed in the mutator functions of:

Countries.java

Participants.java

Countries.java

based on where you have your files locally stored, for example:
![Database Table UML](https://imgur.com/lQAweCt.png)

