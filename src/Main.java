import java.util.*;

class Card{
    static ArrayList<String> suits = new ArrayList<String>(Arrays.asList("S", "C", "D", "H"));//ПКБЧ
    static ArrayList<String> values = new ArrayList<String>(Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "D", "K", "A"));
    private String value;
    private String suit; //HSCD
    Card(String value, String suit){
        this.value = value;
        this.suit = suit;
    }

    public void setValue(String value) {this.value = value;}
    public String getValue() {return value;}
    public int getValueInt() {return values.indexOf(value);}
    public void setSuit(String suit) {this.suit = suit;}
    public String getSuit() {return suit;}
    public int getSuitInt() {return suits.indexOf(suit);}

    @Override
    public int hashCode() {
        return (values.indexOf(value)+2)*100 + suits.indexOf(suit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(value, card.value) && Objects.equals(suit, card.suit);
    }
}
class Hand {
    Card[] cards;
    Hand(String sCards){
        cards = new Card[5];
        int i=0;
        for(String s:sCards.trim().split(" ")){
            int len = s.length();
            cards[i] = new Card(s.substring(0, len-1), s.substring(len-1));
            i++;
        }
    }
    private void change(int i, int j){
        String vl = cards[i].getValue();
        String st = cards[i].getSuit();
        cards[i].setValue(cards[j].getValue());
        cards[i].setSuit(cards[j].getSuit());
        cards[j].setValue(vl);
        cards[j].setSuit(st);
    }
    public void sort(){
        for (int i=0;i<4;i++) {
            for (int j = 0; j < 4-i; j++) {
                if (cards[j].getValueInt()>cards[j+1].getValueInt()){
                    change(j,j+1);
                }
            }
        }
    }
    public void showCards(){
        System.out.print("Cards: ");
        for (Card card:cards){
            System.out.print(card.getValue()+card.getSuit()+" ");
        }
        System.out.println("");
    }
    public boolean isRoyalFlush(){
        int st = cards[0].getSuitInt();
        int i=Card.values.indexOf("10");
        for(Card card:cards){
            if (card.getSuitInt()!=st) return false;
            if (card.getValueInt()!=i++) return false;
        }
        return true;
    };
    public boolean isStraightFlush(){
        int st = cards[0].getSuitInt();
        int i=cards[0].getValueInt();
        for(Card card:cards){
            if (card.getSuitInt()!=st) return false;
            if (card.getValueInt()!=i++) return false;
        }
        return true;
    };
    public boolean isCare(){
        int cnt = 0;
        if (cards[0].getValueInt()==cards[1].getValueInt() &&
                cards[0].getValueInt()==cards[2].getValueInt() &&
                cards[0].getValueInt()==cards[3].getValueInt()) return true;
        if (cards[1].getValueInt()==cards[2].getValueInt() &&
                cards[1].getValueInt()==cards[3].getValueInt() &&
                cards[1].getValueInt()==cards[4].getValueInt()) return true;
        return false;
    };
    public boolean isFullHouse(){
        if (cards[0].getValueInt()==cards[1].getValueInt() &&
                cards[1].getValueInt()==cards[2].getValueInt() &&
                cards[3].getValueInt()==cards[4].getValueInt()) return true;
        if (cards[0].getValueInt()==cards[1].getValueInt() &&
                cards[2].getValueInt()==cards[3].getValueInt() &&
                cards[3].getValueInt()==cards[4].getValueInt()) return true;
        return false;
    };
    public boolean isFlush(){
        int st = cards[0].getSuitInt();
        for(Card card:cards){
            if (card.getSuitInt()!=st) return false;
        }
        return true;
    };
    public boolean isStraight(){
        int i=cards[0].getValueInt();
        for(Card card:cards){
            if (card.getValueInt()!=i++) return false;
        }
        return true;
    };
    public boolean isThree(){
        int cnt = 0;
        for(int i=0;i<3;i++){
            if (cards[i].getValueInt()==cards[i+1].getValueInt() &&
                    cards[i].getValueInt()==cards[i+2].getValueInt()) return true;
        }
        return false;
    };
    public boolean isTwoPairs(){
        int cnt = 0;
        int prevValue=-1;
        for(int i=0;i<4;i++){
            if (cards[i].getValueInt()==cards[i+1].getValueInt() &&
            cards[i].getValueInt()!=prevValue) {
                cnt++;
                prevValue=cards[i].getValueInt();
            }
        }
        if (cnt<2) return false;
        return true;
    };
    public boolean isPair(){
        int cnt = 0;
        for(int i=0;i<4;i++){
            if (cards[i].getValueInt()==cards[i+1].getValueInt()) cnt++;
        }
        if (cnt==0) return false;
        return true;
    };
    public boolean isHighCard(){
        return true;
    };

    public String whatKind(){
        if (isRoyalFlush()) return "Royal Flush";
        if (isStraightFlush()) return "Straight Flush";
        if (isCare()) return "Four of a Kind";
        if (isFullHouse()) return "Full House";
        if (isFlush()) return "Flush";
        if (isStraight()) return "Straight";
        if (isThree()) return "Three of a Kind";
        if (isTwoPairs()) return "Two Pairs";
        if (isPair()) return "One Pair";
        if (isHighCard()) return "High Card";
        return "Unknown";
    }
}
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
       String sCards = sc.nextLine();
        Hand hand = new Hand(sCards);
        hand.sort();
        System.out.println(hand.whatKind());
    }
}
