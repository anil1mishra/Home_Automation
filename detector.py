from firebase import firebase
import time as time
import RPi.GPIO as GPIO
from datetime import datetime
import threading


switch_name = int(0)
switch_type =int(2)
switch_state =int(1)
switch_pin = int(3)


GPIO.setmode(GPIO.BCM)

counters=[["counter1",0, [1,2], [27, 17]],
          ["counter2",0, [1,2], [7, 8]]]

switches =[["switch1", "off","normal", 20], ["switch2", "off", "normal", 26],
           ["switch3", "off","normal", 37], ["switch4", "off", "normal", 36],
           ["switch5", "off", "normal", 30] ]


GPIO.setup(counters[0][3][0],GPIO.IN)
GPIO.setup(counters[0][3][1],GPIO.IN)
GPIO.setup(counters[1][3][0],GPIO.IN)
GPIO.setup(counters[1][3][1],GPIO.IN)

GPIO.setup(switches[0][switch_pin],GPIO.OUT)
GPIO.setup(switches[1][switch_pin],GPIO.OUT)
GPIO.output(20,1)

GPIO.setup(26,GPIO.OUT)
GPIO.setup(20,GPIO.OUT)
GPIO.output(20,1)
GPIO.output(26,1)

def detect(pin,pin1):
     while(True):
          if(GPIO.input(pin)==1):
               GPIO.output(pin1,0)
               time.sleep(1)
          elif(GPIO.input(pin)==0):
               GPIO.output(pin1,1)
               
thread=threading.Thread(target=detect,args=(27,20))
thread1=threading.Thread(target=detect,args=(17,26))

thread1.start()
thread.start()

thread.join()
thread1.join()
