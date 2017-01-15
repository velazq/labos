#APLAB

##Synopsis
Proyecto final de la asignatura Ética, Legislación y Profesión de la Facultad de Informática - UCM.  
APLAB permite conocer la disponibilidad de los laboratorios de FDI realizando filtrados por dia, hora, asignatura y software disponible.  
Para ello se recolecta datos mediante scraping de dos páginas webs de FDI 
[estado](https://web.fdi.ucm.es/labs/estado_lab.asp) y [horarios](https://web.fdi.ucm.es/Docencia/Horarios.aspx?AulaLab_Cod=%s&fdicurso=%s)
de los laboratorios.

##Code Example



*Bucle For principal de la clase Scraper*

``` java
Inicializa el array de laboratorios, para almacenar la informacíon obtenida se usan archivos JSON

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

* Para el diseño de la aplicación se ha utilizado un Bootstrap Theme [Grayscale](https://blackrockdigital.github.io/startbootstrap-grayscale/).
* Herramientas de programación: [Gradle](https://gradle.org/) en entorno [Eclipse](https://eclipse.org/).


##Motivation

APLAB es un proyecto necesario para FDI. Actualmente la información del estado de los laboratorios se encuentra muy dispersa y es muy dificil para los usuarios acceder a ella.  
Con APLAB los usuarios tienen una gran facilidad para consultar el estado de los laboratorios simplemente seleccionando el laboratorio, dia, hora o software disponible.

##Installation

Proyecto Java haciendo uso de la herramienta Gradle.

**Instalación en Eclipse.**
* Descargar respositorio.
* File -> Import -> Gradle Project
* En src/main/java/Main.java -> Click Drch -> Rus As Java Aplication

Abrir la aplicación
* Ir a un navegador y consultar "localhost:4567"

##Contributors

* Alberto Velazquez Alonso
* Jonathan Sánchez Paredes
* Kurosh Dabbagh Escalante
* Lorena Jiménez Corta
* Pablo Capa

##License

A short snippet describing the license (MIT, Apache, etc.)
