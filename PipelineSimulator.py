from Instruction import Instruction

class PipelineSimulator:
    def __init__(self, program_file):
        self.instructions = self.import_program_file(program_file)
        self.labels = self.find_labels(program_file)
        self.memory_data = self.load_memory(program_file)

    def import_program_file(self, entire_file):
        instructions_list = []
        lines = entire_file.split("\n")
        
        for line in lines:
            instruction_parts = line.split()
            if instruction_parts:
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
