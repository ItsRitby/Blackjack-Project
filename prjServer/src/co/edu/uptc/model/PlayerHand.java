package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerHand {
    private List<Card> playerCards;
    private List<Card> playerSecondHand;
    private boolean isSplit;
    private boolean blackJack;

    public PlayerHand() {
        playerCards = new ArrayList<>();
        playerSecondHand = new ArrayList<>();
        isSplit = false;
        blackJack = false;
    }

    // recibe una carta y verifica si la mano sigue siendo valida
    public boolean addCard(Card card) {
        playerCards.add(card);
        return isValidHand(playerCards);
    }

    public boolean addCardToSecondHand(Card card) {
        if (!isSplit)
            return false;
        playerSecondHand.add(card);
        return isValidHand(playerSecondHand);
    }

    public void splitHand(Card card1, Card card2) {
        if (!isSpliteable())
            return;
        playerSecondHand.add(playerCards.remove(1));
        playerCards.add(card1);
        playerSecondHand.add(card2);
        isSplit = true;
    }

    public boolean isSpliteable() {
        return playerCards.size() == 2 && equalsCards() && !isSplit;
    }

    private boolean equalsCards() {
        return playerCards.get(0).getRank() == playerCards.get(1).getRank();
    }

    public void checkBlackJack() {
        if (playerCards.size() == 2 && getHandValue(playerCards) == 21) {
            blackJack = true;
        }
    }

    public int getHandValue(List<Card> hand) {
        int sum = 0, numberOfAs = 0;
        for (Card card : hand) {
            sum += card.getValue();
            if (card.getRank() == 'A')
                numberOfAs++;
        }
        while (sum > 21 && numberOfAs > 0) {
            sum -= 10;
            numberOfAs--;
        }
        return sum;
    }

    public int[] handsValue() {
        if (isSplit) {
            return new int[] { getHandValue(playerCards), getHandValue(playerSecondHand) };
        }
        return new int[] { getHandValue(playerCards) };
    }

    public void clearHand() {
        playerCards.clear();
        playerSecondHand.clear();
        blackJack = false;
        isSplit = false;
    }

    private boolean isValidHand(List<Card> hand) {
        return getHandValue(hand) <= 21;
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public List<Card> getPlayerSecondHand() {
        return isSplit ? playerSecondHand : null;
    }

    public boolean isSplit() {
        return isSplit;
    }

    public boolean isBlackJack() {
        return blackJack;
    }

    @Override
    public String toString() {
        return "PlayerHand Cards" + playerCards + " SecondHand=" + playerSecondHand + "]";
    }

}
