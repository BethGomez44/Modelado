#! /bin/bash

#estructuras
javac -d build -cp .:./build ./src/estructuras/Coleccionable.java   
javac -d build -cp .:./build ./src/estructuras/Listable.java
javac -d build -cp .:./build ./src/estructuras/Lista.java
javac -d build -cp .:./build ./src/estructuras/Pila.java
javac -d build -cp .:./build ./src/estructuras/Cola.java
javac -d build -cp .:./build ./src/estructuras/ArbolBinario.java
javac -d build -cp .:./build ./src/estructuras/ArbolBinarioBusqueda.java
javac -d build -cp .:./build ./src/estructuras/ArbolAVL.java
javac -d build -cp .:./build ./src/estructuras/Heap.java
javac -d build -cp .:./build ./src/estructuras/MaxHeap.java
javac -d build -cp .:./build ./src/estructuras/MinHeap.java

#funcionalidades 
javac -d build -cp .:./build ./src/funcionalidades/Escritor.java
#inventario
javac -d build -cp .:./build:./build/almacen:./build/estructuras ./src/almacen/GeneradorInventario.java
javac -d build -cp .:./build:./build/almacen:./build/estructuras ./src/almacen/Producto.java
javac -d build -cp .:./build:./build/almacen:./build/estructuras:./build/funcionalidades/.* ./src/almacen/Inventario.java
#funcionalidades
javac -d build -cp .:./build:./build/almacen:./build/estructuras -Xlint ./src/funcionalidades/Lector.java
javac -d build -cp .:./build ./src/funcionalidades/ControlEjecucion.java
#clientes
javac -d build -cp .:./build:./build/almacen/.*:./build/estructuras/.* ./src/cliente/Carrito.java
javac -d build -cp .:./build:./build/almacen/.*:./build/estructuras/.* ./src/cliente/Cliente.java

#supermercado
javac -d build -cp .:./build:./build/almacen/.*:./build/estructuras/.*:./build/cliente/.*:./build/funcionalidades/.* ./src/supermercado/Caja.java
javac -d build -cp .:./build:./build/almacen/.*:./build/estructuras/.*:./build/cliente/.* ./src/supermercado/Cajero.java
javac -d build -cp .:./build:./build/almacen/.*:./build/estructuras/.*:./build/cliente/.*:./build/funcionalidades/.* ./src/supermercado/Simulacion.java

#gnuplot
javac -d build -cp .:./build:./build/almacen/.*:./build/estructuras/.*:./build/cliente/.*:./build/funcionalidades/.* ./src/gnuplotProcess/Interprete.java

#menu

javac -d build -cp .:./build:./build/almacen:./build/funcionalidades/* ./src/menus/MenuInventario.java
javac -d build -cp .:./build:./build/almacen:./build/funcionalidades/* ./src/menus/MenuResetear.java
javac -d build -cp .:./build:./build/supermercado/.*:./build/almacen/.*:./build/funcionalidades/*:./build/gnuplotProcess/.* ./src/menus/MenuSimulacion.java

#MAIN

javac -d build -cp .:./build:./build/almacen/.*:./build/funcionalidades/.*:./build/menus/.* ./src/Main.java

