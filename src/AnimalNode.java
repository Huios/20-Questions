
/**
 * An object representing a single node in a Twenty Questions game tree.
 * @author Mac Mason <mac@cs.duke.edu>
 */
public class AnimalNode {
	private AnimalNode myYesChild;
	private AnimalNode myNoChild;
	private String myData;

	public AnimalNode(String data, AnimalNode yes, AnimalNode no) {
		myNoChild = no;
		myYesChild = yes;
		myData = data;
	}

	public String toString(){
		return getMyData();
	}

	public AnimalNode getYes() { 
		return myYesChild;
	}

	public AnimalNode getNo() {
		return myNoChild;
	}

	public void setMyData(String data){
		myData = data;
	}

	public void setYes(AnimalNode yes){//added setter method
		myYesChild = yes;
	}

	public void setNo(AnimalNode no){ //added setter method
		myNoChild = no;
	}

	public String getMyData() { //added getter method
		return myData;
	}


}
/*
 * You may need to add setter functions. Go right ahead.
 */

