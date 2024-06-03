### Requirements:

* [X] Read from the program memory (.txt with instructions to a vector with the instructions)
* [ ] Simulate the pipeline with the 5 stages
  * [X] fetch
  * [X] decode
  * [X] execute
  * [X] memory
  * [ ] write back
* [X] Registers from R0 to R31 (R0 fixed in zero)

### Supported Instructions:
- ADD
- ADDI
- SUB
- SUBI
- BEQ
- J

#### MIPS Instructions


| Assembly | Instruction Opcode | Action                                                                                                                                                                  |
| :--------: | :------------------: | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
|   addi   |       0b000       | Add contents of`regA` with contents of `regB`, store results in `destReg`.                                                                                              |
|   noop   |       0b111       | “No Operation (pronounced no op)” Do nothing.                                                                                                                         |
|   loop   |                   |                                                                                                                                                                         |
|   done   |                   |                                                                                                                                                                         |
|   beq   |       0b100       | “Branch if equal” If the contents of`regA` and `regB` are the same, then branch to the address `PC+1+offsetField`, where `PC` is the address of this beq instruction. |
|   halt   |       0b110       | Increment the`PC` (as with all instructions), then halt the machine (let the simulator notice that the machine halted).                                                 |

### Suggested struct of instructions
```
Struct {
    Opcode
    Op1
    Op2
    Op3
    Temp1
    Temp2
    Temp3
    Valid
}
```
