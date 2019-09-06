# Coding_challenge

The purpose of this repository is to parse a .csv file's contents, send valid results to an sqlite database, send invalid results to an error csv file, and record the numbers in a .log file.

To run this app, go to command line, and execute with "Coding_Challenge.java x" where 'x' is the csv file you want to parse

I approached the project by starting with what I thought was the most important step: parsing the csv file. From there, I addressed invalid entries, then database creation and connection, and lastly, writing to the log file. I made the assumption that all string inputs would be less than 50 characters, since it seemed that there wouldn't be any large text blocks based on what I saw in the given csv file.
