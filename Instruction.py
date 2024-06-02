class Instruction:
    def __init__(self, opcode, *operands):
        self.opcode = opcode
        self.op1 = operands[0] if len(operands) > 0 else None
        self.op2 = operands[1] if len(operands) > 1 else None
        self.op3 = operands[2] if len(operands) > 2 else None
        self.validate = True  # Assume the instruction is valid initially
        
    def __str__(self):
        return f"{self.opcode} {self.op1} {self.op2} {self.op3}"