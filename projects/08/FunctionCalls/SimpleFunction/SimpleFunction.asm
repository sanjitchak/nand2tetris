//function type
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
//push local
@LCL
D=M
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
//push local
@LCL
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
//not
@SP
A=M
A=A-1
M=!M
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
//add
@SP
M=M-1
A=M
D=M
A=A-1
M=D+M
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
//return type
@LCL
D=M
@frame
M=D
A=M
A=A-1
A=A-1
A=A-1
A=A-1
A=A-1
D=M
@retaddr
M=D
@SP
A=M
A=A-1
D=M
@ARG
A=M
M=D
A=A+1
D=A
@SP
M=D
@frame
A=M
A=A-1
D=M
@THAT
M=D
@frame
A=M
A=A-1
A=A-1
D=M
@THIS
M=D
@frame
A=M
A=A-1
A=A-1
A=A-1
D=M
@ARG
M=D
@frame
A=M
A=A-1
A=A-1
A=A-1
A=A-1
D=M
@LCL
M=D
@retaddr
A=M
0;JMP