#
#   Hello World server in Python
#   Binds REP socket to tcp://*:5555
#   Expects b"Hello" from client, replies with b"World"
#

import time
import zmq


class Oferta:
    def __init__(self, titulo, codigo, sector, experiencia, edad, formacion_academica):
        titulo
        codigo
        sector
        experiencia
        edad
        formacion_academica

    def obtener_titulo(self):
        return self.titulo


context = zmq.Context()
socket = context.socket(zmq.REP)
socket.bind("tcp://*:5555")

while True:
    #  Wait for next request from client
    message = Oferta("", "", "", "", "", "")
    message = (socket.recv_pyobj())
    print("llego una nueva oferta laboral")
    print(message["titulo"])
    print(message["codigo"])
    print(message["sector"])
    print(message["experiencia"])
    print(message["edad"])
    print(message["formacion_academica"])

    #  Do some 'work'
    time.sleep(1)

    #  Send reply back to client
    socket.send(b"World")
