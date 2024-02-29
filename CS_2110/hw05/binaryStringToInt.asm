;;=============================================================
;; CS 2110 - Fall 2022
;; Homework 5 - binaryStringToInt
;;=============================================================
;; Name: Jadon Co
;;=============================================================

;;  Pseudocode (see PDF for explanation)
;;
;;  String binaryString = "00010101"; (sample binary string)
;;  int length = 8; (sample length of the above binary string)
;;  int base = 1;
;;  int value = 0;
;;  int i = length - 1;
;;  while (i >= 0) {
;;      int x = binaryString[i] - 48;
;;      if (x == 1) {
;;          if (i == 0) {
;;              value -= base;
;;          } else {
;;              value += base;
;;          }
;;      }     
;;      base += base;
;;      i--;
;;  }
;;  mem[mem[RESULTIDX]] = value;

.orig x3000
    LD R1, LENGTH         ;; the index = length
    ADD R1, R1, #-1       ;; the index (R1) = length - 1
    AND R4, R4, 0         ;; int value = 0 (R4)
    AND R5, R5, 0         ;; int base = 0 (R5)
    ADD R5, R5, 1         ;; int base = 1 (R5)


W1
    ADD R1, R1, 0
    BRn END_WHILE

    LD R3, BINARYSTRING
    ADD R3, R3, R1
    LDR R0, R3, 0         ;; last number in bit string, 2^0
    LD R2, ASCII          ;; (R2) x = -48
    ADD R2, R2, R0        ;; binaryString[i] - 48
    AND R3, R3, 0         ;; clear R3
    ADD R3, R3, #-1       ;; R3 = -1
    ADD R3, R3, R2        ;; if x - 1 = 0
    BRz IF_STATE
    BRnp CONT_WHILE



IF_STATE ADD R1, R1, 0
         BRz SEC_IF
         ADD R4, R4, R5
         BR CONT_WHILE

SEC_IF  NOT R6, R5
        ADD R6, R6, #1
        ADD R4, R4, R6


CONT_WHILE

        ADD R5, R5, R5
        ADD R1, R1, #-1
        BR W1

END_WHILE NOP
    STI R4, RESULTIDX


    HALT
    
;; DO NOT CHANGE THESE VALUES
ASCII           .fill -48
BINARYSTRING    .fill x5000 ;; x5000
                            ;; x5001
                            ;; x5002
                            ;; x5003
                            ;; x5004
                            ;; x5005
                            ;; x5006
                            ;; x5007
LENGTH          .fill 8     ;; x5008
RESULTIDX       .fill x4000 ;; x5009
.end

.orig x5000                    ;;  DO NOT CHANGE THE .orig STATEMENT
    .stringz "00010101"        ;; You can change this string for debugging!
.end
