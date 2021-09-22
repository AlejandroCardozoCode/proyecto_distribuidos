#
#   Hello World client in Python
#   Connects REQ socket to tcp://localhost:5555
#   Sends "Hello" to server, expects "World" back
#

import zmq


oferta = {
    "titulo": "",
    "codigo": "",
    "sector": "",
    "experiencia": "",
    "edad": "",
    "formacion_academica": ""
}

context = zmq.Context()

#  Socket to talk to server
print("Connecting to hello world server…")
socket = context.socket(zmq.REQ)
socket.connect("tcp://25.90.3.122:5555")

oferta.update({"titulo": input("ingrese el titulo de la oferta: ")})
oferta.update({"codigo": input("ingrese el codigo de la oferta: ")})
oferta.update({"sector": input("ingrese el sector de la oferta: ")})
oferta.update({"experiencia": input(
    "ingrese la experiencia del postulante: ")})
oferta.update({"edad": input("ingrese la edad del postulante: ")})
oferta.update({"formacion_academica": input(
    "ingrese la formacion del postulante: ")})
socket.send_pyobj(oferta)
#  Do 10 requests, waiting each time for a response
# for request in range(10):
#print("Sending request %s …" % request)
# socket.send(b"Hello")

# Get the reply.
#message = socket.recv()
#print("Received reply %s [ %s ]" % (request, message))
