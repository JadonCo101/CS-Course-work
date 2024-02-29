;;=============================================================
;;  CS 2110 - Fall 2022
;;  Homework 6 - Binary Search
;;=============================================================
;;  Name: Jadon Co
;;============================================================

;;  In this file, you must implement the 'BINARY_SEARCH' subroutine.

;;  Little reminder from your friendly neighborhood 2110 TA staff: don't run
;;  this directly by pressing 'Run' in complx, since there is nothing put at
;;  address x3000. Instead, call the subroutine by doing the following steps:
;;      * 'Debug' -> 'Simulate Subroutine Call'
;;      * Call the subroutine at the 'BINARY_SEARCH' label
;;      * Add the [root (addr), data] params separated by a comma (,) 
;;        (e.g. x4000, 8)
;;      * Proceed to run, step, add breakpoints, etc.
;;      * Remember R6 should point at the return value after a subroutine
;;        returns. So if you run the program and then go to 
;;        'View' -> 'Goto Address' -> 'R6 Value', you should find your result
;;        from the subroutine there (e.g. Node 8 is found at x4008)

;;  If you would like to setup a replay string (trace an autograder error),
;;  go to 'Test' -> 'Setup Replay String' -> paste the string (everything
;;  between the apostrophes (')) excluding the initial " b' ". If you are 
;;  using the Docker container, you may need to use the clipboard (found
;;  on the left panel) first to transfer your local copied string over.

.orig x3000
    ;; You do not need to write anything here
    HALT

;;  BINARY_SEARCH Pseudocode (see PDF for explanation and examples)
;;  - Nodes are blocks of size 3 in memory:
;;      * The data is located in the 1st memory location (offset 0 from the node itself)
;;      * The node's left child address is located in the 2nd memory location (offset 1 from the node itself)
;;      * The node's right child address is located in the 3rd memory location (offset 2 from the node itself)

;;  BINARY_SEARCH(Node root (addr), int data) {
;;      if (root == 0) {
;;          return 0;
;;      }
;;      if (data == root.data) {
;;          return root;
;;      }
;;      if (data < root.data) {
;;          return BINARY_SEARCH(root.left, data);
;;      }
;;      return BINARY_SEARCH(root.right, data);
;;  }

BINARY_SEARCH ;; Do not change this label! Treat this as like the name of the function in a function header

   ;; Build Up
       ADD R6, R6, #-4  ;; save space for old ret address, old frame pointer, and 2 local variables
       STR R7, R6, #2   ;; save RETURN address in memory
       STR R5, R6, #1   ;; save Old Frame Pointer (FP) in memory
       ADD R5, R6, #0   ;; Sets FP = SP
       ADD R6, R6, #-5  ;; Make room for saved registers (R0-R4)

       STR R0, R5, #-1   ;; Save old R0
       STR R1, R5, #-2  ;; Save old R1
       STR R2, R5, #-3  ;; Save old R2
       STR R3, R5, #-4  ;; Save old R3
       STR R4, R5, #-5  ;; Save old R4


   ;;      if (root == 0) {
   ;;          return 0;
   ;;      }
   ;;      if (data == root.data) {
   ;;          return root;
   ;;      }
   ;;      if (data < root.data) {
   ;;          return BINARY_SEARCH(root.left, data);
   ;;      }
   ;;      return BINARY_SEARCH(root.right, data);
   ;;  }

       LDR R0, R5, 4   ;; loads root (address) into R0
       BRz END
       LDR R1, R0, 0   ;; loads data of root into R1
       LDR R2, R5, 5   ;; loads data parameter into R2
       AND R3, R3, 0   ;; clears R2
       NOT R3, R1      ;; R3 = ~(data of root)
       ADD R3, R3, 1   ;; R3 = -(data of root)
       AND R4, R4, 0   ;; clears R4
       ADD R4, R2, R3  ;; data (parameter) - (data of root)
       BRz END
       BRn LEFT_COND
       BRp RIGHT_COND


       RIGHT_COND NOP
       ADD R6, R6, -2  ;; moves FP
       LDR R4, R0, 2   ;; loads root.left address into R4
       STR R2, R6, 1   ;; pushes data parameter onto the stack
       STR R4, R6, 0   ;; pushes left address onto the stack
       JSR BINARY_SEARCH
       LDR R0, R6, 0     ;; ret = load RV (R0)
       ADD R6, R6, 3     ;; pops (arguments and RV)
       BR END

       LEFT_COND NOP
       ADD R6, R6, -2  ;; moves FP
       LDR R4, R0, 1   ;; loads root.left address into R4
       STR R2, R6, 1   ;; pushes data parameter onto the stack
       STR R4, R6, 0   ;; pushes left address onto the stack
       JSR BINARY_SEARCH
       LDR R0, R6, 0     ;; ret = load RV (R0)
       ADD R6, R6, 3     ;; pops (arguments and RV)


       END NOP

   ;; Tear Down
       STR R0, R5, #3   ;; stores in return value
       LDR R4, R5, #-5  ;; restores R4
       LDR R3, R5, #-4  ;; restores R3
       LDR R2, R5, #-3  ;; restores R2
       LDR R1, R5, #-2  ;; restores R1
       LDR R0, R5, #-1  ;; restores R0
       ADD R6, R5, #0   ;; restores SP (R6)
       LDR R7, R5, #2   ;; restores RA (R7)
       LDR R5, R5, #1   ;; restores FP (R5)

       ADD R6, R6, #3   ;; Pop Old FP, RA, local variable


       ;; LDR R0 ,R6, #0   ;; loads return value into R0
       ;; ADD R6, R6, #3   ;; pops return value & arguments off stack

       RET



;; Needed to Simulate Subroutine Call in Complx
STACK .fill xF000
.end

;;  Assuming the tree starts at address x4000, here's how the tree (see below and in the PDF) is represented in memory
;;
;;              4
;;            /   \
;;           2     8 
;;         /   \
;;        1     3 

.orig x4000 ;; 4    ;; node itself lives here at x4000
    .fill 4         ;; node.data (4)
    .fill x4004     ;; node.left lives at address x4004
    .fill x4008     ;; node.right lives at address x4008
.end

.orig x4004	;; 2    ;; node itself lives here at x4004
    .fill 2         ;; node.data (2)
    .fill x400C     ;; node.left lives at address x400C
    .fill x4010     ;; node.right lives at address x4010
.end

.orig x4008	;; 8    ;; node itself lives here at x4008
    .fill 8         ;; node.data (8)
    .fill 0         ;; node does not have a left child
    .fill 0         ;; node does not have a right child
.end

.orig x400C	;; 1    ;; node itself lives here at x400C
    .fill 1         ;; node.data (1)
    .fill 0         ;; node does not have a left child
    .fill 0         ;; node does not have a right child
.end

.orig x4010	;; 3    ;; node itself lives here at x4010
    .fill 3         ;; node.data (3)
    .fill 0         ;; node does not have a left child
    .fill 0         ;; node does not have a right child
.end

;;  Another way of looking at how this all looks like in memory
;;              4
;;            /   \
;;           2     8 
;;         /   \
;;        1     3 
;;  Memory Address           Data
;;  x4000                    4          (data)
;;  x4001                    x4004      (4.left's address)
;;  x4002                    x4008      (4.right's address)
;;  x4003                    Don't Know
;;  x4004                    2          (data)
;;  x4005                    x400C      (2.left's address)
;;  x4006                    x4010      (2.right's address)
;;  x4007                    Don't Know
;;  x4008                    8          (data)
;;  x4009                    0(NULL)
;;  x400A                    0(NULL)
;;  x400B                    Don't Know
;;  x400C                    1          (data)
;;  x400D                    0(NULL)
;;  x400E                    0(NULL)
;;  x400F                    Dont't Know
;;  x4010                    3          (data)
;;  x4011                    0(NULL)
;;  x4012                    0(NULL)
;;  x4013                    Don't Know
;;  
;;  *Note: 0 is equivalent to NULL in assembly