## 1. Setzen einer externen Konfiguration

Über die System-Property `ninja.external.configuration` kann ein Pfad/URL zu einer externen Konfiguration angeben werden. Weitere Informationen: http://www.ninjaframework.org/documentation/configuration_and_modes.html

## 2. Konfigurationsparameter

### Allgemeine Parameter für Ninja

`application.name` 

Name der Anwendung: *String*

`application.cookie.prefix`

Prefix für Cookies, die von der Anwendung angelegt werden: *String*

`application.languages`

Definiert die verfügbaren Sprachen der Anwendung: *List[String]*

`application.session.expire_time_in_seconds`

Session-Auslaufdauer: *Integer*

`application.session.send_only_if_changed`

Sendet nur Session-Cookie, wenn sich der Content geändert hat: *Boolean*

`application.session.transferred_over_https_only`

Setzt httpOnly-Flag in Session-Cookie und erlaubt ihn so nur über HTTPS versenden: *Boolean*

`application.secret`

Anwendungs-Schlüssel für kryptografische Operationen: *String* 

### Datenbank-Verbindung

`ninja.mongodb.host`

Host der mongodb: *String*

`ninja.mongodb.port`

Port der mongodb: *Integer*

`ninja.mongodb.dbname`

Datenbankname der mongodb: *String*

`ninja.mongodb.user`

Benutzer der mongodb: *String*

`ninja.mongodb.pass`

Passwort der mongodb: *String*

`ninja.mongodb.authdb`

authDb der mongodb: *String*

`ninja.mongodb.useInMemory`

Aktiviert die In-Memory-Mongodb: *Boolean*

`ninja.morphia.package`

Package-Pfad für die Morphia Models: *String*

### hireMe spezifisch

`hireMe.showBuildInfo`

Zeigt build-Informationen im Frontend: *Boolean*

### Cronjob zum Löschen nicht verwendeter Resourcen (z.B. Profilbilder, Thumbnails...)

`hireme.resourcecleanup.delay`

Zeitintervall, für Ausführung des Cleanup-Jobs: Long  
Die Einheit des Zeitintervalls hängt von `hireme.resourcecleanup.timeunit` ab.


`hireme.resourcecleanup.initdelay`

Zeitintervall, für erstmalige Ausführung des Cleanup-Jobs nach Starten der Applikation: Long  
Die Einheit des Zeitintervalls hängt von `hireme.resourcecleanup.timeunit` ab.
  
  
`hireme.resourcecleanup.timeunit`

Zeiteinheit für die Angabe der Zeitintervalle: *String*  
Mögliche Werte: SECONDS, MINUTES, HOURS, DAYS


`hireme.resourcecleanup.expiretime`

Anzahl der Tage, die nicht verwendete Resource behalten werden: *Integer*  
Mindestwert 1 Tag.