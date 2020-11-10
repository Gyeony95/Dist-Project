import RPi.GPIO as GPIO  
import time  
import urllib2  
import json  
  
GPIO.setmode(GPIO.BCM)  
GPIO.setwarnings(False)  
  
#GPIO.setup(26, GPIO.IN)  
#GPIO.setup(3, GPIO.OUT)  
GPIO.setup(DO, GPIO.IN)
  
def sendNotification(token, channel, message):  
 data = {  
  "body" : message,  
  "message_type" : "text/plain"  
 }  
  
 req = urllib2.Request('http://api.pushetta.com/api/pushes/{0}/'.format(channel))  
                         
 req.add_header('Content-Type', 'application/json')  
 req.add_header('Authorization', 'Token {0}'.format(token))  
  
 response = urllib2.urlopen(req, json.dumps(data))  
  
while True:  
              
            i=GPIO.input(26)  
            if i==0:                 #When output from LPG sensor is LOW  
                print("No Gas Leakage Detected",i)  
                #GPIO.output(3, 0)  #Turn OFF LED  
                time.sleep(0.5)  
            elif i==1:               #When output from LPG sensor is HIGH  
                #print("Intruder detected",i)  
                print("LPG Detect",i)  
                sendNotification("7230ffbdd10f0db0a740a6b3865f9082105e58d7", "RaviIoT", "Ravi your home leaked out the LPG Gas be alert!)
                #GPIO.ocdutput(3, 1)  #Turn ON LED  
                time.sleep(0.5)    
