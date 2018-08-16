package game;

import java.util.Scanner;

enum Cell {
    X, O, E; // E means EMPTY

    public char str() {
        if (this == X) {
            return 'X';
        }
        else if (this == O) {
            return 'O';
        }
        else {
            return ' ';
        }
    }
}

public class Main {

    static boolean check(Cell v1, Cell v2, Cell v3) {
        return v1 == v2 && v2 == v3 && v1 != Cell.E;
    }

    static void printTable(Cell[][] table) {
        System.out.println("---------");
        for (Cell[] row : table) {
            System.out.print("| ");
            for (Cell ch: row) {
                System.out.print(ch.str() + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    static void printState(Cell[][] table) {

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

            hasEmptyCells |= table[i][0] == Cell.E || table[i][1] == Cell.E || table[i][2] == Cell.E;
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

    static void makeMove(Cell[][] table) {

        System.out.print("Enter the coordinates: ");

        Scanner scanner = new Scanner(System.in);

        int x = scanner.nextInt();
        int y = scanner.nextInt();

        table[3-y][x-1] = Cell.X;
    }

    public static void main(String[] args) {

        Cell table[][] = new Cell[][] {
                {Cell.E, Cell.X, Cell.X},
                {Cell.O, Cell.O, Cell.E},
                {Cell.O, Cell.X, Cell.E}
        };

        printTable(table);
        makeMove(table);
        printTable(table);
    }
}