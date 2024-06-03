from Instruction import Instruction

class PipelineSimulator:
    def __init__(self, program_file):
        self.instructions = self.import_program_file(program_file)
        self.labels = self.find_labels(program_file)
        self.memory_data = self.load_memory(program_file)
        self.pc = 0  # Program counter
        self.totalRuns = 0 # Count how many runs were make
        self.fetchStep = None  # Instruction fetched (IF/ID pipeline register)
        self.decodeStep = None  # Instruction decoded (ID/EX pipeline register)
        self.executeStep = None  # Instruction executed (EX/MEM pipeline register)
        self.memoryStep = None  # Instruction memory accessed (MEM/WB pipeline register)
        self.wb_value = None # Write-back result
        self.registers = {f'{i}': 0 for i in range(10)}  # Register file with 32 registers

    def import_program_file(self, entire_file):
        instructions_list = []
        lines = entire_file.split("\n")
        
        for line in lines:
            instruction_parts = line.split()
            if instruction_parts:
                if instruction_parts[0].endswith(":"):
                    opcode = instruction_parts[1]
                    operands = instruction_parts[2:]
                else:
                    opcode = instruction_parts[0]
                    operands = instruction_parts[1:]
                    
                instruction_object = Instruction(opcode, *operands)
                instructions_list.append(instruction_object)
        
        return instructions_list

    def find_labels(self, entire_file):
        labels = {}
        lines = entire_file.split("\n")

        for idx, line in enumerate(lines):
            parts = line.split()
            if parts[0].endswith(":"):
                label = parts[0][:-1]
                labels[label] = idx
        print(labels)
        return labels

    def load_memory(self, entire_file):
        memory = {}
        lines = entire_file.split("\n")

        for idx, line in enumerate(lines):
            parts = line.split()
            if len(parts) >= 2 and parts[1] == ".fill":
                value = parts[2]
                if value.isdigit() or (value[1:].isdigit() and value[0] in ['-', '+']):
                    memory[idx] = int(value)
                elif value in self.labels:
                    memory[idx] = self.labels[value]
                else:
                    memory[idx] = 0  # Default to 0 if not found
        print(memory)
        return memory

    def fetch(self):
        if self.pc < len(self.instructions):
            self.fetchStep = self.instructions[self.pc]
            self.pc += 1
        else:
            self.fetchStep = None

    def decode(self):
        if self.fetchStep:
            self.decodeStep = self.fetchStep
            if self.decodeStep.opcode in ["add", "addi", "beq", "noop", "halt", "lw"]:
                self.decodeStep.validate = True
            else:
                self.decodeStep.validate = False
        else:
            self.decodeStep = None

    def execute(self):
        if self.decodeStep and self.decodeStep.validate:
            if self.decodeStep.opcode == "add":
                self.wb_value = (self.decodeStep.op1, self.registers[self.decodeStep.op2] + self.registers[self.decodeStep.op3])
            elif self.decodeStep.opcode == "beq":
                if self.registers[self.decodeStep.op1] == self.registers[self.decodeStep.op2]:
                    self.pc = self.labels[self.decodeStep.op3] # TODO
            elif self.decodeStep.opcode == "halt":
                self.pc = len(self.instructions)  # Force end of instructions
            self.executeStep = self.decodeStep
        else:
            self.executeStep = None

    def print_pipeline_state(self):
        print(f"\n\033[1m--- Pipeline State | PC: {self.pc} | Total Runs: {self.totalRuns} ---\033[0m")
        print(f"Fetch Step: {self.fetchStep}")
        print(f"Decode Step: {self.decodeStep}")
        print(f"Execution Step: {self.executeStep}")
        print(f"Memory Step: {self.memoryStep}")
        print(f"Registers: {self.registers}")
        print(f"Memories: {self.memory_data}")

    def run(self):
        while self.pc < len(self.instructions) or any([self.fetchStep, self.decodeStep, self.executeStep, self.memoryStep]):
            input("\n\033[32mPress Enter to continue...\033[0m")
            self.execute()
            self.decode()
            self.fetch()
            self.totalRuns += 1
            self.print_pipeline_state()
            self.fetch()
