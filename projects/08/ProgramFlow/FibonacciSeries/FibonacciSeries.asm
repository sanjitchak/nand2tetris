//push arg
@ARG
D=M
D=D+1
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
//pop pointer 1
@SP
M=M-1
A=M
D=M
@THAT
M=D
//push const
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop that
@SP
M=M-1
A=M
D=M
@THAT
A=M
M=D
//push const
@1
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop that
@SP
M=M-1
A=M
D=M
@THAT
A=M
A=A+1
M=D
//push arg
@ARG
D=M
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
//push const
@2
D=A
@SP
A=M
M=D
@SP
M=M+1
//sub
@SP
M=M-1
A=M
A=A-1
D=M
A=A+1
M=D-M
D=M
A=A-1
M=D
//pop arg
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D
//label 
(MAIN_LOOP_START)
//push arg
@ARG
D=M
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
//label jump 
@SP
A=M
A=A-1
D=M
@SP
M=M-1
@COMPUTE_ELEMENT
D;JGT
//label jump compulsory 
@END_PROGRAM
0;JMP
//label 
(COMPUTE_ELEMENT)
//push that
@THAT
D=M
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
//push that
@THAT
D=M
D=D+1
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
//add
@SP
M=M-1
A=M
D=M
A=A-1
M=D+M
//pop that
@SP
M=M-1
A=M
D=M
@THAT
A=M
A=A+1
A=A+1
M=D
//push pointer 1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
//push const
@1
D=A
@SP
A=M
M=D
@SP
M=M+1
//add
@SP
M=M-1
A=M
D=M
A=A-1
M=D+M
//pop pointer 1
@SP
M=M-1
A=M
D=M
@THAT
M=D
//push arg
@ARG
D=M
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
//push const
@1
D=A
@SP
A=M
M=D
@SP
M=M+1
//sub
@SP
M=M-1
A=M
A=A-1
D=M
A=A+1
M=D-M
D=M
A=A-1
M=D
//pop arg
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D
//label jump compulsory 
@MAIN_LOOP_START
0;JMP
//label 
(END_PROGRAM)
