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


def crear_oferta():
    oferta.update({"titulo": input("ingrese el titulo de la oferta: ")})
    oferta.update({"codigo": input("ingrese el codigo de la oferta: ")})
    oferta.update({"sector": input("ingrese el sector de la oferta: ")})
    oferta.update({"experiencia": input(
        "ingrese la experiencia del postulante: ")})
    oferta.update({"edad": input("ingrese la edad del postulante: ")})
    oferta.update({"formacion_academica": input(
        "ingrese la formacion del postulante: ")})
    socket.send_pyobj(oferta)
    respuesta = socket.recv_string()
    print(respuesta)


def impresion_menu():
    print("Menu")
    print("1). Ingresar como Empleador")
    print("2). Ingresar como Aspirante")
    print("3). Salir")
    i = int(input())
    print(i)
    if i == 3:
        return -1
    return i


def impresion_menu_empleador():
    print("Menu Empleador")
    print("1). Crear oferta de empleo")
    print("2). Salir")
    i = int(input())
    if i == 2:
        return -1
    return i


context = zmq.Context()
#  Socket to talk to server
print("--> Iniciando el sistema")
socket = context.socket(zmq.REQ)
socket.connect("tcp://25.90.3.122:5555")
print("--> conexion con el servidor establecida correctamente")
i = 0
j = 0
while i != -1:
    i = impresion_menu()
    if i == 1:
        while j != -1:
            j = impresion_menu_empleador()
            if j == 1:
                crear_oferta()
            else:
                exit()

#  Do 10 requests, waiting each time for a response
# for request in range(10):
# print("Sending request %s …" % request)
# socket.send(b"Hello")

# Get the reply.
# message = socket.recv()
# print("Received reply %s [ %s ]" % (request, message))
