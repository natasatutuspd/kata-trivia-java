package trivia;

import java.util.ArrayList;
import java.util.LinkedList;

// REFACTOR ME
public class GameBetter implements IGame {
   ArrayList players = new ArrayList();
   int[] places = new int[6];
   int[] purses = new int[6];
   boolean[] inPenaltyBox = new boolean[6];

   LinkedList popQuestions = new LinkedList();
   LinkedList scienceQuestions = new LinkedList();
   LinkedList sportsQuestions = new LinkedList();
   LinkedList rockQuestions = new LinkedList();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public GameBetter() {
      for (int i = 0; i < 50; i++) {
         fillQuestionsLists(i);
      }
   }

   private void fillQuestionsLists(int i){
      popQuestions.addLast(createQuestion(i, "Pop Question "));
      scienceQuestions.addLast(createQuestion(i, "Science Question "));
      sportsQuestions.addLast(createQuestion(i, "Sports Question "));
      rockQuestions.addLast(createQuestion(i, "Rock Question "));
   }

   private String createQuestion(int index, String message){
      return message + index;
   }
//   private String createPopQuestion(int index) {
//      return "Pop Question " + index;
//   }
//   private String createScienceQuestion(int index) {
//      return "Science Question " + index;
//   }
//   private String createSportsQuestion(int index) {
//      return "Sports Question " + index;
//   }
//   private String createRockQuestion(int index) {
//      return "Rock Question " + index;
//   }

   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {
      players.add(playerName);
      places[howManyPlayers()] = 0;
      purses[howManyPlayers()] = 0;
      inPenaltyBox[howManyPlayers()] = false;

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(players.get(currentPlayer) + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (inPenaltyBox[currentPlayer]) {
         tryToGoOutOfPenaltyBox(roll);
      } else {
         getMove(roll);
      }
   }

   private void tryToGoOutOfPenaltyBox(int roll){
      if (roll % 2 != 0) {
         isGettingOutOfPenaltyBox = true;
         System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
         getMove(roll);
      } else {
         System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
         isGettingOutOfPenaltyBox = false;
      }
   }

   private void getMove(int roll){

      places[currentPlayer] = places[currentPlayer] + roll;
      if (places[currentPlayer] > 11) {
         places[currentPlayer] = places[currentPlayer] - 12;
      }
      showPlayersLocationAndCategoryMessage();
      askQuestion();
   }

   private void showPlayersLocationAndCategoryMessage(){
      System.out.println(players.get(currentPlayer)
              + "'s new location is "
              + places[currentPlayer]);
      System.out.println("The category is " + currentCategory());
   }

   private void askQuestion() {
      if (currentCategory().equals("Pop"))
         System.out.println(popQuestions.removeFirst());
      if (currentCategory().equals("Science"))
         System.out.println(scienceQuestions.removeFirst());
      if (currentCategory().equals("Sports"))
         System.out.println(sportsQuestions.removeFirst());
      if (currentCategory().equals("Rock"))
         System.out.println(rockQuestions.removeFirst());
   }


   private String currentCategory() {
      if (places[currentPlayer] == 0) return "Pop";
      if (places[currentPlayer] == 4) return "Pop";
      if (places[currentPlayer] == 8) return "Pop";
      if (places[currentPlayer] == 1) return "Science";
      if (places[currentPlayer] == 5) return "Science";
      if (places[currentPlayer] == 9) return "Science";
      if (places[currentPlayer] == 2) return "Sports";
      if (places[currentPlayer] == 6) return "Sports";
      if (places[currentPlayer] == 10) return "Sports";
      return "Rock";
   }

   public boolean wasCorrectlyAnswered() {
      if (inPenaltyBox[currentPlayer]) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            purses[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
                               + " now has "
                               + purses[currentPlayer]
                               + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
         } else {
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;
            return true;
         }


      } else {

         System.out.println("Answer was corrent!!!!");
         purses[currentPlayer]++;
         System.out.println(players.get(currentPlayer)
                            + " now has "
                            + purses[currentPlayer]
                            + " Gold Coins.");

         boolean winner = didPlayerWin();
         currentPlayer++;
         if (currentPlayer == players.size()) currentPlayer = 0;

         return winner;
      }
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;

      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
      return true;
   }


   private boolean didPlayerWin() {
      return !(purses[currentPlayer] == 6);
   }
}
