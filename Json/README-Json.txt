Buenos días Alberto, espero que hayas dormido bien :*

Te he subido un boceto para el Json con los horarios. No tiene mucha miga pero te lo comento a continuación:

He pensado que lo mejor es indexar el Json por laboratorios,y dentro de estos indexar por días. Ahora bien, se me han ocurrido tres maneras
de plasmar los datos de horas ocupadas/libres:

1- Dividiendo por horas ocupadas y por horas libres, aceptando agrupaciones de horas y añadiendo la asignatura en el campo de horas ocupadas.

2- Creando una entrada para cada una de las horas del día y añadiendo un campo "ocupado" con valores "si" o "no". En el caso de que sea "si" en el campo
"asignatura" añades el nombre de la misma y sino pues null.

3-Creando una entrada o bien para las horas libres, o bien para las horas ocupadas, no para las dos. El resto de horas que no aparecerían en el Json
se presupone que su estado es el contrario al elegido(si aparecen las horas libres pues las que no aparezcan estan ocupadas, y viceversa). En este caso
yo creo que sería mejor poner las horas ocupadas, por lo de meter la asignatura y tal...

Ya nos cuentas si te parece alguna opción buena o si son todas una puta mierda, en cuyo caso es todo culpa de Joni y yo no he hecho nada. En cualquier caso
va a quedar un Json de unos cuantos cientos de líneas, rico rico jaja!

Para el tratamiento de las horas dentro de la aplicación yo he pensado que usemos la clase LocalTime 
(https://docs.oracle.com/javase/8/docs/api/java/time/LocalTime.html), que te permite tratar horas y minutos como int normales, pero usted dirá.


Un beso a toda la familia.



