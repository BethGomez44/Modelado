#! /bin/bash

if [ -f build/seguridad/Desencriptar.class ] &&  [ -f build/seguridad/Encriptar.class ]  && [ -f build/seguridad/PolinomioSeguro.class ];
then
    echo "Iniciando pruebas.."
    if [ -f build/Tests.class ];
    then
        java -cp .:./build:/build/seguridad Tests
    else
        javac -d build -cp .:./build ./src/Tests.java
        java -cp .:./build:/build/seguridad Tests
    fi
    rm -r testFiles/muestrasEncriptado/*
    rmdir testFiles/muestrasEncriptado/
else
    echo "Parece que el programa no se ha instalado o faltan algunos archivos criticos"
    read -p "Desea completar la instalacion? [s,n]: " response
    if [ "$response" = "s" ] || [ "$response" = "S" ] ;
    then
        echo "Se iniciara la instalacion..."
        if [ -f instalarPanda.sh ] && [ -x instalarPanda.sh ] ;
        then
            ./instalarPanda.sh
            ./run_Test.sh
        else
            echo "No se puede completar la instalacion, si el problema persiste descargue de nuevo el proyecto"
        fi
    else
        echo "hasta pronto"
    fi
fi