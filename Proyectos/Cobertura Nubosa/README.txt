Cobertura nubosa

Autores: Del Moral Morales Francisco Emmanuel
         Gómez de la Torre Heidi Lizbeth

Descripcion: La entrada del programa debe ser el nombre de un archivo de imagen en formato jpeg de 4,368
        pixeles de ancho y 2,912 pixeles de alto (centro en la columna 2184 y el renglón 1456) de
        alrededor de 32MB de tamaño. La imagen del cielo es un círculo (de radio aproximado de 1324
        pixeles) contenido en el rectángulo de la imagen. El centro del círculo y el del rectángulo que lo
        contiene coinciden. 

        La salida puede ser el indice de cobertura nubosa  (CCI) unicamente o mediante banderas se puede solicitar
        el archivo postprocesado de segmentacion de la imagen en cielo y nubes.

Requisitos: Para poder usar el programa es necesario utilizar java 11.0.2 o superior


Invocacion del programa: para utilizar el programa puede usar la siguiente guia:

1)  Compilación :
    $ javac Cobertura.java

2)  Ejecución:
    $ java Cobertura <nombre archivo fuente> <s>

        <nombre archivo fuente> Cadena sin espacios o entre comillas que contiene la ruta relativa 
                                 al archivo de imagen a procesar.

        <s>                     Agregar el caracter 's' o 'S' a la invoacion del programa generara un 
                                archivo de la misma extension, el cual muestra como se segmento el archivo
                                entre nubes y cielo.


Testing: Dentro de la carpeta "test" se cuenta con una clase que genera imagenes de prueba, para hacer uso de ella:
1)  Compilación :
    $ javac TestCasesGenerator.java

2)  Ejecución:
    $ java TestCasesGenerator <nombre archivo salida> 

        <nombre archivo salida> ruta donde se quiere guardar la imagen de prueba SIN extension
                                se generara una imagen .png con una entrada de prueba en la ruta
                                especificada

    La clase proporciona un archivo de prueba ademas de agregar en formato de salida estandar la cantidad de pixeles
    clasificados como nube, el area total de interes y el CCI aproximado.


Reconocemos y agradecemos a G Roy et al: "Sky analysis from CCD images: cloud cover". 2001 por proporcionarnos el criterio
de clasificacion para las nubes en una imagen de formato ARGB.


