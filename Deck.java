import java.util.ArrayList;

public class Deck {
		private ArrayList<Card> cards; //�s���P
		private ArrayList<Card> usedCard; //�o�L���P
		private ArrayList<Card> openCard; //���}�L���P,�~�P�ɷ|���m
		
		public int nUsed;
		//TODO: Please implement the constructor (30 points)
		public Deck(int nDeck){
			cards=new ArrayList<Card>();
			usedCard=new ArrayList<Card>();
			openCard=new ArrayList<Card>();
			//1 Deck have 52 cards, https://en.wikipedia.org/wiki/Poker
			//Hint: Use new Card(x,y) and 3 for loops to add card into deck
			//Sample code start
			//Card card=new Card(1,1); ->means new card as clubs ace
			//cards.add(card);
			//Sample code end
			for(int card_n=0;card_n<nDeck;card_n++) { //���X�ƵP
				 for (Card.Suit s : Card.Suit.values()){
					 for(int card_r=1;card_r<=13;card_r++) //�P���I�� 1-13
						{
							Card card=new Card(s,card_r); //�إߤ@�Ʒs���P,�ö�J����Ʀr
							cards.add(card); //�N���إߪ��P�s�JArrayList�̭�
						}
				 }
			}
		}	
		public ArrayList<Card> getAllCards(){
			return cards;
		}
		public Card getOneCard(boolean isOpened) {
			if(nUsed==52) //�o�X�h���P�F��52�i�K�~�P
				shuffle();
			Card dealed=cards.get((int) (Math.random()*cards.size()-1)); //���ü��H�K�o�@�i�P
			
			nUsed++; //�o�P�@�֥[
			if(isOpened) {
				openCard.add(dealed);
				usedCard.add(dealed);
			}
			else
				usedCard.add(dealed); //�N�o�X�h���P�s�J�}�C

			return dealed;
		}
		public ArrayList getOpenedCard() {
			return openCard;
		}
		public void shuffle() {
		//			for(int i = 0; i < cards.size(); i++) {
		//				Collections.swap(cards, i, (int) (Math.random() * cards.size() - 1));
		//			}
		
					for(int i = 0; i < cards.size(); i++) {
						Card temp=cards.get(i); //temp�̧ǧ�P���X�Ȧs
						int j=(int) (Math.random() * cards.size() - 1); //�H���üƨ��o�@�ӼƦr
						cards.set(i, cards.get(j)); //�N��mi���P���N��j
						cards.set(j,temp); //�N��mj���P���N��i ��Yi�Pj���洫
					}
					
					nUsed=0;
					openCard.clear();
					usedCard.clear(); //�N�o�X���P�B���}���P�H�μƶq���k�s
				}
		//TODO: Please implement the method to print all cards on screen (10 points)
		public void printDeck(){
			//Hint: print all items in ArrayList<Card> cards, 
			//TODO: please implement and reuse printCard method in Card class (5 points)
			int deck_count=1; //�p��ĴX�Ƽ��J�P���ƶq
			for(int i=0;i<cards.size();i++) {
				if(i%52==0) {
					System.out.println("\nDeck_"+deck_count); //�L�X�ĴX�ƵP
					deck_count++;
					}
				cards.get(i).printCard(); //�L�X�Ҧ����P
				}
		}

}
