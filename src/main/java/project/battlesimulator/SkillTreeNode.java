package project.battlesimulator;

public class SkillTreeNode {
    String upgradeName;
    boolean bought;
    double cost;
    int attackUp;
    int defenseUp;
    int hitChanceUp;
    int costDown;
    SkillTreeNode right = null;
    SkillTreeNode left = null;
    public SkillTreeNode(String upgradeName, int attackUp, int defenseUp, int hitChanceUp, int costDown) {
        this.attackUp = attackUp;
        this.defenseUp = defenseUp;
        this.hitChanceUp = hitChanceUp;
        this.costDown = costDown;
        this.upgradeName = upgradeName;
    }
}