import numpy as np
from scipy.signal import butter, lfilter
import glob
import os
import re
from datetime import datetime


#sampling rate
fs = 200

#number of channels
chns = 8

#low and high pass frequencies
lowcut = 8
highcut = 30

#first row of txt file with EEG data
eeg_row = 1
#column in txt file with channel 1 data
eeg_column = 1
#column in txt file with timestamp data (input 0 if there is no timestamp data)
time_column = 0
#delimiter of txt file (e.g. "," for MINT data, " " for data Hamza found)
delim = " "

#define the filter
def butter_bandpass(lowcut, highcut, fs, order=5):
    nyq = 0.5 * fs
    low = lowcut / nyq
    high = highcut / nyq
    b, a = butter(order, [low, high], btype='band')
    return b, a


def butter_bandpass_filter(data, lowcut, highcut, fs, order=5):
    b, a = butter_bandpass(lowcut, highcut, fs, order=order)
    y = lfilter(b, a, data)
    return y


#Meat and Potatoes
path = 'C:\\Users\Riley\PycharmProjects\EEG_Bandpass\Data'
for filename in glob.glob(os.path.join(path, '*.txt')):
   with open(os.path.join(os.getcwd(), filename), 'r') as f:
       lines = f.readlines()
       filtered = []
       raw = []

       #Read the file
       for x in range(eeg_row - 1, len(lines)):
           filtered.append(lines[x].split(delim))

       #Only need timing info if you want to make plots
       # if time_column == 0:
       #     t = np.arange(0, len(filtered))
       # else:
       #     s = datetime.strptime(filtered[0][time_column - 1], " %H:%M:%S.%f")
       #     start = int(s.day)*86400 + int(s.hour)*3600 + int(s.minute)*60 + int(s.second) + int(s.microsecond)/1000000
       #     e = datetime.strptime(filtered[len(filtered) - 1][time_column - 1], " %H:%M:%S.%f")
       #     end = int(e.day)*86400 + int(e.hour)*3600 + int(e.minute)*60 + int(e.second) + int(e.microsecond)/1000000
       #     t = np.linspace(0, end - start, len(filtered))

       #Construct an array (called 'raw') that contains only the EEG data, with each channel as a row
       for y in range(eeg_column - 1, eeg_column - 1 + chns):
           column = []
           for x in range(0, len(filtered)):
               column.append(float(filtered[x][y]))
           raw.append(column)

       # filter each row (EEG channel) of 'raw' array and store in 'filtered' array
       for i in range(0, chns):
           interim = butter_bandpass_filter(raw[i], lowcut, highcut, fs, order=1)
           print(i)
           for j in range(0, len(filtered)):
               filtered[j][eeg_column - 1 + i] = str(int(100*interim[j])/100)
           print(i)

       #name the output file
       if 'raw' in filename:
           output_name = re.sub('raw', 'filtered', filename)
       else:
           output_name = re.sub('.txt', '_filtered.txt', filename)

       # write the output file
       with open(output_name, 'w') as g:

           # the following 3 lines are only for MINT data
           # g.write('%OpenBCI Filtered EEG Data\n')
           # for j in range(1, eeg_row - 1):
           #     g.write(lines[j])

           for i in range(0, len(filtered)):
               g.write(delim.join(filtered[i]))
               g.write('\n')
           g.close()
       f.close()

