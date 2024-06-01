from TextFileReader import TextFileReader


def main(): 
    content = TextFileReader()
    content.read_file("program.txt")
    program_content = content.get_content()
    print(program_content)

main()