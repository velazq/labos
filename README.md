#APLAB

[Versión en castellano](#castellano)

##Synopsis
Last assignment for the "Ética, Legislación y Profesión" course (Ethics, Legislation and Professional Practice) at the Faculty of Computer Sciences (FDI), Complutense University of Madrid.
APLAB lets you see the current availability of the Faculty laboratories, allowing you to filter by day, hour, subject and installed software.
To that end, it gathers data by scraping two existing pages,
[status](https://web.fdi.ucm.es/labs/estado_lab.asp) and [time table](https://web.fdi.ucm.es/Docencia/Horarios.aspx?AulaLab_Cod=%s&fdicurso=%s).

##Resources used

* For the design, we used HTML, CSS and JavaScript along with the Bootstrap Theme template [Grayscale](https://blackrockdigital.github.io/startbootstrap-grayscale/).
* Dev tools: [Gradle](https://gradle.org/), [Spark Framework](http://sparkjava.com/) and Java with [Eclipse](https://eclipse.org/).


##Motivation

APLAB is a much-needed project for the FDI. Currently, the status information for our labs is scattered over several pages and it is really difficult for students to access it.
With APLAB, users can easily check the status of every lab just by selecting the lab number, day, hour or installed software.

##Getting the code

The code is organized as a Gradle project for Eclipse.

**Open project with Eclipse**
* Clone repo
* File -> Import -> Gradle Project
* On src/main/java/Main.java -> Right click -> Run As Java Application

**View the app**
* Open a web browser, then visit "localhost:4567"

##Participants

* Alberto Velazquez Alonso - [velazq](https://github.com/velazq)
* Jonathan Sánchez Paredes - [JoniSanchez](https://github.com/JoniSanchez)
* Kurosh Dabbagh Escalante - [KuroshDa](https://github.com/KuroshDa)
* Lorena Jiménez Corta     - [Kudaes](https://github.com/Kudaes)
* Pablo Capa		   - [pabloCapa](https://github.com/pabloCapa)

##Code license

MIT License  
Copyright (c) 2016-2017 the APLAB developers  
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files.  
More information in "LICENSE.txt" file

##Media license

All images and any other media files are under the [Creative Commons CC-BY-NC-SA license](https://creativecommons.org/licenses/by-nc-sa/3.0/).

---

##<a name="castellano"></a>Sinopsis
Proyecto final de la asignatura Ética, Legislación y Profesión de la Facultad de Informática - UCM.  
APLAB permite conocer la disponibilidad de los laboratorios de FDI realizando filtrados por dia, hora, asignatura y software disponible.  
Para ello se recolecta datos mediante scraping de dos páginas webs de
[estado](https://web.fdi.ucm.es/labs/estado_lab.asp) y [horarios](https://web.fdi.ucm.es/Docencia/Horarios.aspx?AulaLab_Cod=%s&fdicurso=%s)
de los laboratorios.

##Ejemplo de código

Se hace uso de scraping para recolectar la información y de archivo JSON para almancenarla.


*Bucle For principal de la clase Scraper*

``` java
Inicializa el array de laboratorios y crear el archivo JSON

public static Lab[] laboratorios = new Lab[11];

public static void iniciar()
{    
	for(int i = 0;i < laboratorios.length;i ++){  
		laboratorios[i] = new Lab(i + 1);  
		Scraper.getLabsInfo(laboratorios[i]);  
		Scraper.getHoursInfo(laboratorios[i]);  
	}  
	createJson();  
}  
```

##Recursos utilizados

* Para el diseño de la aplicación se ha utilizado html, css y javascript sobre una plantilla de Bootstrap Theme [Grayscale](https://blackrockdigital.github.io/startbootstrap-grayscale/).
* Herramientas de programación: [Gradle](https://gradle.org/), [Spark Framework](http://sparkjava.com/) y Java en entorno [Eclipse](https://eclipse.org/).


##Motivación

APLAB es un proyecto necesario para FDI. Actualmente la información del estado de los laboratorios se encuentra muy dispersa y es muy dificil para los usuarios acceder a ella.  
Con APLAB los usuarios tienen una gran facilidad para consultar el estado de los laboratorios simplemente seleccionando el laboratorio, dia, hora o software disponible.

##Instalación

Proyecto Java haciendo uso de la herramienta Gradle.

**Instalación en Eclipse**
* Descargar respositorio.
* File -> Import -> Gradle Project
* En src/main/java/Main.java -> Click derecho -> Run As Java Application

**Abrir la aplicación**
* Ir a un navegador y consultar "localhost:4567"

##Participantes

* Alberto Velazquez Alonso - [velazq](https://github.com/velazq)
* Jonathan Sánchez Paredes - [JoniSanchez](https://github.com/JoniSanchez)
* Kurosh Dabbagh Escalante - [KuroshDa](https://github.com/KuroshDa)
* Lorena Jiménez Corta     - [Kudaes](https://github.com/Kudaes)
* Pablo Capa		   - [pabloCapa](https://github.com/pabloCapa)

##Licencia del código

MIT License  
Copyright (c) 2016-2017 the APLAB developers  
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files.  
More information in "LICENSE.txt" file

##Licencia de imágenes

Todas las imágenes y otros archivos están bajo la licencia [Creative Commons CC-BY-NC-SA](https://creativecommons.org/licenses/by-nc-sa/3.0/es/).
