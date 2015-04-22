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

### test-concept
| Profile / TestCase | protractor | integration | unit |
|-------------------:|:----------:|:-----------:|:----:|
|                dev |     NO     |      NO     |  YES |
|        integration |     NO     |     YES     |  YES |
|         protractor |     YES    |      NO     |  YES |
|                all |     YES    |     YES     |  YES |

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
