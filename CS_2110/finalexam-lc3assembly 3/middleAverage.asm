;;=======================================
;; CS 2110 - Fall 2022
;; Final Exam - Middle Average
;;=======================================
;; Name:
;;=======================================

;; In this file, you must implement the 'mod' subroutine.

;; Little reminder from your friendly neighborhood 2110 TA staff:
;; don't run this directly by pressing 'RUN' in Complx, since there is nothing
;; put at address x3000. Instead, load it and use 'Debug' -> 'Simulate
;; Subroutine Call' and choose the 'mod' label.

.orig x3000

;; See the PDF for more information on what this subroutine should do.
;;
;; Arguments of middle_average (in order): array's address, length of array (positive integer greater than equal to 5)
;;
;; Pseudocode:
;; arr and len are given!
;; middle_average(arr, len) {
;;     selection_sort(arr, len);
;;     ans = 0;
;;     for (i=2; i< len-2; i++):
;;         ans+=arr[i];
;;     ans = divide(ans, len - 4);
;;     return ans
;; }
;;
MIDDLE_AVERAGE 
    ;STACK BUILDUP, given to students
    ADD     R6, R6, #-4	    ;; Make space for RV, RA, old FP, LV1
    STR     R7, R6, #2 	    ;; Store return address
    STR     R5, R6, #1 	    ;; Store old frame pointer (FP)
    ADD     R5, R6, #0	    ;; Assign new frame pointer (FP)
    ADD     R6, R6, #-5	    ;; Make space for old REGs (assuming only 1 LV)
    STR     R0, R6, #0      ;; Store old R0
    STR     R1, R6, #1      ;; Store old R1
    STR     R2, R6, #2      ;; Store old R2
    STR     R3, R6, #3      ;; Store old R3
    STR     R4, R6, #4      ;; Store old R4
    
    
    LDR R0, R5, #4      ;; loads arr in R0
    LDR R1, R5, #5      ;; loads len in R1
    ADD R6, R6, #-2     ;; makes space for 2 arguments
    STR R0, R6, #0      ;; stores arr in stack
    STR R1, R6, #1      ;; stores len in stack
    JSR SELECTION_SORT  ;; subroutine call

    ADD R6, R6, #2      ;; pops args off stack
    AND R0, R0, #0      ;; clears R0 = ans
    AND R1, R1, #0      ;; clears R1
    ADD R1, R1, #2      ;; R1 = i (2)    
    LDR R2, R5, #5      ;; loads len in R2
    ADD R2, R2, #-2     ;; Len-2 in R2

    FOR_LOOP 
    AND R3, R3, #0      ;; clears R3
    NOT R3, R1          ;; R3 = ~R1 
    ADD R3, R3, #1      ;; R3 = -i
    ADD R3, R3, R2      ;; R3 = len-2-i
    BRnz END_LOOP
    

    LDR R3, R5, #4      ;; loads R3 = arr
    ADD R3, R3, R1     ;; R3 = &arr[i]
    LDR R3, R3, #0      ;; R3 = arr[i]
    ADD R0, R0, R3      ;; R0 = ans + R3
    ADD R1, R1, #1      ;; i++
    BRnzp FOR_LOOP      ;; restart


    END_LOOP 
    LDR R2, R5, #5      ;; loads len in R2
    ADD R2, R2, #-4     ;; len-4 in R2
    ADD R6, R6, #-2     ;; makes space for 2 arguments
    STR R0, R6, #0      ;; stores arr in stack
    STR R2, R6, #1      ;; stores len-4 in stack
    JSR DIVIDE

    LDR R0, R6, #0      ;;R0 = ans = divide(arr,len-4)
    ADD R6, R6, #3      ;; push arguments and return value

    STR R0, R5, #3      ;; stores ans in RV    

    TEARDOWN
    LDR R0, R6, #0      ;; Store old R0
    LDR R1, R6, #1      ;; Store old R1
    LDR R2, R6, #2      ;; Store old R2
    LDR R3, R6, #3      ;; Store old R3
    LDR R4, R6, #4
    ADD R6, R5, #0
    LDR R5, R6, #1
    LDR R7, R6, #2
    ADD R6, R6, #3
    RET
STACK .fill xF000
HALT
.end

.orig x3100

SELECTION_SORT ;; Do not change this label! Treat this as like the name of the function in a function header
    .fill x1DBC
    .fill x7F82
    .fill x7B81
    .fill x1BA0
    .fill x1DBB
    .fill x7180
    .fill x7381
    .fill x7582
    .fill x7783
    .fill x7984
    .fill x5020
    .fill x7140
    .fill x1021
    .fill x54A0
    .fill x14BF
    .fill x6345
    .fill x1481
    .fill x0C24
    .fill x6545
    .fill x94BF
    .fill x14A1
    .fill x1402
    .fill x060D
    .fill x6344
    .fill x1401
    .fill x6480
    .fill x6740
    .fill x1643
    .fill x66C0
    .fill x96FF
    .fill x16E1
    .fill x16C2
    .fill x0C01
    .fill x7140
    .fill x1021 
    .fill x0FEE 
    .fill x6140
    .fill x6344
    .fill x6545
    .fill x14BF
    .fill x1040
    .fill x6600
    .fill x1442
    .fill x6880
    .fill x7680
    .fill x7800
    .fill x6344
    .fill x6545
    .fill x14BF
    .fill x1DBE
    .fill x7380
    .fill x7581
    .fill x4FCB
    .fill x1DA3
    .fill x6180 
    .fill x6381 
    .fill x6582 
    .fill x6783 
    .fill x6984 
    .fill x1D60 
    .fill x6B81 
    .fill x6F82 
    .fill x1DA3 
    .fill xC1C0 

DIVIDE ;DIVIDE(a1, a2) -> returns a1/a2
.fill x1DBC
.fill x7F82
.fill x7B81
.fill x1BA0
.fill x1DBB 
.fill x7180
.fill x7381
.fill x7582 
.fill x7783 
.fill x7984
.fill x6344
.fill x6545
.fill x94BF 
.fill x14A1 
.fill x56E0
.fill x1242
.fill x0802
.fill x16E1
.fill x0FFC
.fill x7743
.fill x6180
.fill x6381 
.fill x6582 
.fill x6783
.fill x6984
.fill x1D60 
.fill x6B81
.fill x6F82
.fill x1DA3
.fill xC1C0
.end


.orig x3500 
.fill 3 
.fill 5
.fill 1
.fill 3
.fill 2
.end