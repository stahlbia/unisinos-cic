from Instruction import Instruction

class PipelineSimulator:
    def __init__(self, program_file):
        self.instructions = self.import_program_file(program_file)
        self.labels = self.find_labels(program_file) # Save label references on the code
        self.memory_data = self.load_memory(program_file) # Simulate the data in Memory
        self.pc = 0  # Program counter
        self.totalRuns = 0 # Count how many runs were make
        self.fetchedStep = None  # Instruction fetched
        self.decodedStep = None  # Instruction decoded
        self.executedStep = None  # Instruction executed
        self.memoryStep = None  # Instruction memory accessed
        self.wb_value = None # Write-back value
        self.registers = {f'{i}': 0 for i in range(32)}  # Simulate the register file with 32 registers

    def import_program_file(self, entire_file):
        instructions_list = []
        lines = entire_file.split("\n")
        
        for line in lines:
            instruction_parts = line.split()
            if instruction_parts:
                if instruction_parts[0] == "loop:":
                    opcode = instruction_parts[1]
                    operands = instruction_parts[2:]
                elif instruction_parts[0].endswith(":"):
                    continue
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
        
        return memory

    def fetch(self):
        if self.pc < len(self.instructions):
            self.fetchedStep = self.instructions[self.pc]
            self.pc += 1
        else:
            self.fetchedStep = None

    def decode(self):
        if self.fetchedStep:
            self.decodedStep = self.fetchedStep
            if self.decodedStep.opcode in ["add", "addi", "beq", "noop", "halt", "lw"]:
                self.decodedStep.validate = True
            else:
                self.decodedStep.validate = False
        else:
            self.decodedStep = None
    
    def execute(self):
        if self.decodedStep and self.decodedStep.validate:
            if self.decodedStep.opcode == "add":
                self.wb_value = (self.decodedStep.op1, self.registers[self.decodedStep.op2] + self.registers[self.decodedStep.op3])
            elif self.decodedStep.opcode == "beq":
                if self.registers[self.decodedStep.op1] == self.registers[self.decodedStep.op2]:
                    self.pc = self.labels[self.decodedStep.op3] # TODO
            elif self.decodedStep.opcode == "halt":
                self.pc = len(self.instructions)  # Force end of instructions
            self.executedStep = self.decodedStep
        else:
            self.executedStep = None
    
    def memory_access(self):
        if self.executedStep and self.executedStep.validate:
            self.memoryStep = self.executedStep
        else:
            self.memoryStep = None

    def write_back(self):
        if self.memoryStep and self.memoryStep.validate:
            if self.wb_value and self.memoryStep.opcode in ["add", "addi"]:
                self.registers[self.wb_value[0]] = self.wb_value[1]
                self.wb_value = None
            if self.memoryStep.opcode in ["lw"]:
                label = self.memoryStep.op3
                key = self.labels[label]
                self.registers[self.memoryStep.op2] = self.memory_data[key]

    def print_pipeline_state(self):
        print(f"\n\033[1m--- Pipeline State | PC: {self.pc} | Total Runs: {self.totalRuns} ---\033[0m")
        print(f"IF/ID: {self.fetchedStep}")
        print(f"ID/EX: {self.decodedStep}")
        print(f"EX/ME: {self.executedStep}")
        print(f"ME/WB: {self.memoryStep}")
        print(f"Registers: {self.registers}")
        print(f"Memories: {self.memory_data}")

    def run(self):
        while self.pc < len(self.instructions) or any([self.fetchedStep, self.decodedStep, self.executedStep, self.memoryStep]):
            input("\n\033[32mPress Enter to continue...\033[0m")
            self.write_back()
            self.memory_access()
            self.execute()
            self.decode()
            self.fetch()
            self.totalRuns += 1
            self.print_pipeline_state()
