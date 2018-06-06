package panel.util;

public class LanguageUtil {
	private char[] cho = {
			'ぁ','あ', 'い', 'ぇ', 'え', 'ぉ', 'け', 'げ', 'こ', 'さ', 'ざ', 'し', 'じ', 'す', 'ず', 'せ', 'ぜ', 'そ', 'ぞ' 
	};
	
	private char[] jung = {
			'た', 'だ', 'ち', 'ぢ', 'っ', 'つ', 'づ', 'て', 'で', 'と',
			'ど', 'な', 'に', 'ぬ', 'ね', 'の', 'は', 'ば', 'ぱ', 'ひ','び' 
	};
	
	private char[] jong = {
			' ', 'ぁ', 'あ', 'ぃ', 'い', 'ぅ', 'う', 'ぇ', 'ぉ', 'お', 
			'か', 'が', 'き', 'ぎ', 'く', 'ぐ', 'け', 'げ', 'ご', 'さ',
			'ざ', 'し', 'じ', 'ず', 'せ', 'ぜ', 'そ', 'ぞ'
	};
	
	public int findChosung (char c) {
		int index = 0;
		for(int i = 0; i < cho.length; i++) {
			if(cho[i] == c) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	public int findJungsung (char c) {
		int index = 0;
		for(int i = 0; i < jung.length; i++) {
			if(jung[i] == c) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	public int findJongsung (char c) {
		int index = 0;
		for(int i = 0; i < jong.length; i++) {
			if(jong[i] == c) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	public int jong2cho(int jong_index) {
		int index = -1;
		for(int i = 0; i < cho.length; i++) {
			if(jong[jong_index] == cho[i]) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	public int getChosung(char c) {
		return ((((int)c - 0xAC00) - ((int)c - 0xAC00) % 28) / 28) / 21;
	}
	
	public int getJungsung(char c) {
		return ((((int)c - 0xAC00) - ((int)c - 0xAC00) % 28) / 28) % 21;
	}
	
	public int getJongsung(char c) {
		return ((int)c - 0xAC00) % 28;
	}
	
	public boolean checkVowels(int jung_index) {
		if(jung_index == 8 || jung_index == 13 || jung_index == 18) {
			return true;
		}
		else
			return false;
	}
	
	public int mergeVowels(int jung_index, char input) {
		if(jung_index == 8) {
			switch(findJungsung(input)) {
			case 0: return 9;
			case 1: return 10;
			case 20: return 11;
			default: break;
			}
		}
		else if(jung_index == 13) {
			switch(findJungsung(input)) {
			case 4: return 14;
			case 5: return 15;
			case 20: return 16;
			default: break;
			}
		}
		else if(jung_index == 18) {
			if(findJungsung(input) == 20) {
				return 19;
			}
		}
		return 0;
	}
	
	public char findVowelsChar(int jung_index) {
		return jung[jung_index];
	}
	
	public char combineWord(int cho, int jung, int jong) {
		int sum = (cho * 21 * 28) + (jung * 28) + jong;
		return (char) (sum + 0xAC00);
	}
	
	public char onlyCho(int cho_index) {
		return cho[cho_index];
	}
	
	public int isBadchim(int jong_index, char cho) {
		
		int cho_index = findChosung(cho);
		
		switch(jong_index) {
		case 1:	//ぁ
			if(cho_index == 0) {
				return 3;
			}
			break;
		case 4:	//い
			if(cho_index == 12) {
				return 5;
			}
			else if(cho_index == 18) {
				return 6;
			}
			break;
		case 8:
			if(cho_index == 0) {
				return 9;
			}
			else if(cho_index == 6) {
				return 10;
			}
			else if(cho_index == 7) {
				return 11;
			}
			else if(cho_index == 9) {
				return 12;
			}
			else if(cho_index == 16) {
				return 13;
			}
			else if(cho_index == 17) {
				return 14;
			}
			else if(cho_index == 18) {
				return 15;
			}
			break;
		case 17:
			if(cho_index == 9) {
				return 18;
			}
			break;
		}

		
		return -1;
	}
}
