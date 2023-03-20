package project.battlesimulator;

import java.util.ArrayList;

public class Enemy {
    String name;
    int hitpoints;
    int maxHitpoints;
    int winXp;
    int boltsDropped;
    ArrayList<Attack> attacks;
    boolean asleep;
    int lengthOfControl;
    boolean defeated = false;
    boolean stongerOverTime = false;
    long whenBattleStarted = 0;
    double damageMultiplier = 1;
    double elusiveness = 1;
    double lifeLeech = 0;
    public Enemy(String name, int hitpoints, int winXp, int boltsDropped) {
        this.name = name;
        this.hitpoints = hitpoints;
        this.maxHitpoints = hitpoints;
        this.winXp = winXp;
        this.boltsDropped = boltsDropped;
        this.attacks = new ArrayList<>();
        this.asleep = false;
        this.lengthOfControl = 0;
    }
}
