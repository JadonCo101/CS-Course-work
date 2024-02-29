;;=============================================================
;; CS 2110 - Fall 2022
;; Homework 5 - fiveCharacterStrings
;;=============================================================
;; Name: Jadon Co
;;=============================================================

;; 	Pseudocode (see PDF for explanation)
;;
;; 	int count = 0; (keep count of number of 5-letter words)
;; 	int chars = 0; (keep track of length of each word)
;; 	int i = 0; (indexer into each word)
;; 	String str = "I enjoy CS 2110 and assembly makes me smile! "; (sample string)
;;  while(str[i] != '\0') {
;;		if (str[i] != ' ')  {
;;			chars++;
;;		}
;;		else {
;;			if (chars == 5) {
;;				count++;   
;			}
;;			chars = 0;
;;		}
;;		i++;
;;	}
;;	mem[ANSWER] = count;
;;
;; ***IMPORTANT***
;; - Assume that all strings provided will end with a space (' ').
;; - Special characters do not have to be treated differently. For instance, strings like "who's" and "Wait," are considered 5 character strings.

.orig x3000
;; int count = 0 (keep count of number of 5-letter words)
;;int chars = 0 (keep track of length of each word)

    AND R0, R0, #0 ;; int count = 0 (R0 = count)
    AND R1, R1, #0 ;; int chars = 0 (R1 = chars)

;; int i = 0 (indexer into each word)
;; String str = "I really love CS 2110 and using wires and code!" (sample string)

    LD R2, STRING   ;; int index
    LDR R3, R2, #0  ;; R3 = str[i]
    LD R5, SPACE    ;; R5 = -32

;; while (str[i] != ‘\0’) {

W1  ADD R3, R3, #0
    BRz END_WHILE

;; else {
;;        if (chars == 5) {
;;            count++
;; }
    AND R4, R4, #0
    ADD R4, R3, R5
    BRnp IF_STATE
    ADD R1, R1, #-5
    BRz SEC_IF
    AND R1, R1, #0
    BR CONT_WHILE

    SEC_IF NOP
    ADD R0, R0, #1
    BR CONT_WHILE

;;    if (str[i] != ‘ ’) {
;;       chars++
;; }
    IF_STATE  ADD R1, R1, #1
    CONT_WHILE NOP
    ADD R2, R2, #1
    LDR R3, R2, #0
    BR W1

    END_WHILE NOP
    ST R0, ANSWER
	HALT

;; DO NOT CHANGE THESE VALUES
SPACE 	.fill #-32
STRING	.fill x4000
ANSWER 	.blkw 1
.end

.orig x4000				;; DO NOT CHANGE THE .orig STATEMENT
	.stringz "I enjoy CS 2110 and assembly makes me smile! " ;; You can change this string for debugging!
.end