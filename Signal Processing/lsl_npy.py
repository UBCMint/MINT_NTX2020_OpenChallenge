#saves data in a constantly updating 5 second window in file called eegdata.npy

from pylsl import StreamInlet, resolve_stream
import time
import numpy as np

streams = resolve_stream('type', 'EEG') #initialize connection to lsl stream

data = []

inlet = StreamInlet(streams[0])

while 1:
    sample, timestamp = inlet.pull_sample() #grab data from lsl
    if sample:
        data.append(sample)
        if len(data) > 1250:
            #if data reaches a certain length (1250 lines), remove the oldest data point when adding a new one
            data.pop(0)
            a = np.array(data)
            np.save("eegdata", a)
