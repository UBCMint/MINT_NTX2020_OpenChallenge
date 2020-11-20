from datetime import datetime
from moviepy101.moviepy.editor import concatenate_videoclips, VideoFileClip
import numpy as np
import os
from os import startfile


# writes a file to the file path with the given text
def write_file(file_path, text):
    f = open(file_path, "a+")
    f.write(text)
    f.close()

def open_video_timestamp(vid_path, vid_name, trial_num):
    startfile(os.path.join(vid_path,'{}.mp4'.format(vid_name)))
    dt = datetime.now()
    write_file('{}/log_timestamp_{}.txt'.format(vid_path,vid_name,trial_num), 'The time video started for trial {}: {}\n'.format(trial_num, dt))
    

if __name__ == '__main__':
    # final_path = 'C:/Users/User/Documents/MyDocuments/Clubs/MINT/Projects/AutomationHome/VideoML/Videos_Han'
    vid_path = 'C:/Users/User/Documents/MyDocuments/Clubs/MINT/Projects/AutomationHome/VideoML/Videos_Han'
    vid_name = 'nov-17_c10_han'
    trial_num = '3'
    # gen_clip(vid_path, final_path, name)
    open_video_timestamp(vid_path, vid_name, trial_num)

