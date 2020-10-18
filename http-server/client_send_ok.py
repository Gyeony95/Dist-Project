from socket import *
from select import *
import sys
from time import ctime

import time

import requests
import json


def web_request(method_name, url, dict_data, is_urlencoded=True):
    method_name = method_name.upper()
    if method_name not in ('GET', 'POST'):
        raise Exception('method_name is GET or POST plz...')

    if method_name == 'GET':
        response = requests.get(url=url, params=dict_data)
    elif method_name == 'POST':
        if is_urlencoded is True:
            response = requests.post(url=url, data=dict_data,
                                     headers={'Content-Type': 'application/x-www-form-urlencoded'})
        else:
            response = requests.post(url=url, data=json.dumps(dict_data), headers={'Content-Type': 'application/json'})

    dict_meta = {'status_code': response.status_code, 'ok': response.ok, 'encoding': response.encoding,
                 'Content-Type': response.headers['Content-Type']}
    if 'json' in str(response.headers['Content-Type']):
        return {**dict_meta, **response.json()}
    else:
        return {**dict_meta, **{'text': response.text}}


url = 'http://ec2-3-35-175-110.ap-northeast-2.compute.amazonaws.com:3000/dists'

data = {'mst': 'good'}
response = web_request(method_name='POST', url=url, dict_data=data)

print(response)
if response['ok'] == True:
    print(response['mst'])
else:
    pass




