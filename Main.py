from PipelineSimulator import PipelineSimulator
from TextFileReader import TextFileReader


def main(): 
    content = TextFileReader()
    content.read_file("program.txt")
    program_content = content.get_content()

    pipeline = PipelineSimulator(program_content)

main()