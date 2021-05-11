import java.util.ArrayList;
import java.lang.Math;

public class Board {
    ArrayList<Boolean> board;
    int size;
    ArrayList<Boolean> reference;
    ArrayList<Pair> boardIndex;

    public Board (int n){
        this.size = n;
        this.board = new ArrayList<>();
        this.reference = new ArrayList<>();
        this.boardIndex = new ArrayList<>();
        for (int i = 0; i < n*n; i++) {
            this.board.add(false);
            this.reference.add(false);
            this.boardIndex.add(getPosition(i));
        }
    }

    public ArrayList<Boolean> solve() {
        if (isSolved()) {
            return this.board;
        } else {
            return solveBoards(nextBoards(this.board));
        }
    }

    public ArrayList<Boolean> solveBoards(ArrayList<ArrayList<Boolean>> boards) {
        ArrayList<Boolean> branch;
        if (boards.isEmpty()) {
            return this.reference;
        } else {
            this.board = boards.get(0);
            branch = solve();
            if (!this.reference.equals(branch)) {
                return branch;
            } else {
                ArrayList<ArrayList<Boolean>> boardsMinus = new ArrayList<ArrayList<Boolean>>(boards);
                boardsMinus.remove(0);
                return solveBoards(boardsMinus);
            }
        }
    }

    public ArrayList<ArrayList<Boolean>> nextBoards(ArrayList<Boolean> board) {
        ArrayList<ArrayList<Boolean>> result = new ArrayList<ArrayList<Boolean>>();
        ArrayList<Boolean> board_change;
        ArrayList<Integer> validIndex;
        int i = 0;
        int numTrue = numTrue(board);
        if (numTrue == 0) {
            for (boolean b : board) {
                board_change = new ArrayList<>(board);
                board_change.set(i, true);
                result.add(board_change);
                i++;
            }
        } else {
            validIndex = nextValid(board);
            for (int j:validIndex) {
                board_change = new ArrayList<>(board);
                board_change.set(j, true);
                result.add(board_change);
            }
        }
        return result;
        }

    public ArrayList<Integer> nextValid(ArrayList<Boolean> bd) {
        ArrayList<Integer> invalidIndexes = new ArrayList<>();
        ArrayList<Integer> result = new ArrayList<>();
        int i = 0;
        for (boolean b : bd) {
            if (b == true) {
                Pair pos = getPosition(i);
                invalidIndexes.addAll(columnsAndRowsAndDiagonals(pos));
            }
            i++;
        }
        for (int j = 0; j < i; j++) {
            if (invalidIndexes.contains(j)) {
                continue;
            } else {
                result.add(j);
            }
        }
        return result;
    }

    public Pair getPosition(int index) {
        int column = index % this.size;
        int row = (int)Math.floor((index / this.size));
        Pair<Integer, Integer> result = new Pair<>(row, column);
        return result;

    }

    public int getIndex(Pair pos) {
        return (this.size * getRow(pos)) + getColumn(pos);
    }

    public boolean isSolved() {
        int numTrue = numTrue(this.board);
        if (numTrue == this.size) {
            return true;
        } else {
            return false;
        }
    }


    public ArrayList<Integer> columnsAndRowsAndDiagonals(Pair pos) {
        ArrayList<Integer> result = new ArrayList<>();
        int row = getRow(pos);
        int column = getColumn(pos);
        for (Pair p : this.boardIndex) {
            int pRow = getRow(p);
            int pColumn = getColumn(p);
            if ((row == pRow) || column == pColumn || (((((float)pRow - row) / ((float)pColumn - column)) == 1) || ((((float)pRow - row) / ((float)pColumn - column)) == -1))) {
                result.add(getIndex(p));
            }
        }
        return result;
    }

    public int getRow(Pair p) {
        return (int)p.getLeft();
    }

    public int getColumn(Pair p) {
        return (int)p.getRight();
    }

    public int numTrue(ArrayList<Boolean> bd) {
        int result = 0;
        for (boolean b: bd) {
            if (b == true) {
                result++;
            }
        }
        return result;
    }

    public String printResult(ArrayList<Boolean> bd) {
        int i = 1;
        String result = "";
        for (boolean b:bd) {
            if (b == true) {
                result += " O";
            } else {
                result += "  ";
            }
            if (i%this.size == 0) {
                result += "\n";
                for (int j=1; j < this.size; j++) {
                    result += "---|";
                }
                result += "--\n";
            } else {
                result += " |";
            }
            i++;
        }
        return result;
    }
}