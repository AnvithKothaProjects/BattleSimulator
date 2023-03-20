package project.battlesimulator;

public class Attack {
    String attackName;
    String description;
    double damage;
    int numTurns;
    double chanceOfHitting;
    boolean sleepEffect;
    boolean remoteControl;
    boolean attackBought;
    int cost;
    int healing;
    int defenseUp;
    boolean initiallyBought = false;
    boolean hitThisTurn = false;
    SkillTree attackTree = new SkillTree();

    public Attack(String attackName, double damage, int numTurns, int chanceOfHitting, boolean sleepEffect, boolean remoteControl, int cost, int healing, int defenseUp) {
        this.description = "";
        this.attackName = attackName;
        this.damage = damage;
        this.numTurns = numTurns; //Number of turns the attack lasts
        this.chanceOfHitting = chanceOfHitting;
        this.sleepEffect = sleepEffect; //If the attack puts the enemy to sleep
        this.remoteControl = remoteControl; //If the attack controls the enemy
        this.cost = cost;
        this.healing = healing;
        this.defenseUp = defenseUp;
    }

    public void addToTree(SkillTreeNode upgrade) {
        attackTree.add(attackTree.head, upgrade);
    }
}