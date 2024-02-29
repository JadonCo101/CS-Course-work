;;=============================================================
;;  CS 2110 - Fall 2022
;;  Homework 6 - Factorial
;;=============================================================
;;  Name: Jadon Co
;;============================================================

;;  In this file, you must implement the 'MULTIPLY' and 'FACTORIAL' subroutines.

;;  Little reminder from your friendly neighborhood 2110 TA staff: don't run
;;  this directly by pressing 'Run' in complx, since there is nothing put at
;;  address x3000. Instead, call the subroutine by doing the following steps:
;;      * 'Debug' -> 'Simulate Subroutine Call'
;;      * Call the subroutine at the 'MULTIPLY' or 'FACTORIAL' labels
;;      * Add the [a, b] or [n] params separated by a comma (,) 
;;        (e.g. 3, 5 for 'MULTIPLY' or 6 for 'FACTORIAL')
;;      * Proceed to run, step, add breakpoints, etc.
;;      * Remember R6 should point at the return value after a subroutine
;;        returns. So if you run the program and then go to 
;;        'View' -> 'Goto Address' -> 'R6 Value', you should find your result
;;        from the subroutine there (e.g. 3 * 5 = 15 or 6! = 720)

;;  If you would like to setup a replay string (trace an autograder error),
;;  go to 'Test' -> 'Setup Replay String' -> paste the string (everything
;;  between the apostrophes (')) excluding the initial " b' ". If you are 
;;  using the Docker container, you may need to use the clipboard (found
;;  on the left panel) first to transfer your local copied string over.

.orig x3000
    ;; you do not need to write anything here
    HALT

;;  MULTIPLY Pseudocode (see PDF for explanation and examples)   
;;  
;;  MULTIPLY(int a, int b) {
;;      int ret = 0;
;;      while (b > 0) {
;;          ret += a;
;;          b--;
;;      }
;;      return ret;
;;  }

MULTIPLY ;; Do not change this label! Treat this as like the name of the function in a function header

    ;; Building Stack
        ADD R6, R6, -4  ;; save space for return value, old ret address, old frame pointer, and 2 local variables
        STR R7, R6, 2   ;; save RETURN address in memory
        STR R5, R6, 1   ;; save Old Frame Pointer (FP) in memory
        ADD R5, R6, 0   ;; Sets FP = SP
        ADD R6, R6, -5  ;; Make room for saved registers (R0-R4)
        STR R0, R5, -1  ;; Save old R0
        STR R1, R5, -2  ;; Save old R1
        STR R2, R5, -3  ;; Save old R2
        STR R3, R5, -4  ;; Save old R3
        STR R4, R5, -5  ;; Save old R4
    ;; DONE



    ;; int ret = 0
    AND R0, R0, 0  ;; clears R0 (int ret = 0)
    LDR R1, R5, 4  ;; loads ret into R1 (a)
    LDR R2, R5, 5  ;; loads x into R2 (b)

;; IMPLEMENTATION
    ;; while (b > 0) {
    W1 NOP
    ADD R2, R2, 0
    BRnz WHILE_END

    ;; ret += a
    ADD R0, R0, R1

    ;; b--
    ADD R2,R2, -1
    BR W1

    WHILE_END NOP

;;DONE

    ;; Tear Down
        STR R0, R5, 3   ;; stores in return value
        LDR R4, R5, -5  ;; restores R4
        LDR R3, R5, -4  ;; restores R3
        LDR R2, R5, -3  ;; restores R2
        LDR R1, R5, -2  ;; restores R1
        LDR R0, R5, -1  ;; restores R0
        ADD R6, R5, 0   ;; restores SP (R6)
        LDR R7, R5, 2   ;; restores RA (R7)
        LDR R5, R5, 1   ;; restores FP (R5)
        ADD R6, R6, #3   ;; Pop Old FP, RA, local variable

    ;; DONE


    RET

;;  FACTORIAL Pseudocode (see PDF for explanation and examples)
;;
;;  FACTORIAL(int n) {
;;      int ret = 1;
;;      for (int x = 2; x <= n; x++) {
;;          ret = MULTIPLY(ret, x);
;;      }
;;      return ret;
;;  }

FACTORIAL ;; Do not change this label! Treat this as like the name of the function in a function header

    ;; Building Stack
        ADD R6, R6, -4  ;; save space for return value, old ret address, old frame pointer, and 2 local variables
        STR R7, R6, 2   ;; save RETURN address in memory
        STR R5, R6, 1   ;; save Old Frame Pointer (FP) in memory
        ADD R5, R6, 0   ;; Sets FP = SP
        ADD R6, R6, -5  ;; Make room for saved registers (R0-R4)
        STR R0, R5, -1  ;; Save old R0
        STR R1, R5, -2  ;; Save old R1
        STR R2, R5, -3  ;; Save old R2
        STR R3, R5, -4  ;; Save old R3
        STR R4, R5, -5  ;; Save old R4
    ;; DONE

    ;; Setting Local Variables
        AND R0, R0, 0   ;; clears R0
        ADD R0, R0, 1   ;; ret = 1 (R0)
        AND R1, R1, 0   ;; clears R1
        ADD R1, R1, 2   ;; x = 2 (R1)
        LDR R2, R5, 4   ;; n --> R2
    ;; DONE


    ;; Implementation of the Factorial Subroutine
        FOR NOP
        AND R3, R3, 0    ;; clears R3
        NOT R3, R1       ;; R3 = ~x
        ADD R3, R3, 1    ;; makes R3 = -x
        ADD R3, R2, R3   ;; n + -x
        BRn END_FOR

        ADD R6, R6, -2  ;; moves FP
        STR R1, R6, 1   ;; pushes x parameter onto the stack
        STR R0, R6, 0   ;; pushes ret parameter onto the stack

        JSR MULTIPLY

        LDR R0, R6, 0     ;; ret = load RV (R0)
        ADD R6, R6, 3     ;; pops (arguments and RV)
        ADD R1, R1, 1     ;; x++
        BR FOR
        END_FOR NOP

    ;; DONE

    ;; Tear Down
        STR R0, R5, 3   ;; stores in return value
        LDR R4, R5, -5  ;; restores R4
        LDR R3, R5, -4  ;; restores R3
        LDR R2, R5, -3  ;; restores R2
        LDR R1, R5, -2  ;; restores R1
        LDR R0, R5, -1  ;; restores R0
        ADD R6, R5, 0   ;; restores SP (R6)
        LDR R7, R5, 2   ;; restores RA (R7)
        LDR R5, R5, 1   ;; restores FP (R5)
        ADD R6, R6, 3   ;; Pop Old FP, RA, local variable
    ;; DONE



    RET

;; Needed to Simulate Subroutine Call in Complx
STACK .fill xF000
.end