import requests
from bs4 import BeautifulSoup
import time
import csv

# Get the URL and the first set of full parameters (full set because we need to garner information)
targetURL = "https://dalonline.dal.ca/PROD/fyskeqiv.P_TransEquiv"
params = {'prov': 'ALLINST', 'inst': 'NSU002', 'subj': 'CSCI'}
HTTPRequest = requests.post(targetURL, data=params)

# Wait to lessen the chance of getting kicked off the server for too many requests while getting the raw HTTP request 
while HTTPRequest.status_code == 429:
    time.wait(30)
    HTTPRequest = requests.post(targetURL, data=params)

# Soupify the code to make it parseable using BeautifulSoup
HTMLcode = BeautifulSoup(HTTPRequest.content, "html.parser")

# Get all the raw institution codes and store them in a list for later use
institution_codes_raw = HTMLcode.find("select", attrs={"name": "inst"}).find_all("option")
institution_codes = []

# For every raw institution in the list, go through and grab its useable value without tags, put them in a 
# list, then pop the first one because it is always "Dummy" data and will mess with future endeavours
for institution in institution_codes_raw:
    value_tag = institution.attrs['value']
    institution_codes.append(value_tag)
institution_codes.pop(0)

# Creating the end variables now so they can be dynamically appended to
output = []
td = ""

# For every institution, iterate through each subject and dynamically build the final output (big picture)
for institution in institution_codes:

    # We only want the institution here because we need to construct a list of subjects and courses that we don't have yet,
    # The rest is the same as before: wait to not get temporarily banned, make a soup object to be parsed, and fill params
    params = {'prov': 'ALLINST', 'inst': institution}
    HTTPRequest = requests.post(targetURL, data=params)
    while HTTPRequest.status_code == 429:
        time.wait(30)
        HTTPRequest = requests.post(targetURL, data=params)
    HTMLcode = BeautifulSoup(HTTPRequest.content, "html.parser")

    # Because there are errors and plenty of non consistent data in the tables we are crawling, and because there are tables with
    # nothing in them, we need to catch errors that will occur or else the loop will stop cutting off part way
    try:

        # Create a new blank list for this subject's list of courses and put the data into that list
        course_codes_raw = HTMLcode.find("select", attrs={"name": "subj"}).find_all("option")
        course_codes = []

        # For every course we just garnered from the above find function, append the course to the list and pop
        # the first one again because it is alwyas "Dummy" and this will mess with things
        for course in course_codes_raw:
            value_tag = course.attrs['value']
            course_codes.append(value_tag)
        course_codes.pop(0)

        # Now that we have the course codes themselves in a list, for each of them, iterate through and use the previous
        # parameters to make a full request for the classes that are in those course codes
        for course in course_codes:
            params = {'prov': 'ALLINST', 'inst': institution, 'subj': course}
            HTTPRequest = requests.post(targetURL, data=params)
            while HTTPRequest.status_code == 429:
                time.wait(30)
                HTTPRequest = requests.post(targetURL, data=params)
            HTMLcode = BeautifulSoup(HTTPRequest.content, "html.parser")

            # Here we are only interested in any tag of type td that has the class dedefault and the same for schools with the
            # option tag which have the value of the current institution we are iteration over
            td = HTMLcode.find_all('td', attrs={'class': 'dedefault'})
            school = HTMLcode.find('option', attrs={'value': institution})
            i = 0

            # For ever cell in that td tag, extract the text, turn it into a string, strip it of all new line charcters,
            # special characters, and blank spaces before sending it to be appended. Each one of these td htmlcodes are
            # a row in our database so they must be printed in the same line.
            for cell in td:  
                cell = cell.get_text()
                cell = str(cell)
                cell = cell.strip('\n')
                cell = cell.replace('\n', '')

                # Because the records come grouped in 4 (or rather that is how we want them in our database), we need to 
                # find a way to seperate them. If the cell is not a new line character and i is less than 4, append it
                if (cell != '\n'):
                    if (i < 4):  # Records are in groups of 4 but we're given the whole table at once.
                        i = i + 1
                        output.append(cell)
                    
                    # If this is the 4th cell, tag on the school, and the suject (CHEM, BIOL, etc.) then reset i to 0
                    elif (i == 4):
                        output.append(cell)
                        output.append(school.get_text())
                        output.append(course)
                        i = 0
    # In case of error, print nothing here to the screen for user convenience
    except:
        print("Nothing here")

# The final out needs to be a 2D matrix but the only way I could make that happen without numpy is through list comprehension
# so that the csv reader can use writerows() to make life much easier 
final_output = [output[i:i+7] for i in range(0, len(output), 7)]

# Open a file called lab 6 and print to it row by row as output
with open('lab6.csv', "w", newline="") as csv_file:
    writer = csv.writer(csv_file)
    writer.writerows(final_output)
