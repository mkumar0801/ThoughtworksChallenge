package thoughtwork.stage1;

import java.util.LinkedList;

public class DecryptMessage {
	
	public String dycriptMessage(String encryptedMessage,int key) {
		
		String decryptedMessage="";
		
		LinkedList<String> alphabet = new LinkedList<String>();
		alphabet.add("A");
		alphabet.add("B");
		alphabet.add("C");
		alphabet.add("D");
		alphabet.add("E");
		alphabet.add("F");
		alphabet.add("G");
		alphabet.add("H");
		alphabet.add("I");
		alphabet.add("J");
		alphabet.add("K");
		alphabet.add("L");
		alphabet.add("M");
		alphabet.add("N");
		alphabet.add("O");
		alphabet.add("P");
		alphabet.add("Q");
		alphabet.add("R");
		alphabet.add("S");
		alphabet.add("T");
		alphabet.add("U");
		alphabet.add("V");
		alphabet.add("W");
		alphabet.add("X");
		alphabet.add("Y");
		alphabet.add("Z");
		LinkedList<String> beta = new LinkedList<String>();
		
		beta.addAll(alphabet);
		
		for(int i=0;i<key;i++) {
			alphabet.addLast(alphabet.removeFirst());
		}
		
		for(int i=0;i<encryptedMessage.length();i++) {
			if(alphabet.contains(String.valueOf(encryptedMessage.charAt(i)))){
				decryptedMessage = decryptedMessage + beta.get(alphabet.indexOf(String.valueOf(encryptedMessage.charAt(i))));
			}else {
				decryptedMessage = decryptedMessage + String.valueOf(encryptedMessage.charAt(i));
			}
		}

		return decryptedMessage;
	}

}
