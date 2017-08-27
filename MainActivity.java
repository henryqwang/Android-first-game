package com.example.android.connect3;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //gameState is an array telling whether a cell has been played by a player (0 or 1), or is still unplayed (2)
    //The index of the array corresponds to a cell on the board, with 0 starting from the top left cell, going from left to right and top to down, with 8 being the bottom right cell
    int[] gameState = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};

    //Winning positions record all possible winning configurations on the checkboard
    final int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    final int kim = 1;
    final int henry = 0;
    int activePlayer = kim;

    //Disables game once someone has won
    boolean gameIsActive = true;

    public void dropIn(View view) {

        ImageView counter = (ImageView) view;

        //Tag of cell (0 - 8) will be returned upon tap
        //The tag is then checked against gameState, updating the game only if the cell is unplayed
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameIsActive) { //If cell is unplayed and game isn't over, do:

                counter.setTranslationY(-1000f);
                if (activePlayer == kim) {
                    counter.setImageResource(R.drawable.kimpiece);
                    activePlayer = henry;
                } else {
                    counter.setImageResource(R.drawable.henrypiece);
                    activePlayer = kim;
                }
                gameState[tappedCounter] = activePlayer;
                counter.animate().translationYBy(1000f).rotation(360).setDuration(200);

                //Check for winning condition
                for (int[] winningPosition : winningPositions) {

                    if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {

                        //Suspend game mechanics
                        gameIsActive = false;

                        //Determine who on and display winner image
                        String winner = "Kim";

                        ImageView winnerImage = (ImageView) findViewById(R.id.winnerImage);
                        winnerImage.setImageResource(R.drawable.kimwon);

                        if (gameState[winningPosition[0]] != 0) {
                            winner = "Henry";
                            winnerImage.setImageResource(R.drawable.henrywon);
                        }

                        //Display winner message
                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                        winnerMessage.setText(winner + " has won!");

                        //Display button whose onClick allows user to restart the game
                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);

                    } else {
                        boolean gameIsOver = true;
                        for (int eachCounterState : gameState) {
                            if (eachCounterState == 2) {
                                gameIsOver = false;
                            }
                        }
                        if (gameIsOver) {

                            //Display info
                            TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                            winnerMessage.setText("It's a draw...");

                            //Display image
                            ImageView winnerImage = (ImageView) findViewById(R.id.winnerImage);
                            winnerImage.setImageResource(R.drawable.draw);

                            //Display button whose onClick allows user to restart the game
                            LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                            layout.setVisibility(View.VISIBLE);

                            gameIsActive = false;
                        }
                    }
                }

        }
    }

    public void playAgain(View view){
        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        //Resume game mechanics
        gameIsActive = true;
        activePlayer = kim;

        //Reset gameState array
        for(int i = 0; i< gameState.length; i++){
            gameState[i] = 2;
        }

        //Reset all children of the grid layout (all of them are ImageView) to empty images
        GridLayout gridlayout = (GridLayout)findViewById(R.id.myGrid);
        for(int i = 0; i<gridlayout.getChildCount(); i++){
            ((ImageView)gridlayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


}
