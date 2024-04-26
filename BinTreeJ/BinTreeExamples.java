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

        System.out.println("\n\nExample 01: PreOrder");
        tree.preOrder();

        System.out.println("\nExample 01: Central");
        tree.central();

        System.out.println("\nExample 01: PostOrder");
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

        System.out.println("\n\nExample 02: PreOrder");
        tree.preOrder();

        System.out.println("\nExample 02: Central");
        tree.central();

        System.out.println("\nExample 02: PostOrder");
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

        System.out.println("\n\nExample 03: PreOrder");
        tree.preOrder();

        System.out.println("\nExample 03: Central");
        tree.central();

        System.out.println("\nExample 03: PostOrder");
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

        System.out.println("\n\nExample 04: PreOrder");
        tree.preOrder();

        System.out.println("\nExample 04: Central");
        tree.central();

        System.out.println("\nExample 04: PostOrder");
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

        System.out.println("\n\nExample 05: PreOrder");
        tree.preOrder();

        System.out.println("\nExample 05: Central");
        tree.central();

        System.out.println("\nExample 05: PostOrder");
        tree.postOrder();

        tree.remove(10);
        tree.generateDotFile("treeBinEx5.dot");
    }
}
