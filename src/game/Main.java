package game;

public class Main {

    static boolean check(char v1, char v2, char v3) {
        return v1 == v2 && v2 == v3 && v1 != ' ';
    }

    static void printTable(char[][] table) {
        for (char[] row : table) {
            for (char ch: row) {
                System.out.print(ch + " ");
            }
            System.out.println();
        }
    }

    static void printState(char[][] table) {

        boolean hasEmptyCells = false;

        for (int i = 0; i < table.length; i++) {
            // check rows
            if (check(table[i][0], table[i][1], table[i][2])) {
                System.out.println(table[i][0] + " wins");
                return;
            }

            //check columns
            if (check(table[0][i], table[1][i], table[2][i])) {
                System.out.println(table[0][i] + " wins");
                return;
            }

            hasEmptyCells |= table[i][0] == ' ' || table[i][1] == ' ' || table[i][2] == ' ';
        }

        //check diagonals
        if (check(table[0][0], table[1][1], table[2][2])) {
            System.out.println(table[0][0] + " wins");
        }
        else if (check(table[0][2], table[1][1], table[2][0])) {
            System.out.println(table[0][0] + " wins");
        }
        else if (hasEmptyCells) {
            System.out.println("Game not finished");
        }
        else {
            System.out.println("Draw");
        }

    }

    public static void main(String[] args) {

        char table[][] = new char[][] {
                {'X', 'O', ' '},
                {'O', 'O', 'X'},
                {' ', 'X', ' '}
        };

        printTable(table);
        printState(table);
    }
}