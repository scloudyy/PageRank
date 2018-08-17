public class Driver {
    /**
     * args0: dir of transition
     * args1: dir of pr0
     * args2: dir of UnitCellMultiplication result
     * args3: times of convergence
     * */
    public static void main(String[] args) throws Exception {
        UnitCellMultiplication multiplication = new UnitCellMultiplication();
        UnitCellSum sum = new UnitCellSum();


        String transitionMatrix = args[0];
        String prMatrix = args[1];
        String unitState = args[2];
        int count = Integer.parseInt(args[3]);
        for(int i=0;  i<count;  i++) {
            String[] args1 = {transitionMatrix, prMatrix+i, unitState+i};
            multiplication.main(args1);
            String[] args2 = {unitState + i, prMatrix+(i+1)};
            sum.main(args2);
        }
    }
}
