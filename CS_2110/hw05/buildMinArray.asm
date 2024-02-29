;;=============================================================
;; CS 2110 - Fall 2022
;; Homework 5 - buildMinArray
;;=============================================================
;; Name: Jadon Co
;;=============================================================

;; 	Pseudocode (see PDF for explanation)
;;
;;	int A[] = {-4, 2, 6}; (sample array)
;;	int B[] = {4, 7, -2}; (sample array)
;;	int C[3]; (sample array)
;;	int length = 3; (sample length of above arrays)
;;
;;	int i = 0;
;;	while (i < length) {
;;		if (A[i] < B[i]) {
;;			C[i] = A[i];
;;		}
;;		else {
;;			C[i] = B[i];
;;		}
;;		i++;
;;	}

.orig x3000
    AND R0, R0, 0 ;; index = 0
    LD R7, LENGTH

W1
    AND R1, R1, 0
    NOT R1, R0
    ADD R1, R1, #1
    AND R2, R2, 0
    ADD R2, R7, R1
    BRnz W_END

    ;; obtaining A value
    LD R3, A
    ADD R3, R3, R0
    LDR R3, R3, 0 ;; value of A array

    ;; obtaining B value
    LD R4, B
    ADD R4, R4, R0
    LDR R4, R4, 0  ;; value of B array

    ;; determining min value
    AND R5, R5, 0
    NOT R5, R4
    ADD R5, R5, #1 ;; flipped signs of B for subtraction
    LD R1, C
    AND R2, R2, 0
    ADD R2, R0, R1
    ADD R6, R3, R5
    BRzp C1
    BRn C2

C1  STR R4, R2, 0
    ADD R0, R0, #1
    BR W1

C2  STR R3, R2, 0
    ADD R0, R0, #1
    BR W1


W_END NOP
	HALT

A 		.fill x3200		;; DO NOT CHANGE
B 		.fill x3300		;; DO NOT CHANGE
C 		.fill x3400		;; DO NOT CHANGE
LENGTH 	.fill 3			;; You can change this value if you increase the size of the arrays below
.end

.orig x3200				;; Array A : You can change these values for debugging! DO NOT CHANGE THE .orig STATEMENT
	.fill -4  ;; x3200
	.fill 2   ;; x3201
	.fill 6   ;; x3202
.end

.orig x3300				;; Array B : You can change these values for debugging! DO NOT CHANGE THE .orig STATEMENT
	.fill 4  ;; x3300
	.fill 7  ;; x3301
	.fill -2  ;; x3302
.end

.orig x3400				;; DO NOT CHANGE THE .orig STATEMENT
	.blkw 3				;; Array C: Make sure to increase block size if you add more values to Arrays A and B!
.end