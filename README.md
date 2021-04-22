# Portal de gestión

## → Proyecto en Dekra ← 

Usaré este readme a modo de bloc de notas para apuntar las dudas o cuestiones y irlas pasando de ordenador a ordenador 
sin problemas. Tambien llevare un diario sobre las cosas que realizo. A partir del 20/04/2020 añadire horas de entrada 
al diario para hacer más preciso y estimado las horas a las que encuentro los fallos y cuanto tardo en la solución de 
los mismos.

`Cuestiones a resolver :`

¿Esta bien como he utilizado los enum? <br> <--
¿Esta bien mi salida formateadad de datos para reformar los datos de alta? <br>
¿Puedo cambiar o mejorar la manera en la que utilizo el enum para optimizar el proceso? <br>

¿He aplicado bien el principio divide y venceras? <br>
Separar las funciones de la clase Servicio o dejarlo dentro

Preguntar sobre como vamos a introducir los datos a los tecnicos ( nueva seleccion de elija cual es su puesto en la empresa ) (¿?)

`Registro diario :`

<b>_→ 19/04/2021 :_ </b>Primer día en la empresa, por ahora casi toda la mañana se ha basado en pasarme los archivos desde un 
 terminal ( el mio personal ) al ordenador proporcionado por la empresa. No estaba ninguno de mis responsables asi que
 no he visto las instalaciones ni nada de eso. Por ahora el proyecto. <br>

 Sergio ha encontrado un fallo en mi codigo ( cambio de dirección) y lo hemos corregido entre los dos, el fallo venia de
 no haber adaptado la función a cambiar ( la funcion cambio era una copia de la funcion creación ). <br>

 <b> Ya funciona la enumeración </b>, ahora solo me falta saber como cambiar el formato en el que sale 
 ( por ahora sale como es definido en el Enum ). <br>
 <b> He cambiado eso </b> y ahora da el dato con el formato deseado. <br>
 <b> He encontrado la forma de eliminar y mejorar el sistema que utilizaba para la reforma de datos </b> tras leer algunas
 documentaciones online y con ayuda de Sergio he encontrado una forma más optima de realizar el formateo de datos. He
 ayudado tambien a Sergio con el uso del enum ya que los dos estabamos dandole vueltas al tema de como usarlo <br><br>
 Hoy el proyecto ha terminado en la version 2.1 ( aunque por error en un commit la he nombrado V3).

<b>_→ 20/04/2021 :_</b> Vamos a tener ahora una reunion con curro para exponer las dudas y trabajarlas ( 8:38 ) <br>
Hemos restrasado la reunión a las 10 por algunos imprevisto de ultima hora y hemos empezado a mirarnos excepciones ( 8:52 ) <br>
Voy a probrar el try catch en la creacion de fechas ( 9:20 ) <br>
Ya he implementado estas mejoras y he podido quitar el throw parse exception que tenia en todas las funciones que hacian referencia
a algun campo del tipo fecha en breves empezamos la reunión( 9:49 ) <br>
La reunion termino sobre las 11 y sergio y yo aprovechamos para ir al descanso. Hemos vuelto hace un rato y estoy trabajando
en mejorar como se borran y cambian los datos de los empleados de manera efectiva (12:27) <br>
Por ahora todos los prints estan adeacuados a como yo queria tenerlos para hoy y ya he averiguado y aplicado una forma más
eficiente y concisa de establecer el estado de un empleado. He empezado a intentar ahora mejorar el modificar ( 13:09 ) <br>
Casi ya no queda tiempo he dejado planteado algunos TODO para no perder el hilo de lo que tengo que hacer ( 14:47 ) <br>

<b>_→ 21/04/2021 :_</b> Esta mañana hemos tenido hasta las 9:30 una pequeña clase con curro que nos ha explicado Arraylist
, List, Maps y Set. Y ahora acabo de terminar de reformar la función de borrado ( 10:36 ) <br>
Casi he terminado la funcion de modificado, ya solo me falta guardar los nuevos valores en el arraylist (11:15) <br>
Ya he terminado los cambios a la funcion modificar y voy a empezar la papelera de los datos que creamos, la cual funciona 
con Maps( 13:27 ) <br>
Ya he terminado con la papelera de HashMaps, voy a hacer un poco de limpieza de codigo, comentar y reestructurar estos ultimos
30 min ( 14:30 )

<b>_→ 22/04/2021 :_</b> Estoy haciendo algunos cambios en la funcion de modificar y borrar para que sean mas efectivas y
aparte estoy parchenado errores que se producen en ciertas ocasiones ( 9:35 )<br>
Llevo toda la mañana con los ficheros y no estoy siendo capaz de conseguir que funcione como debe, seguiré intentandolo una 
hora mas y la ultima hora la dedicare a limpieza de código y comentar un poco todo ( 13:00 ) <br>
Ya he sido capaz de hacer que sepa separar las palabras, ahora necesito que pare en cada linea ( 13:12 ) <br>

![img.png](img.png)

Sigo teniendo problemas para que se pare en cada linea pero voy a dedicar el tiempo que me queda a hacer algunos TODO para
no perder el hilo y la semana que viene poder seguir ( 14:00 ) <br>
Voy a ir cerrando en breves ya que hoy he llegado un poco antes a la oficina, sobre las 8:20 ( 14:45 )