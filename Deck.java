import java.util.ArrayList;

public class Deck {
		private ArrayList<Card> cards; //新的牌
		private ArrayList<Card> usedCard; //發過的牌
		private ArrayList<Card> openCard; //打開過的牌,洗牌時會重置
		
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
			for(int card_n=0;card_n<nDeck;card_n++) { //有幾副牌
				 for (Card.Suit s : Card.Suit.values()){
					 for(int card_r=1;card_r<=13;card_r++) //牌的點數 1-13
						{
							Card card=new Card(s,card_r); //建立一副新的牌,並填入花色跟數字
							cards.add(card); //將剛剛建立的牌存入ArrayList裡面
						}
				 }
			}
		}	
		public ArrayList<Card> getAllCards(){
			return cards;
		}
		public Card getOneCard(boolean isOpened) {
			if(nUsed==52) //發出去的牌達到52張便洗牌
				shuffle();
			Card dealed=cards.get((int) (Math.random()*cards.size()-1)); //取亂數隨便發一張牌
			
			nUsed++; //發牌作累加
			if(isOpened) {
				openCard.add(dealed);
				usedCard.add(dealed);
			}
			else
				usedCard.add(dealed); //將發出去的牌存入陣列

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
						Card temp=cards.get(i); //temp依序把牌取出暫存
						int j=(int) (Math.random() * cards.size() - 1); //隨機亂數取得一個數字
						cards.set(i, cards.get(j)); //將位置i的牌取代為j
						cards.set(j,temp); //將位置j的牌取代為i 亦即i與j做交換
					}
					
					nUsed=0;
					openCard.clear();
					usedCard.clear(); //將發出的牌、打開的牌以及數量做歸零
				}
		//TODO: Please implement the method to print all cards on screen (10 points)
		public void printDeck(){
			//Hint: print all items in ArrayList<Card> cards, 
			//TODO: please implement and reuse printCard method in Card class (5 points)
			int deck_count=1; //計算第幾副撲克牌的數量
			for(int i=0;i<cards.size();i++) {
				if(i%52==0) {
					System.out.println("\nDeck_"+deck_count); //印出第幾副牌
					deck_count++;
					}
				cards.get(i).printCard(); //印出所有的牌
				}
		}

}
