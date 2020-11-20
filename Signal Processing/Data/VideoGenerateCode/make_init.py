from moviepy101.moviepy.editor import VideoFileClip, concatenate_videoclips

blank_white = VideoFileClip('./blankwhite.mp4')
blink = VideoFileClip('./blink.mp4')

blank_white = blank_white.subclip(t_start=0, t_end=5)
blank_white_short = blank_white.subclip(t_start=0, t_end=1)
blink = blink.subclip(t_start=0, t_end=1)

init_clip = concatenate_videoclips([
    blank_white,
    blink,
    blank_white_short,
    blink,
    blank_white_short,
    blink,
    blank_white_short,
    blink,
    blank_white_short,
    blink,
    blank_white_short
])

init_clip.write_videofile('./init_clip.mp4')