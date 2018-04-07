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


firebase = firebase.FirebaseApplication('https://projectlit-86257.firebaseio.com/', None)


def fir(sw,pin):
     while(True):
#          if(1 == firebase.get('/House1','/change')):
#               t=time.time()
               change= firebase.get('/House1/appliances',sw)
               print(sw,change)
               if(change=='off'):
                    GPIO.output(pin,1)
               elif(change=='on'):
                    GPIO.output(pin,0)
#               firebase.put('House1', 'change', 0)
     
     return


t=threading.Thread(target=fir,args=('switch1',26))
t1=threading.Thread(target=fir,args=('switch2',20))
t.start()
t1.start()
print("hello world")
time.sleep(1)


print("har")



def on_switch(pin):
    GPIO.output(pin,0)
    return

def off_switch(pin):
    GPIO.output(pin,1)
    return

def counting(cnt):
     while True:
          print(counters[cnt][1])
          if ((GPIO.input(counters[cnt][3][0]) == 1) and (GPIO.input(counters[cnt][3][1]) == 0)):
               i = 1
               while (i == 1):
                    if (GPIO.input(counters[cnt][3][1]) == 1):
                         counters[cnt][1]=counters[cnt][1]+1
                         print(counters[cnt][1])
                         #on_switch(20)
                         firebase.put('House1/appliances', 'switch2', 'on')
                         firebase.put('House1/appliances', 'switch1', 'on')
                         i = 0
                         #time.sleep(0.4)
          if ((GPIO.input(counters[cnt][3][1]) == 1) and (GPIO.input(counters[cnt][3][0]) == 0)):
               i = 1
               while (i == 1):
                   if (GPIO.input(counters[cnt][3][0]) == 1):
                       if(counters[cnt][1]>0):
                           counters[cnt][1] = counters[cnt][1] - 1
                           print(counters[cnt][1])
                           i = 0
                           time.sleep(0.4)
                       if(counters[cnt][1]==0):
                           print(counters[cnt][1])
                           #off_switch(20)
                           firebase.put('House1/appliances', 'switch2', 'off')
                           firebase.put('House1/appliances', 'switch1', 'off')
                           #off_switch(switches[counters[cnt][2][0]][switch_pin])
                           i = 0
                           #time.sleep(0.4)
                            
     return


def fir(sw,dev):
     t0= datetime.now()
     counter=0
     while(True):
               change= firebase.get('/House1/appliances', sw)
               if(change=='on'):
                       if(counter==0):
                         print("switch  on")
                         t0= datetime.now()
                         counter=1
                    


               elif(change=='off'):
                        if(counter==1):
                         print("switch off")
                         t1= datetime.now()
                         print(t1)
                         t2=t1-t0
                         print(t2.total_seconds())
                         t1= firebase.get('House1/unit', dev)
                         t1=t2.total_seconds()+t1
                         t1=t1/3600
                         t1=5*t1
                         t1=t1/1000
                         results = firebase.patch('https://projectlit-86257.firebaseio.com/'+'House1/unit/', {dev: t1})
                         print(results)
                         counter=0
     
     return


ta=threading.Thread(target=fir,args=('switch1','device1'))
ta1=threading.Thread(target=fir,args=('switch2','device2'))
ta2=threading.Thread(target=fir,args=('switch3','device3'))
ta3=threading.Thread(target=fir,args=('switch4','device4'))
ta4=threading.Thread(target=fir,args=('switch5','device5'))

xx=threading.Thread(target=counting,args=(0,))
#yy=threading.Thread(target=counting,args=(1,))

ta.start()
ta1.start()
ta2.start()
ta3.start()
ta4.start()
#yy.start()
xx.start()
#while(True):
#     time.sleep(1)
ta.join()
ta1.join()
ta2.join()
ta3.join()
ta4.join()


print("ashray")


t.join()
t1.join()
xx.join()
#yy.join()
