/*AnimalGameModel.java
 * TODO: YOUR NAME HERE.
 * 
 * Implementation of IAnimalGameModel.
 */

// Author:	Tanaka Jimha

public class AnimalGameModel implements IAnimalModel {


	private AnimalGameViewer myView;
	AnimalNode myTemp;
	AnimalNode myRoot;
	AnimalNode myCurrent;
	AnimalNode myParent;
	AnimalNode mySuperParent;
	StringBuilder myPath = new StringBuilder();



	private AnimalNode readHelper(Scanner s) {
		String line = s.nextLine();

		if(line.startsWith("#Q:")) {
			AnimalNode yes = readHelper(s);
			AnimalNode no = readHelper(s);
			return new AnimalNode(line, yes, no);
		} else {
			return new AnimalNode(line, null, null);
		}			

	}

	public void preorderTraversalPrint(AnimalNode root, FileWriter writer) { // Helper Function for writing to a file
		if(root == null) return;
		try {
			writer.write(root.toString() + "\n");
			preorderTraversalPrint(root.getYes(), writer);
			preorderTraversalPrint(root.getNo(), writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addNewKnowledge(String question) {

		AnimalNode newQuestion = new AnimalNode(question, myParent, myTemp);	//The new question to differentiate between the two animals is set as the animals' parent

		mySuperParent.setNo(newQuestion);	

		newGame();
		
		myView.showDialog("Thank you! You can start a new game now, or save your file");

	}

	@Override
	public void addNewQuestion(String noResponse) {

		myTemp = new AnimalNode(noResponse, null,null);	//Storing the new question we just got

		showMyPath();
		myView.update("Can you do me another favour please: \n Enter a question whose answer is YES for " + myParent.getMyData() +  "but NO for the animal you have in mind ");

		myView.getDifferentiator();	

	}

	@Override
	public void initialize(Scanner s) {
		// TODO Auto-generated method stub
		myView.setEnabled(true);
		myRoot = readHelper(s);
		newGame();
	}

	@Override
	public void newGame() {
		myCurrent = myRoot;
		askQuestion(myRoot);

		// TODO Auto-generated method stub
	}

	@Override
	public void processYesNo(boolean yes) {

		myParent = myCurrent; // Storing value of current node
		mySuperParent = (myCurrent.getNo() != null || myCurrent.getYes() != null)? myParent : mySuperParent; // check to see if children are null, otherwise keep moving

		if(yes) {
			trackMyPath(myCurrent, "yes");	//tracking my Path for a yes response
			myCurrent = myCurrent.getYes();	// Moving to the yes child
		} 

		else {
			trackMyPath(myCurrent, "no");	//tracking my Path for a no response
			myCurrent = myCurrent.getNo();	//moving to the no child
		}

		if(myCurrent == null && yes){	//If we have reached a correct animal
			myView.showDialog("I win! You can play again.");
			newGame();
		} 
		if(myCurrent == null && !yes){	//If we have gotten a wrong animal
			showMyPath();	
			myView.getNewInfoLeaf();
		}


		askQuestion(myCurrent);//asks the user a new question
	}



	@Override
	public void setView(AnimalGameViewer view) {
		myView = view;
	}

	@Override
	public void write(FileWriter writer) {
		preorderTraversalPrint(myRoot, writer);

	}
	public void askQuestion(AnimalNode myCurrent){	//asks the player a new question
		StringBuilder sb = new StringBuilder();
		sb.append(myCurrent.toString() +"\n");
		myView.update(sb.toString());

	}

	public void trackMyPath(AnimalNode myCurrent, String answer ) {	//Keeps track of the path the user is taking

		if(answer.toLowerCase() == "yes"){ //tracking a response to a YES answer
			myPath.append("You answered YES to " + myCurrent.toString() +"\n");	
		}
		if(answer.toLowerCase() == "no") {	//tracking a response to a no answer
			myPath.append("You answered NO to " + myCurrent.toString() +"\n");
		}
	}

	public void showMyPath(){	//shows the path of the player has taken
		String other = "You win! \n Can you do me a favour please: \n Kindly enter the right question identifying the animal you are thinking about. \n For example: \n If you are thinkng of a Lion, please ask: Is it a lion? \n \n";
		myView.update(other + myPath.toString());
	}
}
