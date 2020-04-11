#saves the data in the format of each channel separated by a comma per line.
#The data is saved in 5 second chunks and every two chunks have a third overlapping them. 
#File name is ite#.csv where # increases starting at 1 as the files increase. 
#Files saved in directory in downloads with date as name.

import os
from datetime import datetime
import time
from pylsl import StreamInlet, resolve_stream

now = datetime.now()  # get current date and time
date = now.strftime("%d-%m-%Y")  # get date as string
path = "C:/Users/hamza/Downloads/" + date  # create directory in downloads with date as name
cnt=1
# first resolve an EEG stream on the lab network
streams = resolve_stream('type', 'EEG')

# create a new inlet to read from the stream
inlet = StreamInlet(streams[0])

while 1:
    t_end = time.time() + 5
    data=[]

    try:
        os.mkdir(path)  # try to make the path
    except OSError:
        print("")  # error message
    else:
        print("")  # success message

    while time.time() < t_end:
        sample, timestamp = inlet.pull_sample()
        readd = str(sample)[1:-1]
        data.append(readd)

    def create_file():  # create new csv file with time as name
        with open("ite" + str(cnt) + ".csv", "w") as file:
            file.write("")
    fileName = "ite" + str(cnt) + ".csv"  # get file name of csv file
    create_file()  # create txt file
    filepath = os.path.join(path, fileName)  # add txt file to directory that was created
    file1 = open(filepath, "w")  # open csv file

    with open(filepath, 'w') as f:
        for item in data:
            f.write("%s\n" % item)
        lst1 = data[:]

    ##################################################

    t_end = time.time() + 5
    data=[]

    while time.time() < t_end:
        sample, timestamp = inlet.pull_sample()
        readd = str(sample)[1:-1]
        data.append(readd)

    def create_file():  # create new csv file with time as name
        with open("ite" + str(cnt+2) + ".csv", "w") as file:
            file.write("")
    fileName = "ite" + str(cnt+2) + ".csv"  # get file name of csv file
    create_file()  # create txt file
    filepath = os.path.join(path, fileName)  # add csv file to directory that was created
    file1 = open(filepath, "w")  # open csv file

    with open(filepath, 'w') as f:
        for item in data:
            f.write("%s\n" % item)
        lst3 = data[:]

    ###################################################

    lst2 = lst1[int((len(lst1)/2)):len(lst1)] + lst3[0:int((len(lst3)/2))]

    def create_file():  # create new csv file with time as name
        with open("ite" + str(cnt+1) + ".csv", "w") as file:
            file.write("")
    fileName = "ite" + str(cnt+1) + ".csv"  # get file name of csv file
    create_file()  # create txt file
    filepath = os.path.join(path, fileName)  # add csv file to directory that was created
    file1 = open(filepath, "w")  # open csv file

    with open(filepath, 'w') as f:
        for item in lst2:
            f.write("%s\n" % item)
        lst3 = data[:]

    cnt = cnt + 3
