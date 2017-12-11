public class Card {
	enum Suit {Club, Diamond, Heart, Spade}
	private Suit suit; //Definition: 1~4, Clubs=1, Diamonds=2, Hearts=3, Spades=4
	private int rank; //1~13
	/**
	 * @param s suit
	 * @param r rank
	 */
	public Card(Suit s,int value){
		suit=s;
		rank=value;
	}	
	//TODO: 1. Please implement the printCard method (20 points, 10 for suit, 10 for rank)
	public void printCard(){
		//Hint: print (System.out.println) card as suit,rank, for example: print 1,1 as Clubs Ace
		switch(suit) { //用suit判斷並印出花色,rank判斷並印出點數(JQK特例)
		case Club:
			switch(rank) {
			case 1:
				System.out.println(suit+ " A");
				break;			
			case 11:
				System.out.println(suit+ " J");
				break;
			case 12:
				System.out.println(suit+" Q");
				break;
			case 13:
				System.out.println(suit+" K");
				break;
			default:
				System.out.println(suit+" "+rank);
				break;
			}
			break;
		case Diamond:
			switch(rank) {
			case 1:
				System.out.println(suit+ " A");
				break;
			case 11:
				System.out.println(suit+ " J");
				break;
			case 12:
				System.out.println(suit+" Q");
				break;
			case 13:
				System.out.println(suit+" K");
				break;
			default:
				System.out.println(suit+" "+rank);
				break;
			}
			break;
		case Heart:
			switch(rank) {
			case 1:
				System.out.println(suit+ " A");
				break;
			case 11:
				System.out.println(suit+ " J");
				break;
			case 12:
				System.out.println(suit+" Q");
				break;
			case 13:
				System.out.println(suit+" K");
				break;
			default:
				System.out.println(suit+" "+rank);
				break;
			}
			break;
		case Spade:
			switch(rank) {
			case 1:
				System.out.println(suit+ " A");
				break;
			case 11:
				System.out.println(suit+ " J");
				break;
			case 12:
				System.out.println(suit+" Q");
				break;
			case 13:
				System.out.println(suit+" K");
				break;
			default:
				System.out.println(suit+" "+rank);
				break;
			}
			break;
		}
	}
	public Suit getSuit(){
		return suit;
	}
	public int getRank(){
		if(rank==1)
			return 1;
		else if(rank==11||rank==12||rank==13)
			return 10;
		else
			return rank;
	}
}