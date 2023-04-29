/** The InvalidDescriptionException wraps all standard Java exception 
 * and enriches them with a custom string.
 * 
 * @author 12345
 */

class InvalidDescriptionException extends Exception{
	public InvalidDescriptionException(String str)
	{
       super(str);
    }
}
