import java.util.ArrayList;

public class Player extends Person{
	private String name; //玩家姓名
	private int chips; //玩家有的籌碼
	private int bet; //預計下注金額
	public Player(String name, int chips) {
		this.name=name;
		this.chips=chips;
	}
	public String getName() {
		return name;
	}
	public int makeBet() {
		if(chips!=0) {
			bet=1; //基本暫定1元
			//System.out.println("您這回合下注了"+bet+"元");
			return bet;
		}
		else {
			//System.out.println("您已沒有足夠的籌碼能夠下注了~");
			return 0;
		}
	}

	public int getCurrentChips() {
		return chips;
	}
	public void increaseChips (int diff) {
		chips=chips+diff;
	}
	public void sayHello() {
		System.out.println("Hello, I am " + name + ".");
		System.out.println("I have " + chips + " chips.");
	}
	@Override
	public boolean hit_me(Table tbl) {
		int total_value = getTotalValue();
		if (total_value < 17)
			return true;
		else if (total_value == 17 && hasAce()) {
			return true;
		} else {
			if (total_value >= 21)
				return false;
			else {
				Player[] players = tbl.get_player();
				int lose_count = 0;
				int v_count = 0;
				int[] betArray = tbl.get_players_bet();
				for (int i = 0; i < players.length; i++) {
					if (players[i] == null) {
						continue;
					}
					if (players[i].getTotalValue() != 0) {
						if (total_value < players[i].getTotalValue()) {
							lose_count += betArray[i];
						} else if (total_value > players[i].getTotalValue()) {
							v_count += betArray[i];
						}
					}
				}
				if (v_count < lose_count)
					return true;
				else
					return false;
			}
		}
	} 
}
