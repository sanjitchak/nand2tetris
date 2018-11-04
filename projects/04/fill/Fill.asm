// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
@SCREEN
D=A


@totalnumber
M=D

@8191
D=A

@totalnumber
M=M+D

(LOOP)

@KBD
D=M

@KEYPRESS
D;JNE 

@LOOP
0;JMP

(LOOPFROMBLACK)

@SCREEN
M=0

D=A //storing address
@storeaddr
M=D //storing address to variable
(LOOPWHITE)


@storeaddr //retreiving address
D=M

D=D+1 //going to next screen register
A=D
M=0
@storeaddr
M=D
@totalnumber
D=D-M //checking if address > total
@LOOPWHITE
D;JLE


@LOOP //back to key detection
0;JMP

(KEYPRESS)

@SCREEN
M=-1
D=A //storing address
@storeaddr
M=D //storing address to variable

(LOOPBLACK)
@storeaddr //retreiving address
D=M

D=D+1 //going to next screen register
A=D
M=-1
@storeaddr
M=D
@totalnumber
D=D-M //checking if address > total

@LOOPBLACK
D;JLE

(KEYAGAIN)
@KBD
D=M

@KEYAGAIN
D;JNE

@LOOPFROMBLACK
0;JMP





