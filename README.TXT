------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE:TicTacToe
PURPOSE OF PROJECT: Play a TicTacToe game with server and multiple clients.
VERSION or DATE: 05/11/20
HOW TO START THIS PROJECT:
AUTHORS:Akmal Muminov and Yael Weiss
USER INSTRUCTIONS:
 1. To launch application, TicTacToeServer is ran and requires port number and server name. --> ie."4444","Server"
 2. Then run TicTacToeClient which requires host name, port number and client's name --> ie. "localhost","4444","Pooja"
 3. Application runs and user is prompted to enter first move in game --> 3, for third box on board
 4. Server side also recieves prompt for its new move
 5. Game is played until either side wins. 
 6. Client/user is prompted for whether they wish to play again --> ie. "Do you want to play again?  Y/N"
 7. Depending on previous decision, new game runs again or not.
 8. After each game, leaderboard is popped up. After each session the leaderboard is updated and sorted according to number of wins each player has.


 - What features were implemented thus far
	- Functioning TTT game w/ synchronization. We implemented everything that was described in the
	requirements and design document.
 - To what extent the feature requiring synchronization was completed
	- Fully.
 - How you would proceed to enhance the application given more time
	- Create a GUI for the tic tac toe board 

 - Output of your test cases (screenshots)
	- In separate file: Screenshots.docx

 - Enhanced version should have a feature that requires synchronized communication
	- Implementation of leaderboard requires synchronization

For this project, TCP protocol was used for reliable data transfer. It is seen through the creation of connections.



