import java.util.ArrayList;

public class Table {
	private int nDeck;
	private Deck deck; //�Ҧ����P
	private Player[] allPlayer; //�Ҧ����a
	private Dealer dealer; //���a
	private int[] pos_betArray=new int[MAXPLAYER]; //���a�C�@���ҤU���`
	private ArrayList<ArrayList<Card>> playerCards = new ArrayList<ArrayList<Card>>();
	private ArrayList<Card> player1Card = new ArrayList<Card>();
	private ArrayList<Card> player2Card = new ArrayList<Card>();
	private ArrayList<Card> player3Card = new ArrayList<Card>();
	private ArrayList<Card> player4Card = new ArrayList<Card>(); //�|�Ӫ��a�ұo�쪺�P
	private ArrayList<Card> dealerCard=new ArrayList<Card>(); //�s��W���|�Ӫ��P��ArrayList

	static final int  MAXPLAYER = 4; //�̤j����H�Ƭ�4�H
	
	public Table(int nDeck) {
		this.nDeck=nDeck;
		this.deck=new Deck(nDeck);
		this.allPlayer=new Player[MAXPLAYER]; //�]�wplayer�H��
		
		playerCards.clear();
		playerCards.add(player1Card);
		playerCards.add(player2Card);
		playerCards.add(player3Card);
		playerCards.add(player4Card);//�N�|��player���P���s�J�@��ArrayList�H��K�j�����
	}
	
	public void set_player(int pos,Player p) {
		allPlayer[pos]=p; //�إ�Player
	}
	
	public Player[] get_player() {
		return allPlayer;
	}
	
	public void set_dealer(Dealer d) {
		this.dealer=d; //�إ�Dealer
	}
	public Card get_face_up_card_of_dealer() {
		return dealerCard.get(1); //�^�ǲ��a���쪺�ĤG�i�P(���}���P)
	}
	private void ask_each_player_about_bets() {
		for (int i=0;i<allPlayer.length;i++) {
			allPlayer[i].sayHello(); //���a���۩I
			allPlayer[i].makeBet(); //���a�U�`
			pos_betArray[i]=allPlayer[i].makeBet(); //�U�`���B�Ȧs
		}
	}
	private void distribute_cards_to_dealer_and_players() {
		for(int i=0;i<allPlayer.length;i++) {
			playerCards.get(i).add(deck.getOneCard(true));
			playerCards.get(i).add(deck.getOneCard(true));
			allPlayer[i].setOneRoundCard(playerCards.get(i));
		}//���o�����a��i�\���P
		
		dealerCard.add(deck.getOneCard(false));
		dealerCard.add(deck.getOneCard(true));
		dealer.setOneRoundCard(dealerCard);
		//���o�����a�@�i�\��Τ@�i���}���P
		System.out.print("Dealer's face up card is: ");
		get_face_up_card_of_dealer().printCard();
		System.out.println();
	}
	private void ask_each_player_about_hits() {
		for (int i=0;i<allPlayer.length;i++) {
			if(allPlayer[i].getTotalValue()<=21) {
				//�p�G���a���ثe�I�Ƥp��21�I�~���n�P�����|
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
						}//���a�n�P
					}
					else{
						System.out.println(allPlayer[i].getName()+", Pass hit!");
						System.out.println(allPlayer[i].getName()+", Final Card:");
						for(Card c : playerCards.get(i)){
							c.printCard();
						}
						System.out.println();
					}//���a���n�P
				}while(hit);
			}
			else {
				System.out.println(allPlayer[i].getName()+", is Bomb!");
				System.out.println(allPlayer[i].getName()+", Final Card:");
				for(Card c : playerCards.get(i)){
					c.printCard();
				}
				System.out.println();
			}//���a�I�ƶW�L21�I�N����z��
		}
	}
	private void ask_dealer_about_hits() {
		boolean hit=false;
		if(dealer.getTotalValue()<=21) {
			//�p�G���a�I�Ƥp��21�I�~���n�P�����|
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
				}//���a�n�P
				else{
					System.out.println("Dealer Pass hit!");
					System.out.println("Dealer's Final Card:");
					for(Card c : dealerCard){
						c.printCard();
					}
					System.out.println();
				}//���a���n�P
			}while(hit);
		}
		else {
			System.out.println("Dealer isBomb!");
			System.out.println("Dealer's Final Card:");
			for(Card c : dealerCard){
				c.printCard();
			}
			System.out.println();
		}//���a�I�ƶW�L21�I�N����z��
		System.out.println("Dealer's hit is over!");
	}
	private void calculate_chips() {
		System.out.print("Dealer's card value is "+dealer.getTotalValue()+" , Cards:\n");
		dealer.printAllCard();
		//�p��U�a���ثe�I��,�i���Ĺ�P�_
		for(int i=0;i<allPlayer.length;i++) {
			System.out.print(allPlayer[i].getName()+"'s card value is "+allPlayer[i].getTotalValue()+", Cards:\n");
			allPlayer[i].printAllCard();
			if(dealer.getTotalValue()>21) {
				System.out.println("Dealer is Bomb!");
				if(allPlayer[i].getTotalValue()<=21){
					allPlayer[i].increaseChips(pos_betArray[i]);
					System.out.println(allPlayer[i].getName()+" Get "+pos_betArray[i]+" Chips, the Chips now is: "+allPlayer[i].getCurrentChips()+"\n");
				}//���a�p��21�I�B���a�z
				else if(allPlayer[i].getTotalValue()>21) {
					System.out.println(allPlayer[i].getName()+" is Bomb!");
					System.out.println(",chips have no change! The Chips now is: "+allPlayer[i].getCurrentChips()+"\n");
				}//���a�j��21�I�B���a�z
			}
			else if(dealer.getTotalValue()<=21) {
				if(allPlayer[i].getTotalValue()<=21){
					if(allPlayer[i].getTotalValue()>dealer.getTotalValue()) {
						allPlayer[i].increaseChips(pos_betArray[i]);
						System.out.println(allPlayer[i].getName()+" Get "+pos_betArray[i]+" Chips, the Chips now is: "+allPlayer[i].getCurrentChips()+"\n");
					}//���a�I�Ƥj����a�YĹ
					else if(allPlayer[i].getTotalValue()<dealer.getTotalValue()) {
						allPlayer[i].increaseChips(-pos_betArray[i]);
						System.out.println(allPlayer[i].getName()+" Loss "+pos_betArray[i]+" Chips, the Chips now is: "+allPlayer[i].getCurrentChips()+"\n");
					}//���a�I�Ƥp����a�Y��
					else if(allPlayer[i].getTotalValue()==dealer.getTotalValue()) {
						System.out.println(",chips have no change! The Chips now is: "+allPlayer[i].getCurrentChips()+"\n");
					}//���a�I�Ƶ�����a�Y����
				}
				else if(allPlayer[i].getTotalValue()>21) {
					System.out.println(allPlayer[i].getName()+" is Bomb!");
					allPlayer[i].increaseChips(-pos_betArray[i]);
					System.out.println(allPlayer[i].getName()+" Loss "+pos_betArray[i]+" Chips, the Chips now is: "+allPlayer[i].getCurrentChips()+"\n");
				}//���a�j��21�I�Y��
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
