from time import strftime
import tkinter as tk
import tkinter.font as tkFont

"""
Python app for Radio PC Pi touchscreen app.
"""

# 800x480 pixel main window, unadorned with window controls
window = tk.Tk()
window.geometry('800x480')
window.overrideredirect(1)

frmClock = tk.Frame(master=window)
frmClock.pack(side=tk.TOP)

clockFont = tkFont.Font(family='FLIPclockwhite', size=200)
lblClock = tk.Label(master=frmClock, text='00:00', font=clockFont)
lblClock.pack()

def time():
    timeString = strftime('%H:%M')
    lblClock.config(text=timeString)
    lblClock.after(1000, time)

frmButtons = tk.Frame(master=window, relief=tk.RAISED, borderwidth=5)
frmButtons.pack(side=tk.BOTTOM)

btnClock = tk.Button(master=frmButtons, text='Clock', width=15)
btnNews = tk.Button(master=frmButtons, text='News', width=15)
btnWeather = tk.Button(master=frmButtons, text='Weather', width=15)
btnSystem = tk.Button(master=frmButtons, text='System', width=15)

btnClock.pack(fill=tk.X, side=tk.LEFT)
btnNews.pack(fill=tk.X, side=tk.LEFT)
btnWeather.pack(fill=tk.X, side=tk.LEFT)
btnSystem.pack(fill=tk.X, side=tk.LEFT)

time()

window.mainloop()