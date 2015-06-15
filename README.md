# Welcome to **hireMe**


******************************************************


## Quick start

### Requirements
1. Java8
2. Maven 3.3.1

### Run
1. Checkout
2. `mvn clean install`
3. `mvn ninja:run`
4. visit http://localhost:8080/


******************************************************


## Development-Environment

### Installation of Development environment

#### Installation guide for Eclipse
1. Download and install *Eclipse IDE for Java EE Developers* from https://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/lunasr2

### Google-styleguide
To enable a default styleguide for both ide's: Intellij and eclipse,
we defined the google-styleguide as default code style.

#### Installation Guide for **IntelliJ**
1. Locate google-styleguide for IntelliJ: [intellij-java-google-style.xml](http://stash.maredit.net/projects/COM/repos/hireme/browse/readme-sources/intellij-java-google-style.xml?raw)
2. Move the intellij-java-google-style.xml to ${User}/Library/Preferences/IntelliJIdea${Version}/
3. Restart IntelliJ
4. Enable the google-styleguide by Preferences >  Editor CodeStyle > Scheme : GoogleStyle
5. Enable auto-code-style by commit by checking Before Commit > Reformat Code in screen 'Commit Changes (cmd+K)'
![IntelliJ Screen Reformat Code by Commit](http://stash.maredit.net/projects/COM/repos/hireme/browse/readme-sources/intellij-reformatcode-commit.png?raw)


#### Installation Guide for **Eclipse**

1. Locate google-styleguide for Eclipse: [eclipse-java-google-style.xml](http://stash.maredit.net/projects/COM/repos/hireme/browse/readme-sources/eclipse-java-google-style.xml?raw)
2. Import google-styleguide in Eclipse via Preferences > Java > Code Style > Import...
3. Enable google-styleguide in Eclipse via Preferences > Java > Code Style : GoogleStyle
4. Enable auto-code-style on save in Eclipse via Preferences > Java > Editor > Save Actions

![Eclipse Screen Reformat Code on save](http://stash.maredit.net/projects/COM/repos/hireme/browse/readme-sources/eclipse-reformatcode-save.png?raw)


### Start gulp watcher
1. In project root directory execute: `./gulp-watcher` to automatically build all distribution asset files (.js, .css) after changing/saving source files (.js, .coffee, .css, .less).


### Guidelines

#### [HTTP/1.1 Method Definitions](http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html)

Official W3C Spec on how to use the HTTP-methods

******************************************************


## Testing 

### Test-concept

#### Overview profile `(x)` to testCase `(y)` mapping

| Profile                   | unit tests executed  |  integration tests executed  | protractor tests executed
--------------------------:  | :-------------------:  | :---------------------------:  | :-------------:
|  env-dev *(default)*    |  YES          |  NO              |  NO
|  env-jenkins        |  YES          |  YES              |  YES
|  env-build        |  NO          |  NO              |  NO
|  env-prod        |  NO          |  NO              |  NO
|  tests-none        |  NO          |  NO              |  NO
|  tests-unit        |  YES          |  NO              |  NO
|  tests-integration    |  YES          |  YES              |  NO  
|  tests-protractor    |  YES          |  NO              |  YES  
|  tests-all        |  YES          |  YES              |  YES

##### Usage

Execute reasonable tests for a given environment:

```
mvn integration-test -P [env-profile]
```

Example (execute reasonable tests for develop environment):

```
mvn integration-test -P env-dev
```

Run other tests than reasonable for a given environment

```
mvn integration-test -P [env-profile],[test-override-profile]
```

Example (execute all tests in develop environment, instead of just unit tests):

```
mvn integration-test -P env-dev,tests-all
```

*Important*: Never try to override test executions for environments `env-build` and `env-prod` as the tests would be executed on the db connection of the build or prod system!

*Note*: Protractor tests per default are executed on the [mercus grid](http://mercus-selenium-grid.maredit.net:4444/wd/hub) using the ip-address that the integration tests were started from as server. If you start the integration tests locally be sure to deactivate your firewall, so that the grid can access http://[your-ip-address]:8080/`.


####### Run protractor tests locally

For testing purposes it may be useful to run protractor tests locally and see how the tests are executed.
To run all protractor tests locally, first ensure that ninja is running

```
mvn ninja:run
```

and afterwards execute protractor:

```
./protractor
```

It is also possible to run just a single protractor test via:

```
./protractor --specs src/test/javascript/e2e-tests/dashboard/dashboard.spec.js
```

To change how protractor is run locally, you could change the config file: `/hireme/src/test/javascript/conf/protractor.local.conf.js`

******************************************************


## Building and deploying a release

### Environment-specific build

The environment determines which configuration properties the application should be build with.

* Existing environments
  * **env-dev** - environment for local development (active by default)
  * **env-jenkins**  - environment for jenkins/tests
  * **env-build** - environment for build system
  * **env-prod** - environment for prod system
  
The configuration files for the different environments are located at: `/hireme/src/env/[environment]`
During the build of the application with an environment parameter, the environment-specific configuration is copied to `target/classes`,
so the resulting target directory is in a form that the application will be using the environment-specific configuration by default.

Command for building environment-specific:

```
mvn clean install -P [environment]
```

Example:

```
mvn clean install -P env-build
```

### Deploy environment specific artifacts to nexus

The following command will build with the jenkins environment configuration,
runs all tests, build user and operation manual and creates deployment artifacts
for use in development, build and production systems.

```
mvn clean deploy -Penv-jenkins,build-manuals,build-deployment-artifacts
```

Resulting artifacts pushed to nexus:

* **hireme-[version]-dev.war** - war file for development
* **hireme-[version]-build.war** - war file for build system
* **hireme-[version]-prod.war** - war file for prod system
* **hireme-[version]-site.jar** - usermanual
* **hireme-[version]-operationmanual.pdf** - operational manual

### Create and tag a release
1. create a release branch (e.g. *hireme-0.1*) from develop branch
2. `mvn clean install`
3. verify all tests pass
4. `mvn release:prepare` (set **scm release version** (e.g. *0.1*), **new delevopment version** (e.g. *0.2-SNAPSHOT*) and **tag name** (e.g. *hireme-0.1*))
5. `mvn release:perform`
6. merge *release* branch to *develop* branch and push changes
7. merge *release* branch to *master* branch and push changes

### Deploy a release
1. `mvn clean install -P[environment]`
2. deploy release to desired system (see commands below)

### Deploy commands
* General deploy command: `mvn cargo:[command] -P [system]`
* Commands
  * **deploy** - deploys the application to the system
  * **undeploy**  - undeploy the application from the system
  * **redeploy** - Undeploy and deploy the application to the system again. If the application was not deployed before calling cargo:redeploy it will simply be deployed.
* Deploy system profiles
  * **system-build** - deploy to build system
  * **system-prod** - deploy to prod system  
* Examples:
  * Deploy build system: `mvn cargo:deploy -P system-build`
  * Redeploy prod system: `mvn cargo:redeploy -P system-prod`

******************************************************


## Creating Export-Templates

All Templates must be located in /exportTemplates/` within the classpath. 
A template consists of a json file that contains the metadata and a template file. 

### Variables

- All variables that can be used are from `model.Profile`
- Lists of String that are annotated are concatenated by default to a String like  `value1, value2, ...`
- If a List of Strings is annotated by `@ExcludeFromStringConcatenation` the list will not be concatenated into a string

### ProfileImage

- `<img src="profileImage.png>` will print the profile image. 
- If no image in set inside the profile the default profile image will be printed

### Print all available variables

 Add macro inside template
`<#list .data_model?keys as var>
  ${var}
  </#list>`

******************************************************


## Documentation

All documentation is written in markdown language.
To get familiar with the syntax see [Markdown Basics](http://daringfireball.net/projects/markdown/basics) and [Markdown Syntax](http://daringfireball.net/projects/markdown/syntax).

### Developer documentation

All developer documentation should be integrated in this readme file.  

### User manual

The user manual gives an overview of the application and answers questions about special non-obvious use cases.
It is a manual website which is integrated into the application and can be accessed on a running instance of the application via the *Help*-Button.

The user manual is written in markdown and compiled to a website.
All source pages of the manual are located at

```
src/main/usermanual/markdown/*.md
```

All resources used in the manual files should be placed at

```
src/main/usermanual/resources
```

The compilation of the user manual website can be configured in

```
src/main/usermanual/site.xml
```

All pages that should be included in the user manual need to be specified in this file via

```
<menu name="Anwenderdokumentation">
...
  <item href="myPage.html" name="My Page Title" />
...
</menu>
```

### Operation manual

The operation manual contains all information needed for the maintenance of the application.  
It is written in markdown and compiled to pdf.
All source pages of the manual are located at

```
src/main/operationmanual/markdown/*.md
```

All resources used in the manual files should be placed at

```
src/main/operationmanual/resources
```

The compilation of the operation manual pdf can be configured in

```
src/main/usermanual/pdf.xml
```

All pages that should be included in the operation manual need to be specified in this file via

```
<toc name="Inhaltsverzeichnis">
...
  <item ref="myPage.md" name="My Page Title" />
...
</toc>
```

### Generation of manuals

The user manual and the operation manual can be build via

```
mvn package -P [environment],build-manuals
```

Example:

```
mvn package -P env-dev,build-manuals
```

The user manual will be compiled to

```
target/classes/assets/usermanual
```

The operation manual will be compiled to

```
target/classes/assets/operationmanual
```



******************************************************


## Technology usages

### Web framework (Full Stack)

<img src='http://stash.maredit.net/projects/COM/repos/hireme/browse/readme-sources/tech-stickers/ninja.png?raw' width='230' height='70' />

[Ninja Framework Homepage](http://www.ninjaframework.org/)

### Javascript framework

<img src='http://stash.maredit.net/projects/COM/repos/hireme/browse/readme-sources/tech-stickers/angularjs.png?raw' width='70' height='70' />

[AngularJs Homepage](https://angularjs.org/)

### Database

<img src='http://stash.maredit.net/projects/COM/repos/hireme/browse/readme-sources/tech-stickers/mongodb.png?raw' width='70' height='70' />

[MongoDB Homepage](https://www.mongodb.org/)

### Build-Management-Tool

<img src='http://stash.maredit.net/projects/COM/repos/hireme/browse/readme-sources/tech-stickers/maven.png?raw' width='230' height='70' />

[Maven Homepage](http://maven.apache.org/)

### Test frameworks

<img src='http://stash.maredit.net/projects/COM/repos/hireme/browse/readme-sources/tech-stickers/karma.png?raw' width='70' height='70' />

[Karma Homepage](http://karma-runner.github.io)

<img src='http://stash.maredit.net/projects/COM/repos/hireme/browse/readme-sources/tech-stickers/phantomjs-logo.png?raw' width='230' height='70' />

[PhantomJS Homepage](http://phantomjs.org/)

#### Framework helpers

<img src='http://stash.maredit.net/projects/COM/repos/hireme/browse/readme-sources/tech-stickers/nodejs.png?raw' width='70' height='70' />

[NodeJs Homepage](https://nodejs.org/)

<img src='http://stash.maredit.net/projects/COM/repos/hireme/browse/readme-sources/tech-stickers/npm.png?raw' width='70' height='70' />

[npm Homepage](https://www.npmjs.com/)



