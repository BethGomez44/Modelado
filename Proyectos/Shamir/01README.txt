                       _       
                      | |      
 _ __   __ _ _ __   __| | __ _ 
| '_ \ / _` | '_ \ / _` |/ _` |
| |_) | (_| | | | | (_| | (_| |
| .__/ \__,_|_| |_|\__,_|\__,_|
| |     V 1.0             	         
|_      Software criptográfico


___¿Qué es panda?___

Panda es un Software criptográfico enfocado en el módelo del esquema de
Shamir. Este permite ocultar secretos o documentos que se pretende que 
conozcan un numero n de personas, y sólo sea posible su revelado si
se cuentan con k <= n personas reunidas o dispuestas a compartir su clave.

Permite tomar un archivo cualsea y generar un documento cifrado a partir 
de el. A la hora de cifrar se debe especificar n = numero de claves a 
generar y k = numero de claves mínimas para poder acceder al contenido
descifrado del documento.

No importa que claves sean, siempre y cuando se reunan al menos k de ellas
se podra descrifrar el documento.

Para el cifrado se ocupa el estandar simétrico "AES" con clave de 256 bits,
de manera que puede estar seguro de que le proporcionamos una gran protección
contra terceros malintecionados.

___¿Cómo empezar a usar panda?___

Panda funciona sobre java 11.0.2, asi que asegurate de contar con una 
instalacion de esa versión o superior. Si no cuentas con java, visita:

    https://www.java.com/es/download/

El Software esta enfocado a sistemas Linux/Unix ya que hace uso de GNU 
bash, version 3 o superior. Dentro de la carpeta contenedora encontraras
3 scripts de bash, dependiendo de tu sistema operativo es posible que antes
de comenzar a ejecutarlos, ahora solo procede a abrir una terminal en la 
carpeta del proyecto.

    $ ./instalarPanda.sh      [1]

Se encarga de realizar la instalacion de Panda, no es necesario que lo vuelvas
a ejecutar en posteriores usos de Panda, pues este solo se encarga de realizar
la compilacion del código fuente. 

    $ ./Panda.sh              [2]

Si es la primera vez que ejecutas este comando se te ofrecera la opción de 
instalar Panda, aunque esto tambien se puede realizar invocando al comando 
anterior [1], ./Panda.sh invoca al programa principal y es como usaras Panda
siempre que lo necesites

El  programa tiene dos mdos de operacion:

    $ ./Panda.sh <modo de operacion>

        -c  Modo encriptación:    Este modo es el que te permite 
                                  cifrar documentos

        -d  Modo desencriptación  Este modo es el que permite 
                                  desencriptar los documentos  

Dependiendo de los modos de operación el programa recibe diferentes argumentos:

    [Modo encriptación]

    $ ./Panda.sh -c <Ruta fuente> <Ruta salida> <n> <k>

            <Ruta fuente>:    Ruta donde se encuentra el archivo a encriptar

            <Ruta salida>:    Ruta donde se quiere depositar el archivo 
                              encriptado. Establecer la misma ruta de salida
                              y fuente sobreescribira el archivo original.

            <n>          :    Número de claves a generar (keys)  n >= 2
            <k>          :    Número de claves necesarias para desencriptar 
                              el documento, 2 <= k <= n.

Una vez completada una invocación valida al modo de encriptación, se te pedirá una
contraseña para finalizar la encriptación de tu documento. Se generará un criptograma 
en la ruta de salida especificada, asi como una carpeta que contiene las llaves 
solicitadas.

    [Modo desencriptación]

$ ./Panda.sh -d  <Ruta criptograma> <Ruta keys> 

            <Ruta criptograma>: Ruta donde se encuentra el archivo a desencriptar

            <Ruta keys>:        Ruta al directorio contenedor de las claves

Una vez completada una invocación valida al modo de desencriptación se generará 
un archivo en la misma ruta de origen del criptograma unicamente si la carpeta 
contenedora contenia suficientes llaves para completar el descifrado, en caso
contrario el programa solo finaliza la ejecución. Esto con el fin de proteger 
el documento de terceros malintecionados.


    $ ./run_Test.sh            [2]

Este ultimo script corre pruebas unitarias sobre los modulos esenciales del programa.
Si crees que algo no funciona con normalidad, puedes ejecutarlo para verificar. 

___ADVERTENCIAS___
Es altamente  recomendable no intentar modificar los cirptogramas ni las llaves 
generadas por el programa, pues manipularlas puede ocasionar la perdida total o
parcial de la información original.

Si usted desea personalizar el nombre de las llaves, es importante que se apegue
a la siguiente convencion:

        <nombre personalizado>key<Numero original de key>.key

De otra forma el programa ignorara la llave o la hara invalida, por ejemplo si su
llave tenia el nombre: "ejemplokey10.key" y desea entregarle la llave a Chucho Perez
podria especificar el nuevo nombre como "Chucho_Perezkey10.key"



___¿Quienes somos?___
Estudiantes de ciencias de la computación

    Del Moral Morales Francisco Emmanuel 420003162
    correo: fcoemmdmm@ciencias.unam.mx

    Gomez De La Torre Heidi Lizbeth      317266245
    correo: liz_gomez04@ciencias.unam.mx

                              _,add8ba,
                            ,d888888888b,
                           d8888888888888b                        _,ad8ba,_
                          d888888888888888)                     ,d888888888b,
                          I8888888888888888 _________          ,8888888888888b
                __________`Y88888888888888P"""""""""""baaa,__ ,888888888888888,
            ,adP"""""""""""9888888888P""^                 ^""Y8888888888888888I
         ,a8"^           ,d888P"888P^                           ^"Y8888888888P'
       ,a8^            ,d8888'                                     ^Y8888888P'
      a88'           ,d8888P'                                        I88P"^
    ,d88'           d88888P'                                          "b,
   ,d88'           d888888'                                            `b,
  ,d88'           d888888I                                              `b,
  d88I           ,8888888'            ___                                `b,
 ,888'           d8888888          ,d88888b,              ____            `b,
 d888           ,8888888I         d88888888b,           ,d8888b,           `b
,8888           I8888888I        d8888888888I          ,88888888b           8,
I8888           88888888b       d88888888888'          8888888888b          8I
d8886           888888888       Y888888888P'           Y8888888888,        ,8b
88888b          I88888888b      `Y8888888^             `Y888888888I        d88,
Y88888b         `888888888b,      `""""^                `Y8888888P'       d888I
`888888b         88888888888b,                           `Y8888P^        d88888
 Y888888b       ,8888888888888ba,_          _______        `""^        ,d888888
 I8888888b,    ,888888888888888888ba,_     d88888888b               ,ad8888888I
 `888888888b,  I8888888888888888888888b,    ^"Y888P"^      ____.,ad88888888888I
  88888888888b,`888888888888888888888888b,     ""      ad888888888888888888888'
  8888888888888698888888888888888888888888b_,ad88ba,_,d88888888888888888888888
  88888888888888888888888888888888888888888b,`"""^ d8888888888888888888888888I
  8888888888888888888888888888888888888888888baaad888888888888888888888888888'
  Y8888888888888888888888888888888888888888888888888888888888888888888888888P
  I888888888888888888888888888888888888888888888P^  ^Y8888888888888888888888'
  `Y88888888888888888P88888888888888888888888888'     ^88888888888888888888I
   `Y8888888888888888 `8888888888888888888888888       8888888888888888888P'
    `Y888888888888888  `888888888888888888888888,     ,888888888888888888P'
     `Y88888888888888b  `88888888888888888888888I     I888888888888888888'
       "Y8888888888888b  `8888888888888888888888I     I88888888888888888'
         "Y88888888888P   `888888888888888888888b     d8888888888888888'
            ^""""""""^     `Y88888888888888888888,    888888888888888P'
                             "8888888888888888888b,   Y888888888888P^
                              `Y888888888888888888b   `Y8888888P"^
                                "Y8888888888888888P     `""""^
                                  `"YY88888888888P'

