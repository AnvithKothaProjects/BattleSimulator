package project.battlesimulator;

import java.util.ArrayList;
import java.util.List;

public class Hero {
    String name;
    int health;
    int maxHealth;
    int defense;
    int chromebits;
    int bolts;
    int normalDefense;
    int level;
    int currentXp;
    int xpForNextLevel;
    int chromeBitsPerTurn;
    List<Attack> attacks = new ArrayList<>();

    public Hero(String name, int health, int defense) {
        this.name = name;
        this.defense = defense;
        this.normalDefense = defense;
        this.health = health;
        this.maxHealth = health;
        this.chromebits = 0;
        this.level = 1;
        this.currentXp = 0;
        this.xpForNextLevel = (int) (5 * Math.pow(2, level));
        this.chromeBitsPerTurn = 10;

        attacks.add(new Attack("Nanobots", 25, 1, 9, false, false,3,0,0));
        attacks.add(new Attack("Virus", 50, 2, 8, false, false, 10,0,0));
        attacks.add(new Attack("Firewall", 0, 1, 10, false, false, 5,0,5));
        attacks.add(new Attack("EMP", 0, 1, 7, true, false, 7,0,0));
        attacks.add(new Attack("Remote Control", 0, 1, 6, false, true, 4,0,0));
        attacks.add(new Attack("Recharge", 0, 2, 10, false, false, 6,30,0));
    }
}