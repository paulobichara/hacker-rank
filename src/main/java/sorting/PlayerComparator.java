package sorting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Player {

    String name;
    int score;

    Player(String name, int score) {
        this.name = name;
        this.score = score;
    }
}

class Checker implements Comparator<Player> {
    // complete this method
    public int compare(Player one, Player other) {
        if (one == null || other == null) {
            throw new NullPointerException();
        }
        return one.score > other.score ? -1 : one.score < other.score ? 1 : one.name.compareTo(other.name);
    }
}


public class PlayerComparator {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        Player[] player = new Player[n];
        Checker checker = new Checker();

        for(int i = 0; i < n; i++){
            player[i] = new Player(scan.next(), scan.nextInt());
        }
        scan.close();

        Arrays.sort(player, checker);
        for(int i = 0; i < player.length; i++){
            System.out.printf("%s %s\n", player[i].name, player[i].score);
        }
    }
}
