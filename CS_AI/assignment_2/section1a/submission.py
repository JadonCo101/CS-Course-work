
#################################################
### THIS FILE WAS AUTOGENERATED! DO NOT EDIT! ###
#################################################
# file to edit: notebook.ipynb

import time
from isolation import Board

# Credits if any
# 1)
# 2)
# 3)

class OpenMoveEvalFn:
    def score(self, game, my_player=None):
        """Score the current game state
        Evaluation function that outputs a score equal to how many
        moves are open for AI player on the board minus how many moves
        are open for Opponent's player on the board.

        Note:
            If you think of better evaluation function, do it in CustomEvalFn below.

            Args
                game (Board): The board and game state.
                my_player (Player object): This specifies which player you are.

            Returns:
                float: The current state's score. MyMoves-OppMoves.

            """

        # TODO: finish this function!

        score = len(game.get_player_moves(my_player)) - len(game.get_opponent_moves(my_player))

        return score




######################################################################
########## DON'T WRITE ANY CODE OUTSIDE THE FUNCTION! ################
######## IF YOU WANT TO CALL OR TEST IT CREATE A NEW CELL ############
######################################################################
##### CODE BELOW IS USED FOR RUNNING LOCAL TEST DON'T MODIFY IT ######
################ END OF LOCAL TEST CODE SECTION ######################

class CustomPlayer:
    # TODO: finish this class!
    """Player that chooses a move using your evaluation function
    and a minimax algorithm with alpha-beta pruning.
    You must finish and test this player to make sure it properly
    uses minimax and alpha-beta to return a good move."""

    def __init__(self, search_depth=2, eval_fn=OpenMoveEvalFn()):
        """Initializes your player.

        if you find yourself with a superior eval function, update the default
        value of `eval_fn` to `CustomEvalFn()`

        Args:
            search_depth (int): The depth to which your agent will search
            eval_fn (function): Evaluation function used by your agent
        """
        self.eval_fn = eval_fn
        self.search_depth = search_depth

    def move(self, game, time_left):
        """Called to determine one move by your agent


        Note:
            1. Do NOT change the name of this 'move' function. We are going to call
            this function directly.
            2. Call alphabeta instead of minimax once implemented.
        Args:
            game (Board): The board and game state.
            time_left (function): Used to determine time left before timeout

        Returns:
            tuple: (int,int): Your best move
        """
        best_move, utility = alphabeta(self, game, time_left, depth=self.search_depth)
        return best_move

    def utility(self, game, my_turn):
        """You can handle special cases here (e.g. endgame)"""
        return self.eval_fn.score(game, self)

###################################################################
########## DON'T WRITE ANY CODE OUTSIDE THE CLASS! ################
###### IF YOU WANT TO CALL OR TEST IT CREATE A NEW CELL ###########
###################################################################


def minimax(player, game, time_left, depth, my_turn=True):
    """Implementation of the minimax algorithm.
    Args:
        player (CustomPlayer): This is the instantiation of CustomPlayer()
            that represents your agent. It is used to call anything you
            need from the CustomPlayer class (the utility() method, for example,
            or any class variables that belong to CustomPlayer()).
        game (Board): A board and game state.
        time_left (function): Used to determine time left before timeout
        depth: Used to track how deep you are in the search tree
        my_turn (bool): True if you are computing scores during your turn.

    Returns:
        (tuple, int): best_move, val
    """
    if my_turn == False:
        answer = minVal(player, game, my_turn, depth)
    else:
        answer = maxVal(player, game, my_turn, depth)
    move, value = answer[0], answer[1]


    return (move, value)

    # TODO: finish this function!

def maxVal(player, game, my_turn, depth):
    if ((not game.get_active_moves()) or depth == 0):
        return (None, (player.utility(game, my_turn)))
    v = float('-inf')
    for element in (game.get_active_moves()):
        pMove = (game.forecast_move(element))
        temp = minVal(player, pMove[0], pMove[1], (depth-1))
        v_two = temp[1]
        a_two = temp[0]
        if (v_two > v):
            v = v_two
            move = element

    return (move, v)

def minVal(player, game, my_turn, depth):
    if ((not game.get_active_moves()) or depth == 0):
        return (None, (player.utility(game, my_turn)))
    v = float('inf')
    for element in (game.get_opponent_moves(player)):
        pMove = (game.forecast_move(element))
        temp = maxVal(player, pMove[0], pMove[1], (depth-1))
        v_two = temp[1]
        a_two = temp[0]
        if (v_two < v):
            v = v_two
            move = element

    return (move, v)

######################################################################
########## DON'T WRITE ANY CODE OUTSIDE THE FUNCTION! ################
######## IF YOU WANT TO CALL OR TEST IT CREATE A NEW CELL ############
######################################################################
##### CODE BELOW IS USED FOR RUNNING LOCAL TEST DON'T MODIFY IT ######
################ END OF LOCAL TEST CODE SECTION ######################

def alphabeta(player, game, time_left, depth, alpha=float("-inf"), beta=float("inf"), my_turn=True):
    """Implementation of the alphabeta algorithm.

    Args:
        player (CustomPlayer): This is the instantiation of CustomPlayer()
            that represents your agent. It is used to call anything you need
            from the CustomPlayer class (the utility() method, for example,
            or any class variables that belong to CustomPlayer())
        game (Board): A board and game state.
        time_left (function): Used to determine time left before timeout
        depth: Used to track how deep you are in the search tree
        alpha (float): Alpha value for pruning
        beta (float): Beta value for pruning
        my_turn (bool): True if you are computing scores during your turn.

    Returns:
        (tuple, int): best_move, val
    """
    if my_turn == False:
        answer = minVals(player, game, my_turn, depth, alpha, beta)
    else:
        answer = maxVals(player, game, my_turn, depth, alpha, beta)
    move, value = answer[0], answer[1]

    print(move, value)
    return (move, value)


    # TODO: finish this function!


######################################################################
########## DON'T WRITE ANY CODE OUTSIDE THE FUNCTION! ################
######## IF YOU WANT TO CALL OR TEST IT CREATE A NEW CELL ############
######################################################################
##### CODE BELOW IS USED FOR RUNNING LOCAL TEST DON'T MODIFY IT ######
# tests.name_of_the_test #you can uncomment this line to run your test
################ END OF LOCAL TEST CODE SECTION ######################

class CustomEvalFn:
    def __init__(self):
        pass

    def score(self, game, my_player=None):
        """Score the current game state.

        Custom evaluation function that acts however you think it should. This
        is not required but highly encouraged if you want to build the best
        AI possible.

        Args:
            game (Board): The board and game state.
            my_player (Player object): This specifies which player you are.

        Returns:
            float: The current state's score, based on your own heuristic.
        """

        # TODO: finish this function!
        raise NotImplementedError

######################################################################
############ DON'T WRITE ANY CODE OUTSIDE THE CLASS! #################
######## IF YOU WANT TO CALL OR TEST IT CREATE A NEW CELL ############
######################################################################