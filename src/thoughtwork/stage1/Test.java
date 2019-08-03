package thoughtwork.stage1;

import java.util.HashMap;
import java.util.LinkedList;

public class Test {
	
	public static void main(String[] args) {
		/**
		String decryptedMessage ;
		DecryptMessage decrypt = new DecryptMessage();
		decryptedMessage = decrypt.dycriptMessage("GZ DGGC, A LZAFC QGMJ UDAWFLK FWWV QGM. SK QGM GFUW KSAV, KG EWEGJSTDQ, AL'K SDD BMKL S TMKAFWKK. KG YG, YG VG TMKAFWKK.", 18);
		System.out.println(decryptedMessage);
		
		Stage1 stage1 = new Stage1();
		stage1.solveStage1();
		**/
		String hiddenTools = "opekandifehgujnsr";
		//String hiddenTools = "asdkasfnafgfdihfghjegjggkuknlsjklroasdfpe345targmoxzucvr";
		//String hiddenTools = "fasfaladsfadsaafdsrfadsfesferwiterwrgstfadsgifghdugurynrgrdfovdfpgsge";
		LinkedList<String> tools = new LinkedList<String>();
		HashMap<String,Boolean> toolsFound = new HashMap<String,Boolean>() ;
		//String tool1 = "knife";
		tools.add("knife");
		tools.add("guns");
		tools.add("rope");
//		tools.add("map");
//		tools.add("armour");
//		
//		tools.add("flare");
//		tools.add("firstaid");
//		tools.add("crowbar");
//		tools.add("gun");
//		tools.add("rope");
		
		for(String tool : tools) {
			System.out.println(tool);
			boolean found = true;
			int index = 0;
			for (Character c : tool.toCharArray()){
				if(hiddenTools.indexOf(c,index)>-1) {
					//hiddenTools = hiddenTools.substring(hiddenTools.indexOf("k")+1);
					//index = hiddenTools.indexOf(c)+1;
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
		
	}
		
		//System.out.println(hiddenTools.substring(3));	
}


