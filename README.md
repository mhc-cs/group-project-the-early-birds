# cs316-template
### How to add more Decks:
Add your images to the application.defaultImages folder. They should be approximately 1x2 aspect ratio and be named “deckname#.png” with numbers 0-23.

In guesswho.Deck add a private String[] named “decknameNames” containing all the card names in order. Then add the following to the chosen deck constructor which takes a string as a parameter, substituting your deckname in: 

        else if(name.equals("deckname")) {
        	useNames = decknameNames;
        }
        
In application.HostGamecodeScreenController find the following line in the initialize method and add your deckname to the list. 

     		deckOptions.getItems().addAll("Default", "Avengers"); 

Export to jar and run as described above.
