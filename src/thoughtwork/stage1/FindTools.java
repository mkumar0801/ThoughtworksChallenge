package thoughtwork.stage1;

import java.util.HashMap;
import java.util.LinkedList;

public class FindTools {
	
	public HashMap<String,Boolean> findTools(String hiddenTools, LinkedList<String> tools ) {
	
		HashMap<String,Boolean> toolsFound = new HashMap<String,Boolean>() ;
		
		for(String tool : tools) {
			System.out.println(tool);
			boolean found = true;
			int index = 0;
			for (Character c : tool.toCharArray()){
				if(hiddenTools.indexOf(c,index)>-1) {
					index = hiddenTools.indexOf(c, index)+1;
					System.out.println(index);
				}else {
					System.out.println("Iam in else");
					found = false;
					System.out.println(found);
					break;
				}
				
			}
			
			if(found) {
				toolsFound.put(tool, found);
				hiddenTools = hiddenTools.substring(index);
				System.out.println(hiddenTools);
			}else {
				toolsFound.put(tool, false);
			}
			
		}
		
		System.out.println(toolsFound);
		return toolsFound;
	}
		

}


