from glob import glob
import os
import numpy as np
import tsaug
from tsaug.visualization import plot
from tsaug import TimeWarp, Drift, Reverse

def dataAug():
    # Find the data
    dir = "C:\\Users\josha\Desktop\BMEG\BMEG Year 3\MINT\data\\"
    path_list = glob(dir)

    for path in path_list:
        data_path = glob(path + "raw*.txt")
        label_path = glob(path + "log*.txt")

        for data, label in zip(data_path, label_path):
            data_name = data.split("\\")
            log_name = label.split("\\")
            augmentation(data, dir, data_name[8], log_name[8])


def augmentation(data, dir, data_name, log_name):
    data_name = data_name.split(".")[0]
    log_name = log_name.split(".")[0]

    # Define augmenter
    my_augmenter = (
            TimeWarp()
            + Drift(max_drift=(0.1, 0.3)) @ 0.8
            + Reverse() @ 0.5
            + tsaug.AddNoise(scale=0.008)
    )

    # Read data, and split channels into time series plots
    num_lines = sum(1 for line in open(data)) - 7
    X = np.empty([num_lines])
    Y = np.empty([8, num_lines])
    timestamp = []
    ind = 0
    header = ""

    with open(data) as file:
        lines_read = 0
        for line in file:
            if lines_read < 7:
                if lines_read < 6:
                    header = header + line
                lines_read += 1
                continue
            data = line.split(", ")
            X[ind] = data[0]
            timestamp.append(data[12])
            for i in range(7):
                Y[i, ind] = data[i + 1]
            ind += 1

    file.close()

    # Augment data
    Y_aug1 = np.empty([8, num_lines])
    Y_aug2 = np.empty([8, num_lines])
    Y_aug3 = np.empty([8, num_lines])

    for i in range(8):
        Y_aug1[i,] = my_augmenter.augment(Y[i,])
        Y_aug2[i,] = my_augmenter.augment(Y[i,])
        Y_aug3[i,] = my_augmenter.augment(Y[i,])


    # write to new file
    newFile = open(dir + data_name + "Aug1.txt", 'w+')

    newFile.write(header)

    for i in range(num_lines):
        newFile.write(str(X[i]))
        for j in range(8):
            y = "{:.2f}".format(Y_aug1[j, i])
            newFile.write(", " + y)
        for k in range(3):
            newFile.write(", 0.00")
        newFile.write(timestamp[i])
        newFile.write("\n")
    newFile.close()

    newFile = open(dir + data_name + "Aug2.txt", 'w+')

    newFile.write(header)

    for i in range(num_lines):
        newFile.write(str(X[i]))
        for j in range(8):
            y = "{:.2f}".format(Y_aug1[j, i])
            newFile.write(", " + y)
        for k in range(3):
            newFile.write(", 0.00")
        newFile.write(timestamp[i])
        newFile.write("\n")
    newFile.close()

    newFile = open(dir + data_name + "Aug3.txt", 'w+')

    newFile.write(header)

    for i in range(num_lines):
        newFile.write(str(X[i]))
        for j in range(8):
            y = "{:.2f}".format(Y_aug1[j, i])
            newFile.write(", " + y)
        for k in range(3):
            newFile.write(", 0.00")
        newFile.write(timestamp[i])
        newFile.write("\n")
    newFile.close()

    # Copy all the log files
    suffix = ["Aug1.txt", "Aug2.txt", "Aug3.txt"]

    for suf in suffix:
        open(dir + log_name + suf, "w").writelines([l for l in open(dir + log_name + ".txt").readlines()])


if __name__ == "__main__":
    dataAug()