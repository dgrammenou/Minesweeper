class TestLines {
    public void validate(int lines) throws InvalidDescriptionException{
	     if(lines != 4) {
		     throw new InvalidDescriptionException("Wrong number of input lines!");
	 }
 }
}
