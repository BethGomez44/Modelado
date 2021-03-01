#! /bin/bash

if [ -f build/seguridad/Desencriptar.class ] &&  [ -f build/seguridad/Encriptar.class ]  && [ -f build/seguridad/PolinomioSeguro.class ] && [ -f build/Main.class ];
then
    echo "Iniciando Panda..."
    java -cp .:./build:/build/seguridad Main $*  
else
    echo "Parece que el programa no se ha instalado correctamente o faltan algunos archivos criticos"
    read -p "Desea completar la instalacion? [s,n]: " response
    if [ "$response" = "s" ] || [ "$response" = "S" ] ;
    then
        echo "Se iniciara la instalacion..."
        if [ -f instalarPanda.sh ] && [ -x instalarPanda.sh ];
        then
            ./instalarPanda.sh
            echo "Panda esta listo para usarse!"
        else
            echo "No se puede completar la instalacion, si el problema persiste descargue de nuevo el proyecto"
        fi
    else
        echo "Hasta pronto"
    fi
fi
