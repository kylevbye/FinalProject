package edu.lewisu.cs.kylevbye;

public class AsgoreDialogue {
	
	///
	///	Fields
	///
	
	private int dialogueCounter;
	
	///
	///	Getters
	///
	
	public int getDialogueCounter() { return dialogueCounter; }
	
	///
	///	Setters
	///
	
	public void setDialogueCounter(int dialogueCounterIn) { 
		if (dialogueCounterIn > 0) dialogueCounter = dialogueCounterIn;
	}
	
	///
	///	Functions
	///
	
	public void incrementDialogue() { ++dialogueCounter; }
	
	public String currentDialogue() {
		
		String dialogue = new String();
		
		switch (dialogueCounter) {
		
		case 0:
			break;
			
		case 1:
			break;
			
		case 2:
			break;
			
		case 3:
			break;
			
		}
		
		return dialogue;
	}
	
	///
	///	Constructors
	///
	public AsgoreDialogue() { setDialogueCounter(0); }

}
