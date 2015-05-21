Konfigurationsparameter
====================

## 1. Setzen einer externen Konfiguration

Über die System-Property `ninja.external.configuration` kann ein Pfad/URL zu einer externen Konfiguration angeben werden. [Weitere Infos](http://www.ninjaframework.org/documentation/configuration_and_modes.html)


## 2. Konfigurationsparameter

### Allgemein für Ninja

`application.name` 

Name der Anwendung: String

`application.cookie.prefix`

Prefix für Cookies, die von der Anwendung angelegt werden: String

`application.languages`

verfügbare Sprachen der Anwendung: List[String]

`application.session.expire_time_in_seconds`

Session-Auslaufdauer: Int

`application.session.send_only_if_changed`

Sendet nur Session-Cookie, wenn sich der Content geändert hat: Boolean

`application.session.transferred_over_https_only`

Setzt httpOnly-Flag in Session-Cookie und erlaubt ihn so nur über HTTPS versenden: Boolean

`application.secret`

Anwendungs-Schlüssel für kryptografische Operationen: String 

### MongoDb Verbindung

`ninja.mongodb.host`

Host der mongodb: String

`ninja.mongodb.port`

Port der mongodb: Int

`ninja.mongodb.dbname`

Datenbankname der mongodb: String

`ninja.mongodb.user`

Benutzer der mongodb: String

`ninja.mongodb.pass`

Passwort der mongodb: String

`ninja.mongodb.authdb`

authDb der mongodb: String

`ninja.mongodb.useInMemory`

aktiviert die in memory mongodb: Boolean

`ninja.morphia.package`

Package-Pfad für die Morphia Models: String

### hireMe spezifisch

`hireMe.showBuildInfo`

Zeigt build-Informationen im Frontend: Boolean