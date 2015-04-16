# Welcome to **hireMe**

### Requirements
1. Java8
2. Maven 3.3.1

### Run
1. Checkout
2. mvn clean install
3. mvn ninja:run
4. visit http://localhost:8080/

### Google-styleguide
To enable a default styleguide for both ide's: Intellij and eclipse,
we defined the google-styleguide as default code style.

## Installation Guide for IntelliJ
1. Download google-styleguide for intellij: https://code.google.com/p/google-styleguide/source/browse/trunk/intellij-java-google-style.xml
2. Move the intellij-java-google-style.xml to ${User}/Library/Preferences/IntelliJIdea${Version}/
3. Restart IntelliJ
4. Enable the google-styleguide by Preferences >  Editor CodeStyle > Scheme : GoogleStyle
5. Enable auto-code-style by commit by checking Before Commit > Reformat Code in screen 'Commit Changes (cmd+K)'
![IntelliJ Screen Reformat Code by Commit](/readme-sources/intellij-reformatcode-commit.png)