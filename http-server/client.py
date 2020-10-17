from socket import *
from select import *
import sys
from time import ctime

import RPi.GPIO as GPIO
import time


DO = 26

GPIO.setmode(GPIO.BCM)
GPIO.setup(DO, GPIO.IN)


HOST = '127.0.0.1'
PORT = 10000
BUFSIZE = 1024
ADDR = (HOST, PORT)

url = 'http://ec2-3-35-175-110.ap-northeast-2.compute.amazonaws.com:3000/dists'


try:
    while True:
        if GPIO.input(DO) < 0.01:
            data = {'mst': 'good'}
            response = web_request(method_name='POST', url=url, dict_data=data)
            print(response)
            if response['ok'] == True:
            	print(response['text'])
            else:
                print("fail1")

        else:
            data = {'mst': 'bad'}
            response = web_request(method_name='POST', url=url, dict_data=data)
            print(response)
            if response['ok'] == True:
            	print(response['text'])
            else:
                print("fail2")

        time.sleep(1)


except Exception as e:
    print('%s:%s' % ADDR)
    sys.exit()

print('connect is success')
