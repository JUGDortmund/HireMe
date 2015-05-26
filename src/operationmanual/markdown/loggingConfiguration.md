## Überschreiben der Logging-Konfiguration

Über die System-Property `logback.configurationFile` kann ein Pfad/URL zu einer externen logback Konfiguration angeben werden. 

## Dev

* INFO -> System.out
 
## Jenkins

* DEBUG -> System.out

## Build

* DEBUG -> System.out
* INFO -> Syslog

## Prod

* INFO -> System.out
* WARN -> Syslog

