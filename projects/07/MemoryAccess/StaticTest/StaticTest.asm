//push const
@111
D=A
@SP
A=M
M=D
@SP
M=M+1
//push const
@333
D=A
@SP
A=M
M=D
@SP
M=M+1
//push const
@888
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop static
@SP
M=M-1
A=M
D=M
@StaticTest.8
M=D
//pop static
@SP
M=M-1
A=M
D=M
@StaticTest.3
M=D
//pop static
@SP
M=M-1
A=M
D=M
@StaticTest.1
M=D
//push static
@StaticTest.3
D=M
@SP
A=M
M=D
@SP
M=M+1
//push static
@StaticTest.1
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
//push static
@StaticTest.8
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
