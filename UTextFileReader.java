public class UTextFileReader {
    public static void main(String[] args) {
       //put words of Gizmo into a Uset
        //put the words into Gadget
        RunTimer PocketWatch = new RunTimer(); //a timer to track


        WordReader Gizmo = new WordReader("sample-texts/Metamorphoses.txt");
        BinarySearchTree<String> Gadget = new BinarySearchTree<String>(); //create a new USet
        PocketWatch.start();
//        for(String w: Gizmo.words){//for every string w in Gizmo.words
//            Gadget.add(w);
//        }
        String w = Gizmo.nextWord();
        while(w != null){
            Gadget.add(w);
            w = Gizmo.nextWord();
        }

        PocketWatch.stop();
        System.out.println("Metamorphoses:");
        System.out.println(PocketWatch.getElapsedNanos());

    }
}
