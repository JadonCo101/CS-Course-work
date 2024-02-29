;;=============================================================
;;  CS 2110 - Fall 2022
;;  Homework 6 - Selection Sort
;;=============================================================
;;  Name: Jadon Co
;;============================================================

;;  In this file, you must implement the 'SELECTION_SORT' subroutine.

;;  Little reminder from your friendly neighborhood 2110 TA staff: don't run
;;  this directly by pressing 'Run' in complx, since there is nothing put at
;;  address x3000. Instead, call the subroutine by doing the following steps:
;;      * 'Debug' -> 'Simulate Subroutine Call'
;;      * Call the subroutine at the 'SELECTION_SORT' label
;;      * Add the [arr (addr), length] params separated by a comma (,) 
;;        (e.g. x4000, 5)
;;      * Proceed to run, step, add breakpoints, etc.
;;      * SELECTION_SORT is an in-place algorithm, so if you go to the address
;;        of the array by going to 'View' -> 'Goto Address' -> 'Address of
;;        the Array', you should see the array (at x4000) successfully 
;;        sorted after running the program (e.g [2,3,1,1,6] -> [1,1,2,3,6])

;;  If you would like to setup a replay string (trace an autograder error),
;;  go to 'Test' -> 'Setup Replay String' -> paste the string (everything
;;  between the apostrophes (')) excluding the initial " b' ". If you are 
;;  using the Docker container, you may need to use the clipboard (found
;;  on the left panel) first to transfer your local copied string over.

.orig x3000
    ;; You do not need to write anything here
    HALT

;;  SELECTION_SORT Pseudocode (see PDF for explanation and examples)
;; 
;;  SELECTION_SORT(int[] arr (addr), int length) {
;;      if (length <= 1) {
;;          return;
;;      }
;;      int largest = 0;
;;      for (int i = 1; i < length; i++) {
;;          if (arr[i] > arr[largest]) {
;;              largest = i;
;;          }
;;      }
;;      int temp = arr[length - 1];
;;      arr[length - 1] = arr[largest];
;;      arr[largest] = temp;
;;
;;      SELECTION_SORT(arr, length - 1);
;;      return;
;;  }

SELECTION_SORT ;; Do not change this label! Treat this as like the name of the function in a function header


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
        LDR R3, R5, 5   ;; length in R3
        AND R0, R0, 0   ;; largest = 0 (R0)
    ;; DONE

    ;;      if (length <= 1) {
    ;;          return;
    ;;      }
        AND R1, R1, 0   ;; clears R1
        ADD R1, R3, -1  ;; length - 1 = R1
        BRnz IF_ELSE_END


        AND R2, R2, 0   ;; int i = 0
        ADD R2, R2, 1   ;; int i = 1


        FOR NOP
        AND R4, R4, 0   ;; clears R4
        NOT R4, R3      ;; R4 = ~length
        ADD R4, R4, 1   ;; R4 = -length
        ADD R4, R4, R2  ;; R4 = -length + i
        BRzp END_FOR
        LDR R4, R5, 4   ;; array address in R4 (has a[0])
        ADD R4, R4, R2   ;; array address in R4 (has a[i])
        LDR R1, R4, 0    ;; R1 has a[i] value

        LDR R4, R5, 4   ;; array address in R4 (has a[0])
        ADD R4, R4, R0   ;; array address in R4 (has a[largest])
        LDR R4, R4, 0    ;; a[largest] in R4
        NOT R1, R1       ;; R1 = ~a[i]
        ADD R1, R1, 1    ;; R1 = -a[i]
        ADD R1, R1, R4   ;; R1 = a[largest] - a[i]
        BRzp SEC_IF
        AND R0, R0, 0    ;; clears R0
        ADD R0, R0, R2   ;; largest = i
        SEC_IF NOP
        ADD R2, R2, 1    ;; x++
        BR FOR




        END_FOR NOP
        ADD R3, R3, -1  ;; R3 = length - 1
        LDR R4, R5, 4   ;; array address in R4 (has a[0])
        ADD R4, R4, R3  ;; array address in R4 (has a[length-1])
        LDR R4, R4, 0   ;; stores a[length - 1] value
        AND R2, R2, 0   ;; clears R2
        ADD R2, R2, R4  ;; temp = a[length-1]

        LDR R4, R5, 4   ;; array address in R4 (has a[0])
        AND R1, R1, 0   ;; clears R1
        ADD R1, R0, R4  ;; R1 = address of a[largest]
        LDR R1, R1, 0   ;; a[largest] value in R1
        ADD R4, R4, R3  ;; array address in R4 (has a[length-1])
        STR R1, R4, 0   ;; a[length-1] = a[largest]

        LDR R1, R5, 4   ;; array address in R1 (has a[0])
        AND R4, R4, 0   ;; clears R4
        ADD R1, R1, R0  ;; array address in R1 (has a[largest])
        STR R2, R1, 0   ;; a[largest] = temp


        ADD R6, R6, -2  ;; moves FP
        LDR R2, R5, 4   ;; loads array address into R2
        STR R2, R6, 0   ;; pushes array address parameter onto the stack
        STR R3, R6, 1   ;; pushes length - 1 onto the stack
        JSR SELECTION_SORT
        ADD R6, R6, 3     ;; pops (arguments and RV)


        IF_ELSE_END NOP



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

;; Needed to Simulate Subroutine Call in Complx
STACK .fill xF000
.end

.orig x4000	;; Array : You can change these values for debugging!
    .fill 2
    .fill 3
    .fill 1
    .fill 1
    .fill 6
.end