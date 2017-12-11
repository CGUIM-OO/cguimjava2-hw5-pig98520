import java.util.ArrayList;

public class Table {
	private int nDeck;
	private Deck deck; //所有的牌
	private Player[] allPlayer; //所有玩家
	private Dealer dealer; //莊家
	private int[] pos_betArray=new int[MAXPLAYER]; //玩家每一局所下的注
	private ArrayList<ArrayList<Card>> playerCards = new ArrayList<ArrayList<Card>>();
	private ArrayList<Card> player1Card = new ArrayList<Card>();
	private ArrayList<Card> player2Card = new ArrayList<Card>();
	private ArrayList<Card> player3Card = new ArrayList<Card>();
	private ArrayList<Card> player4Card = new ArrayList<Card>(); //四個玩家所得到的牌
	private ArrayList<Card> dealerCard=new ArrayList<Card>(); //存放上面四個的牌的ArrayList

	static final int  MAXPLAYER = 4; //最大限制人數為4人
	
	public Table(int nDeck) {
		this.nDeck=nDeck;
		this.deck=new Deck(nDeck);
		this.allPlayer=new Player[MAXPLAYER]; //設定player人數
		
		playerCards.clear();
		playerCards.add(player1Card);
		playerCards.add(player2Card);
		playerCards.add(player3Card);
		playerCards.add(player4Card);//將四個player的牌都存入一個ArrayList以方便迴圈取用
	}
	
	public void set_player(int pos,Player p) {
		allPlayer[pos]=p; //建立Player
	}
	
	public Player[] get_player() {
		return allPlayer;
	}
	
	public void set_dealer(Dealer d) {
		this.dealer=d; //建立Dealer
	}
	public Card get_face_up_card_of_dealer() {
		return dealerCard.get(1); //回傳莊家拿到的第二張牌(打開的牌)
	}
	private void ask_each_player_about_bets() {
		for (int i=0;i<allPlayer.length;i++) {
			allPlayer[i].sayHello(); //玩家打招呼
			allPlayer[i].makeBet(); //玩家下注
			pos_betArray[i]=allPlayer[i].makeBet(); //下注金額暫存
		}
	}
	private void distribute_cards_to_dealer_and_players() {
		for(int i=0;i<allPlayer.length;i++) {
			playerCards.get(i).add(deck.getOneCard(true));
			playerCards.get(i).add(deck.getOneCard(true));
			allPlayer[i].setOneRoundCard(playerCards.get(i));
		}//先發給玩家兩張蓋住的牌
		
		dealerCard.add(deck.getOneCard(false));
		dealerCard.add(deck.getOneCard(true));
		dealer.setOneRoundCard(dealerCard);
		//先發給莊家一張蓋住及一張打開的牌
		System.out.print("Dealer's face up card is: ");
		get_face_up_card_of_dealer().printCard();
		System.out.println();
	}
	private void ask_each_player_about_hits() {
		for (int i=0;i<allPlayer.length;i++) {
			if(allPlayer[i].getTotalValue()<=21) {
				//如果玩家的目前點數小於21點才有要牌的機會
				boolean hit=false;
				do{
					hit=allPlayer[i].hit_me(this); //this
					if(hit){
						playerCards.get(i).add(deck.getOneCard(true));
						allPlayer[i].setOneRoundCard(playerCards.get(i));
						System.out.print("Hit! ");
						System.out.println(allPlayer[i].getName()+"'s Cards now:");
						for(Card c : playerCards.get(i)){
							c.printCard();
						}//玩家要牌
					}
					else{
						System.out.println(allPlayer[i].getName()+", Pass hit!");
						System.out.println(allPlayer[i].getName()+", Final Card:");
						for(Card c : playerCards.get(i)){
							c.printCard();
						}
						System.out.println();
					}//玩家不要牌
				}while(hit);
			}
			else {
				System.out.println(allPlayer[i].getName()+", is Bomb!");
				System.out.println(allPlayer[i].getName()+", Final Card:");
				for(Card c : playerCards.get(i)){
					c.printCard();
				}
				System.out.println();
			}//玩家點數超過21點就顯示爆掉
		}
	}
	private void ask_dealer_about_hits() {
		boolean hit=false;
		if(dealer.getTotalValue()<=21) {
			//如果莊家點數小於21點才有要牌的機會
			do{
				hit=dealer.hit_me(this); //this
				if(hit){
					dealerCard.add(deck.getOneCard(true));
					dealer.setOneRoundCard(dealerCard);
					System.out.print("Hit! ");
					System.out.println("Dealer's Cards now:");
					for(Card c : dealerCard){
						c.printCard();
					}
				}//莊家要牌
				else{
					System.out.println("Dealer Pass hit!");
					System.out.println("Dealer's Final Card:");
					for(Card c : dealerCard){
						c.printCard();
					}
					System.out.println();
				}//莊家不要牌
			}while(hit);
		}
		else {
			System.out.println("Dealer isBomb!");
			System.out.println("Dealer's Final Card:");
			for(Card c : dealerCard){
				c.printCard();
			}
			System.out.println();
		}//莊家點數超過21點就顯示爆掉
		System.out.println("Dealer's hit is over!");
	}
	private void calculate_chips() {
		System.out.print("Dealer's card value is "+dealer.getTotalValue()+" , Cards:\n");
		dealer.printAllCard();
		//計算各家的目前點數,進行輸贏判斷
		for(int i=0;i<allPlayer.length;i++) {
			System.out.print(allPlayer[i].getName()+"'s card value is "+allPlayer[i].getTotalValue()+", Cards:\n");
			allPlayer[i].printAllCard();
			if(dealer.getTotalValue()>21) {
				System.out.println("Dealer is Bomb!");
				if(allPlayer[i].getTotalValue()<=21){
					allPlayer[i].increaseChips(pos_betArray[i]);
					System.out.println(allPlayer[i].getName()+" Get "+pos_betArray[i]+" Chips, the Chips now is: "+allPlayer[i].getCurrentChips()+"\n");
				}//玩家小於21點且莊家爆
				else if(allPlayer[i].getTotalValue()>21) {
					System.out.println(allPlayer[i].getName()+" is Bomb!");
					System.out.println(",chips have no change! The Chips now is: "+allPlayer[i].getCurrentChips()+"\n");
				}//玩家大於21點且莊家爆
			}
			else if(dealer.getTotalValue()<=21) {
				if(allPlayer[i].getTotalValue()<=21){
					if(allPlayer[i].getTotalValue()>dealer.getTotalValue()) {
						allPlayer[i].increaseChips(pos_betArray[i]);
						System.out.println(allPlayer[i].getName()+" Get "+pos_betArray[i]+" Chips, the Chips now is: "+allPlayer[i].getCurrentChips()+"\n");
					}//玩家點數大於莊家即贏
					else if(allPlayer[i].getTotalValue()<dealer.getTotalValue()) {
						allPlayer[i].increaseChips(-pos_betArray[i]);
						System.out.println(allPlayer[i].getName()+" Loss "+pos_betArray[i]+" Chips, the Chips now is: "+allPlayer[i].getCurrentChips()+"\n");
					}//玩家點數小於莊家即輸
					else if(allPlayer[i].getTotalValue()==dealer.getTotalValue()) {
						System.out.println(",chips have no change! The Chips now is: "+allPlayer[i].getCurrentChips()+"\n");
					}//玩家點數等於莊家即平手
				}
				else if(allPlayer[i].getTotalValue()>21) {
					System.out.println(allPlayer[i].getName()+" is Bomb!");
					allPlayer[i].increaseChips(-pos_betArray[i]);
					System.out.println(allPlayer[i].getName()+" Loss "+pos_betArray[i]+" Chips, the Chips now is: "+allPlayer[i].getCurrentChips()+"\n");
				}//玩家大於21點即輸
			}
		}
	}
	public int[] get_players_bet() {
		return pos_betArray;
	}
	public void play() {
		ask_each_player_about_bets();
		distribute_cards_to_dealer_and_players();
		ask_each_player_about_hits();
		ask_dealer_about_hits();
		calculate_chips();
	}
}
