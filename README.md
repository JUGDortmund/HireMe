# Welcome to **hireMe**

## Quick start

### Requirements
1. Java8
2. Maven 3.3.1

### Run
1. Checkout
2. mvn clean install
3. mvn ninja:run
4. visit http://localhost:8080/


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


## Testing 

### Test-concept

#### Overview profile `(x)` to testCase `(y)` mapping

  | protractor | integration | unit 
-------------: | :-------------: | :-------------: | :-------------:
|                dev |     NO     |      NO     |  YES |
|        integration |     NO     |     YES     |  YES |
|         protractor |     YES    |      NO     |  YES |
|                all |     YES    |     YES     |  YES |

##### Usage

General:

```
mvn integration-test -P [profile]
```

Example:

```
mvn integration-test -P dev
```

## Building and deploying a release

### Create and tag a release
1. create a release branch (e.g. hireme-0.1) from the develop branch
2. mvn clean install
3. verify all tests pass
4. mvn release:prepare (set scm release version (e.g. 0.1), new delevopment version (e.g. 0.2-SNAPSHOT) and tag name (e.g. hireme-0.1))
5. mvn release:perform
6. merge release branch to develop branch and push changes
7. merge release branch to master branch and push changes


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



