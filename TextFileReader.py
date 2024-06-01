class TextFileReader:
    def __init__(self):
        self.content = None

    def read_file(self, file_path):
        try:
            with open(file_path, 'r') as file:
                self.content = file.read()
        except FileNotFoundError:
            print("File not found.")
            
    def get_content(self):
        return str(self.content)