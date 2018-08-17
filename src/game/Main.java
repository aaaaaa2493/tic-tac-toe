package game;

import java.util.*;

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

    public GameState winner() {
        if (this == X) {
            return GameState.X_WIN;
        }
        else if (this == O) {
            return GameState.O_WIN;
        }
        else {
            throw new IllegalArgumentException("Empty cells can not win");
        }
    }

    public Cell other() {
        if (this == X) {
            return Cell.O;
        }
        else if (this == O) {
            return Cell.X;
        }
        else {
            throw new IllegalArgumentException("Empty cells can not have other");
        }
    }
}

enum GameState {
    X_WIN, O_WIN, DRAW, NOT_FINISHED;

    public String str() {
        if (this == X_WIN) {
            return "X wins";
        }
        else if (this == O_WIN) {
            return "O wins";
        }
        else if (this == DRAW) {
            return "Draw";
        }
        else {
            return "Game not finished";
        }
    }
}

interface Decision {
    void decide(Cell[][] table, Cell move);
}


public class Main {

    static Decision getDecision(String player) {
        switch (player) {
            case "user":
                return Main::makeMove;
            case "easy":
                return Main::makeMoveLevelEasy;
            case "medium":
                return Main::makeMoveLevelMedium;
            default:
                return null;
        }
    }

    static boolean check(Cell v1, Cell v2, Cell v3) {
        return v1 == v2 && v2 == v3 && v1 != Cell.E;
    }

    static boolean checkAlmost(Cell v1, Cell v2, Cell v3, Cell move) {
        boolean fstEmpty = v1 == Cell.E && v2 == move && v3 == move;
        boolean sndEmpty = v1 == move && v2 == Cell.E && v3 == move;
        boolean trdEmpty = v1 == move && v2 == move && v3 == Cell.E;
        return fstEmpty || sndEmpty || trdEmpty;
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

    static GameState getState(Cell[][] table) {

        boolean hasEmptyCells = false;

        for (int i = 0; i < table.length; i++) {
            // check rows
            if (check(table[i][0], table[i][1], table[i][2])) {
                return table[i][0].winner();
            }

            //check columns
            if (check(table[0][i], table[1][i], table[2][i])) {
                return table[0][i].winner();
            }

            hasEmptyCells |= table[i][0] == Cell.E || table[i][1] == Cell.E || table[i][2] == Cell.E;
        }

        //check diagonals
        if (check(table[0][0], table[1][1], table[2][2]) || check(table[0][2], table[1][1], table[2][0])) {
            return table[1][1].winner();
        }
        else if (hasEmptyCells) {
            return GameState.NOT_FINISHED;
        }
        else {
            return GameState.DRAW;
        }

    }

    static boolean makeRandomMove(Cell[][] table, Cell move) {

        if (getState(table) != GameState.NOT_FINISHED) {
            return false;
        }

        Random random = new Random();

        while (true) {

            int x = random.nextInt(3);
            int y = random.nextInt(3);

            if (table[y][x] != Cell.E) {
                continue;
            }

            table[y][x] = move;
            break;

        }

        return true;
    }

    static boolean makeObviousMove(Cell[][] table, Cell move) {
        // check for immediate winning then for immediate losing
        for (Cell cell : new Cell[]{move, move.other()}) {
            for (int i = 0; i < table.length; i++) {

                // check rows
                if (checkAlmost(table[i][0], table[i][1], table[i][2], cell)) {
                    for (int j = 0; j < table[i].length; j++) {
                        if (table[i][j] == Cell.E) {
                            table[i][j] = move;
                            return true;
                        }
                    }
                }

                //check columns
                if (checkAlmost(table[0][i], table[1][i], table[2][i], cell)) {
                    for (int j = 0; j < table[i].length; j++) {
                        if (table[j][i] == Cell.E) {
                            table[j][i] = move;
                            return true;
                        }
                    }
                }

                // check diagonals
                if (checkAlmost(table[0][0], table[1][1], table[2][2], cell)) {
                    if (table[0][0] == Cell.E) {
                        table[0][0] = move;
                        return true;
                    }
                    else if (table[1][1] == Cell.E) {
                        table[1][1] = move;
                        return true;
                    }
                    else if (table[2][2] == Cell.E) {
                        table[2][2] = move;
                        return true;
                    }
                }
                else if (checkAlmost(table[0][2], table[1][1], table[2][0], cell)) {
                    if (table[0][2] == Cell.E) {
                        table[0][2] = move;
                        return true;
                    }
                    else if (table[1][1] == Cell.E) {
                        table[1][1] = move;
                        return true;
                    }
                    else if (table[2][0] == Cell.E) {
                        table[2][0] = move;
                        return true;
                    }
                }
            }
        }

        return false;
    }

    static void makeMove(Cell[][] table, Cell move) {

        Scanner scanner = new Scanner(System.in);

        int x;
        int y;

        while (true) {

            System.out.print("Enter the coordinates: ");

            try {
                x = scanner.nextInt();
                y = scanner.nextInt();
            }
            catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("You should enter numbers!");
                continue;
            }

            if (x > 3 || x < 1 || y > 3 || y < 1) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            int indexX = x - 1;
            int indexY = 3 - y;

            if (table[indexY][indexX] != Cell.E) {
                System.out.println("This cell is busy! Choose another one!");
                continue;
            }

            table[indexY][indexX] = move;
            break;

        }
    }

    static void makeMoveLevelEasy(Cell[][] table, Cell move) {
        System.out.println("Making move level \"easy\"");
        makeRandomMove(table, move);
    }

    static void makeMoveLevelMedium(Cell[][] table, Cell move) {
        System.out.println("Making move level \"medium\"");
        if (!makeObviousMove(table, move)) {
            makeRandomMove(table, move);
        }
    }

    static void startGame(Decision playerX, Decision playerO) {

        Cell table[][] = new Cell[][] {
                {Cell.E, Cell.E, Cell.E},
                {Cell.E, Cell.E, Cell.E},
                {Cell.E, Cell.E, Cell.E}
        };

        GameState state;
        while (true) {
            printTable(table);
            playerX.decide(table, Cell.X);

            state = getState(table);
            if (state != GameState.NOT_FINISHED) {
                break;
            }

            printTable(table);
            playerO.decide(table, Cell.O);

            state = getState(table);
            if (state != GameState.NOT_FINISHED) {
                break;
            }
        }

        printTable(table);
        System.out.println(state.str());

    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.print("Input command: ");
            String[] command = scanner.nextLine().split(" ");

            switch (command[0]) {
                case "start":
                    if (command.length != 3) {
                        System.out.println("Bad parameters!");
                        continue;
                    }

                    Decision playerX = getDecision(command[1]);
                    Decision playerO = getDecision(command[2]);
                    if (playerX == null || playerO == null) {
                        System.out.println("Bad parameters!");
                        continue;
                    }

                    startGame(playerX, playerO);
                    System.out.println();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Bad command!");
            }
        }

    }
}