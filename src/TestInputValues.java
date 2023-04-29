class TestInputValues {
	public void validateinput(int firstline, int secondline, int thirdline, int fourthline) throws InvalidValueException{
		  
		 if(firstline == 1) {
			
			    if((secondline-9)*(secondline-11) <= 0) {
			    	if((thirdline-120)*(thirdline -180) <= 0) {
			    		if(fourthline != 0) {
					    	 throw new InvalidValueException("Invalid Input data");
			    	}
			    	}
			        else {
			        	throw new InvalidValueException("Invalid input data");
			        }
			    }
			    else {
			    	  throw new InvalidValueException("Invalid input data");
			    }
			    			     
		 }
		 else if(firstline == 2) {
			 	
			 if((secondline-35)*(secondline-45) <= 0) {
			    	if((thirdline-240)*(thirdline - 360) <= 0) {
			    		if(fourthline != 1) {
					    	 throw new InvalidValueException("Invalid Input data");
			    	}
			    	}
			        else {
			        	throw new InvalidValueException("Invalid input data");
			        }
			    }
			    else {
			    	  throw new InvalidValueException("Invalid input data");
			    }
			    
			 	 
			 }
		 else {
			 throw new InvalidValueException("First line describes difficulty level!It must be 1 or 2!");
		 }
		 }
}