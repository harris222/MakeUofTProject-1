import RPi.GPIO as GPIO
import time
from socket import *
GPIO.setmode(GPIO.BOARD)
GPIO.setup(11, GPIO.OUT)
def light_on():
    GPIO.output(11, GPIO.HIGH)


def light_off():
    GPIO.output(11, GPIO.LOW)

sock = socket(AF_INET, SOCK_STREAM)
server_address = ("100.65.59.103", 12345)
sock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
sock.bind(server_address)

sock.listen(1)


print ('Waiting for a connection')
connection, client_address = sock.accept()
#try: 
#    while True:
#        data = connection.recv(64)    
#        if data == 1 :
#            light_on()
#        else:
#            light_off()
#finally:
#    connection.close()
response = connection.recv(64)
print(response.decode().find("Hello"))

try:
    while True:
        response = connection.recv(64)
        print(response.decode())
        if response.decode().find("HIGH") != -1:
            light_on()
        else:
            light_off()
except KeyboardInterrupt:
    print("Interrupted!")
    light_off()

connection.close()
