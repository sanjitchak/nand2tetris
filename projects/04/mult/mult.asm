// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.
@R0
D=M
@n //no of times to add
M=D

@R2
M=0

@R1
D=M
@sum //total sum
M=0

@const
M=D

@i //no of loop left
M=1

(LOOP)
@n
D=M

@i
D=M-D

@END
D;JGT

@const
D=M

@sum
M=M+D



@i
M=M+1

@LOOP
0;JMP

(END)
@sum
D=M

@R2
M=D

(END2)
@END2

0;JMP


