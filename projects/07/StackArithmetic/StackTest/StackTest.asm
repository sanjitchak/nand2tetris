//push const
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
//push const
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
//eq
@SP
M=M-1
A=M
D=M
A=A-1
M=D-M
D=M
@EQ0
D;JEQ
@SP
A=M
A=A-1
M=0
@EXIT0
0;JMP
(EQ0)
@SP
A=M
A=A-1
M=-1
(EXIT0)
//push const
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
//push const
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
//eq
@SP
M=M-1
A=M
D=M
A=A-1
M=D-M
D=M
@EQ1
D;JEQ
@SP
A=M
A=A-1
M=0
@EXIT1
0;JMP
(EQ1)
@SP
A=M
A=A-1
M=-1
(EXIT1)
//push const
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
//push const
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
//eq
@SP
M=M-1
A=M
D=M
A=A-1
M=D-M
D=M
@EQ2
D;JEQ
@SP
A=M
A=A-1
M=0
@EXIT2
0;JMP
(EQ2)
@SP
A=M
A=A-1
M=-1
(EXIT2)
//push const
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
//push const
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
//lt
@SP
M=M-1
A=M
A=A-1
D=M
A=A+1
M=D-M
D=M
@LT3
D;JLT
@SP
A=M
A=A-1
M=0
@EXIT3
0;JMP
(LT3)
@SP
A=M
A=A-1
M=-1
(EXIT3)
//push const
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
//push const
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
//lt
@SP
M=M-1
A=M
A=A-1
D=M
A=A+1
M=D-M
D=M
@LT4
D;JLT
@SP
A=M
A=A-1
M=0
@EXIT4
0;JMP
(LT4)
@SP
A=M
A=A-1
M=-1
(EXIT4)
//push const
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
//push const
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
//lt
@SP
M=M-1
A=M
A=A-1
D=M
A=A+1
M=D-M
D=M
@LT5
D;JLT
@SP
A=M
A=A-1
M=0
@EXIT5
0;JMP
(LT5)
@SP
A=M
A=A-1
M=-1
(EXIT5)
//push const
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
//push const
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
//gt
@SP
M=M-1
A=M
A=A-1
D=M
A=A+1
M=D-M
D=M
@GT6
D;JGT
@SP
A=M
A=A-1
M=0
@EXIT6
0;JMP
(GT6)
@SP
A=M
A=A-1
M=-1
(EXIT6)
//push const
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
//push const
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
//gt
@SP
M=M-1
A=M
A=A-1
D=M
A=A+1
M=D-M
D=M
@GT7
D;JGT
@SP
A=M
A=A-1
M=0
@EXIT7
0;JMP
(GT7)
@SP
A=M
A=A-1
M=-1
(EXIT7)
//push const
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
//push const
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
//gt
@SP
M=M-1
A=M
A=A-1
D=M
A=A+1
M=D-M
D=M
@GT8
D;JGT
@SP
A=M
A=A-1
M=0
@EXIT8
0;JMP
(GT8)
@SP
A=M
A=A-1
M=-1
(EXIT8)
//push const
@57
D=A
@SP
A=M
M=D
@SP
M=M+1
//push const
@31
D=A
@SP
A=M
M=D
@SP
M=M+1
//push const
@53
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
//push const
@112
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
//neg
@SP
A=M
A=A-1
M=!M
M=M+1
//and
@SP
M=M-1
A=M
A=A-1
D=M
A=A+1
M=D&M
D=M
A=A-1
M=D
//push const
@82
D=A
@SP
A=M
M=D
@SP
M=M+1
//or
@SP
M=M-1
A=M
A=A-1
D=M
A=A+1
M=D|M
D=M
A=A-1
M=D
//not
@SP
A=M
A=A-1
M=!M
