import java.util.ArrayList;

public class Player extends Person{
	private String name; //���a�m�W
	private int chips; //���a�����w�X
	private int bet; //�w�p�U�`���B
	public Player(String name, int chips) {
		this.name=name;
		this.chips=chips;
	}
	public String getName() {
		return name;
	}
	public int makeBet() {
		if(chips!=0) {
			bet=1; //�򥻼ȩw1��
			//System.out.println("�z�o�^�X�U�`�F"+bet+"��");
			return bet;
		}
		else {
			//System.out.println("�z�w�S���������w�X����U�`�F~");
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
