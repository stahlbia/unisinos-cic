package BinTreeJ;

public class BinTreeExamples {
    public static void main(String[] args) {
        BinTreeExamples run = new BinTreeExamples();
        run.example01();
        run.example02();
        run.example03();
        run.example04();
        run.example05();
    }

    private void example01() {
        BinTree tree = new BinTree();

        tree.insert(10);
        tree.insert(5);
        tree.insert(20);
        tree.insert(3);
        tree.insert(7);

        tree.preOrder();
        tree.central();
        tree.postOrder();
        tree.generateDotFile("treeBinEx1.dot");
    }

    private void example02() {
        BinTree tree = new BinTree();
        
        tree.insert(10);
        tree.insert(5);
        tree.insert(20);
        tree.insert(3);
        tree.insert(7);
        tree.insert(38);
        tree.insert(15);
        tree.insert(45);
        tree.insert(23);
        tree.insert(12);

        tree.preOrder();
        tree.central();
        tree.postOrder();

        tree.generateDotFile("treeBinEx2-1.dot");

        tree.remove(38);
        tree.generateDotFile("treeBinEx2-2.dot");
    }

    private void example03() {
        BinTree tree = new BinTree();
        
        tree.insert(10);
        tree.insert(5);
        tree.insert(20);
        tree.insert(3);
        tree.insert(7);

        tree.preOrder();
        tree.central();
        tree.postOrder();

        tree.remove(5);
        tree.remove(20);
        tree.generateDotFile("treeBinEx3.dot");
    }

    private void example04() {
        BinTree tree = new BinTree();
        
        tree.insert(10);
        tree.insert(5);
        tree.insert(20);
        tree.insert(3);
        tree.insert(7);

        tree.preOrder();
        tree.central();
        tree.postOrder();

        tree.remove(3);
        tree.remove(5);
        tree.generateDotFile("treeBinEx4.dot");
    }

    private void example05() {
        BinTree tree = new BinTree();
        
        tree.insert(10);
        tree.insert(5);
        tree.insert(20);
        tree.insert(3);
        tree.insert(7);

        tree.preOrder();
        tree.central();
        tree.postOrder();

        tree.remove(10);
        tree.generateDotFile("treeBinEx5.dot");
    }
}
