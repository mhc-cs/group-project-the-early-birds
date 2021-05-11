# cs316-Guess Who
## Created on May 10, 2021
## Authors: Dani Levy, Hannah Brown, Anna Pickett
## Customer: Rachel Marks
### How to run jar file:
The server code is continuously running on Murphy’s game server on "Sockette.net" and with port number 9878 so you should be able to connect to it without doing anything additonal
Make sure you have at least Java 11 JDK
How to determine version in terminal: Java -version
If you need to install a Java JDK, install the JDK from https://www.oracle.com/java/technologies/javase-downloads.html and set up the class path by typing these lines into the command line: 
set JAVA_HOME="your jdk path"
set PATH=%JAVA_HOME%\bin;%PATH%.
Install JavaFX SDK version 11.0.2 https://gluonhq.com/products/javafx/
In the command line, navigate to the folder with the .jar file that was included in the project (GuessWho.jar)
Run this line:
java --module-path [YOUR PATH TO javafx-sdk-11.0.2\lib] --add-modules=javafx.controls,javafx.fxml -cp GuessWho.jar GuessWho.Controller
How to run code from Eclipse:
Our game uses two external libraries that will need to be added to the classpath, one for the GSON of JSON messages and one for JavaFX.
First, set up JavaFX:
Watch this (https://www.youtube.com/watch?v=bk28ytggz7E) video for a short tutorial, or follow these steps:
Go to Help -> Eclipse Marketplace and search for fx. Then install the e(fx)clipse plugin.
Install JavaFX version 11.0.2 https://gluonhq.com/products/javafx/
Extract the files to the directory of your choosing.
Go to Window -> Preferences and search “user”, then click on User Libraries under Build Path.
Click “New…” and name the library “JavaFX”
Click on the library you just added (JavaFX) and click “Add External JARs...”
Navigate to the lib folder of your JavaFX installation and select all the items EXCEPT src and press open. Apply and close.
Back in Eclipse, right click on the top item in the Guess Who directory, named GuessWho and choose Build Path -> Configure Build Path.
Go to Libraries, click on Classpath and Add Library
Click on User Library, next, select JavaFX and press finish, then apply and close.
Set the run configurations by clicking on the arrow next to the play button at the top of the screen and clicking Run Configurations...
Then, set up GSON (from Dev)
Download gson-2.6.2.jar
Add Gson to the buildpath: Build Path > Add External Archives, then select your jar file.
You may need to go into security settings to allow your computer to open this file

## Directory structure: 
Our game has four main files: 
Application: Contains files related to the UI including the fxml and css that draw the screens and screen controller files that regulate transitions between screens and button actions.
application.defaultImages: Contains the png files used in the game such as the card images.
guesswho: Contains the java class files that control the game logic, including the Controller which contains the main method.
Messages: Contains Message and its child classes which are used to properly store the information either translated from JSON messages or information that will be translated to a JSON message. 
## Implemented functions:
Host and player computers connect over the Internet.
Two different cards are chosen and allocated to each player.
A chat feature.
When a card is clicked on, it gets greyed out, and when clicked on again, it goes back to normal.
The ability to have multiple rounds and keep score.
A turn indicator and end turn button, as well as a guess button.
Allowing the host and the player to name themselves.
Click on a question mark button to display the “How to Play” page.
## Known problems:
No error handling for REPEATCODE or BADCODE error messages from the server. The game will not crash, but the user will not connect over the server. 
JSON parser can get overwhelmed if too many messages are sent at one time
Setup for the game takes several steps and is not standardized.
## Changes from original plan:
Most of the main features of the game were implemented, but for some “wishlist” features we didn’t have time to implement them. These included the customization of card images and names, the ability to change the number of cards, the ability to redraw your card, and a voice chat feature.
The only main feature we didn’t implement was making it easy to download and set up because we couldn’t get jpackage to work with gson.
