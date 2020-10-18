import requests

from socket import *
from select import *
import sys
from time import ctime

import RPi.GPIO as GPIO
import time


URL = 'http://ec2-3-35-175-110.ap-northeast-2.compute.amazonaws.com:3000/dists'

DO = 26

GPIO.setmode(GPIO.BCM)
GPIO.setup(DO, GPIO.IN)


clientSocket = socket(AF_INET, SOCK_STREAM)

try:
    while True:
        if GPIO.input(DO) < 0.01:
            data = {'mst': 'high'}
        else:
            data = {'mst': 'low'}
        res = requests.post(URL, data=data)
        print(res.status_code,res.text)
        time.sleep(1)


except Exception as e:
    print('Exception')
    sys.exit()

print('connect is success')







