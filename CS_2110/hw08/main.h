#ifndef MAIN_H
#define MAIN_H

#include "gba.h"

// TODO: Create any necessary structs

/*
* For example, for a Snake game, one could be:
*
* struct snake {
*   int heading;
*   int length;
*   int row;
*   int col;
* };
*
* Example of a struct to hold state machine data:
*
* struct state {
*   int currentState;
*   int nextState;
* };
*
*/
struct player {
        int row;
        int col;
        int width;
        int height;
        int old_row;
        int old_col;
        int score;
        unsigned short color;
    };

    
  struct enemy {
        int row;
        int col;
        int width;
        int height;
        int x_speed;
        int y_speed;
        int last_row;
        int last_col;
        unsigned short color;
  };
//state screens or helper functions
void startScreen(void);
void instructions(void);
void playScreen(void);
void winScreen(void);
void loseScreen(void);



#endif

