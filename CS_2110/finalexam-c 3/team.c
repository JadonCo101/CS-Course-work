/**
 * CS 2110 - Fall 2022
 * Final Exam - Implement Team
 *
 * Name:
 */

/**
 * The following structs are defined in team.h. You will need to use these to store your stack's data:
 *
 * struct player {
 *   char *name;     // pointer to player's name
 *   int age;        // the player's age
 *   int number;     // the player's jersey number
 * };
 *
 * struct roster {
 *   int size; // current number of players on the roster
 *   int capacity; // the max number of players the roster can currently hold
 *   struct player **players; // pointer to element 0 of array of pointers to players
 * };
 * 
 * IMPORTANT: string.h has been included so you are encouraged to use any string functions (strlen, strncpy, etc.) that you may need.
 */

// DO NOT MODIFY THE INCLUDE(s) LIST
#include <stdio.h>
#include "team.h"

/** createPlayer
 *
 * Creates a player struct by allocating memory for it on the heap.
 * Take all of the passed in arguments and store them in the created player struct.
 * 
 * ------------------------------------------------------------
 * Here is the struct definition of a player for reference:
 * struct player {
 *   char *name;     // pointer to player's name
 *   int age;        // the player's age
 *   int number;     // the player's jersey number
 * };
 * ------------------------------------------------------------
 * 
 * If this function succeeds, it should return SUCCESS and modify dataOut to
 * hold the newly created player. If it fails, it should return FAILURE
 * and leave dataOut unchanged.
 * 
 * @param name The name of the player
 * @param age The age of the player
 * @param number The number of the player
 * @param dataOut A pointer to pointer to return the created player
 * @return FAILURE if any of the following are true:
 *         (1) name is NULL
 *         (2) age is negative
 *         (3) number is negative
 *         (4) dataOut is NULL
 *         (5) a malloc failure occurs
 *         Otherwise, return SUCCESS
 */
int createPlayer(const char *name, int age, int number, struct player **dataOut) {
  if (name == NULL || age < 0 || number < 0 || dataOut == NULL) {
    return FAILURE;
  }
  struct player *pPTR = malloc(sizeof(struct player));
  if (pPTR == NULL) {
    free(pPTR);
    return FAILURE;
  }

  char *namePTR = malloc(strlen(name) + 1);
  if (namePTR == NULL) {
    free(pPTR);
    free(namePTR);
    return FAILURE;
  }

  strcpy(namePTR, name);
  *(namePTR + strlen(name)) = (char) 0;
  pPTR->name = namePTR;
  
  pPTR->age = age;
  pPTR->number = number;
  
  *dataOut = pPTR;


  return SUCCESS;
}

/** addToRoster
 * 
 * Add a player to the players array in the roster struct. You may assume that if 
 * the player struct passed in is not NULL, then it has already been allocated on the heap. 
 * 
 * ------------------------------------------------------------
 * Here is the struct definition of a roster for reference:
 * struct roster {
 *   int size; // current number of players on the roster
 *   int capacity; // the max number of players the roster can currently hold
 *   struct player **players; // pointer to element 0 of array of pointers to players
 * };
 * ------------------------------------------------------------
 * 
 * The maximum capacity/size for the array is MAX_ROSTER_SIZE, which can be found in team.h
 * 
 * If the array is not filled (i.e. when the size less than the capacity), then add the player to the end of the array.
 * If the array is filled, then do one of the following:
 *    (1) Resize the array and double its capacity
 *    (2) Resize the array to MAX_ROSTER_SIZE if doubling the capacity causes it to exceed MAX_ROSTER_SIZE
 *    (3) Don't resize the array if the capacity is already at MAX_ROSTER_SIZE
 * 
 * 
 * @param roster a pointer to the roster struct
 * @param newPlayer a pointer to a previously allocated player struct
 * @return FAILURE if any of the following are true:
 *         (1) roster or its player array are null
 *         (2) Either the size or capacity is negative or is greater than or equal to MAX_ROSTER_SIZE
 *         (3) newPlayer is NULL
 *         (4) a malloc or realloc failure occurs
 *         Otherwise, return SUCCESS
 */
int addToRoster(struct roster *roster, struct player *newPlayer) {
  
  if (roster == NULL || newPlayer == NULL || roster->size < 0 || roster->size > MAX_ROSTER_SIZE || roster->capacity < 0 || roster->capacity > MAX_ROSTER_SIZE) {
    return FAILURE;
  }
  if (newPlayer == NULL) {
    return FAILURE;
  }
  if (roster->size == MAX_ROSTER_SIZE && roster->capacity == MAX_ROSTER_SIZE) {
    return FAILURE;
  }
  if (roster->size > roster->capacity) {
    return FAILURE;
  }

  if (roster->size < roster->capacity) {
    (roster->players)[roster->size] = newPlayer;
    roster->size++;
    return SUCCESS;
  }

  

  if (((roster->capacity) * 2) > MAX_ROSTER_SIZE) {
    struct player **playerArray = realloc(roster->players, MAX_ROSTER_SIZE);
    if (playerArray == NULL) {
      free(playerArray);
      return FAILURE;
    }
    
    roster->players = playerArray;
    (roster->players)[roster->size] = newPlayer;
    roster->capacity = MAX_ROSTER_SIZE;
    roster->size++;
    //free(playerArray);
    return SUCCESS;
  }

   struct player **playerArray = realloc(roster->players, (roster->capacity * 2));
   if (playerArray == NULL) {
    free(playerArray);
    return FAILURE;
   }
   roster->players = playerArray;
   (roster->players)[roster->size] = newPlayer;
   roster->size++;
   roster->capacity *= 2;
   //free(playerArray);
   return SUCCESS;


}
