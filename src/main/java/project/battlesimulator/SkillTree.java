package project.battlesimulator;

import java.util.ArrayList;

public class SkillTree {
    SkillTreeNode head = null;
    private static final double costMultPerLevel = 2;
    int numNodes = 0;

    public void add(SkillTreeNode currentNode, SkillTreeNode nodeToAdd) { //CurrentNode will always be head (first function)
        if (head == null) {
            head = nodeToAdd;
            numNodes++;
            head.cost = (int )Math.pow(costMultPerLevel, (int) (Math.log(numNodes)/Math.log(2)+1));
        } else if (currentNode.upgradeName.compareTo(nodeToAdd.upgradeName) >= 0) { //We can always assume that currentNode will not be null
            // because if it's the left or right of another node, then it will be made nodeToAdd and the program will finish before it becomes
            // currentNode. If it's the head node, it will be resolved by the first if statement
            if (currentNode.left == null) {
                currentNode.left = nodeToAdd;
                numNodes++;
                currentNode.left.cost = (int )Math.pow(costMultPerLevel, (int) (Math.log(numNodes)/Math.log(2)+1));
            } else add(currentNode.left,nodeToAdd);
        } else  {
            if (currentNode.right == null) {
                currentNode.right = nodeToAdd;
                numNodes++;
                currentNode.right.cost = (int) Math.pow(costMultPerLevel, (int) (Math.log(numNodes)/Math.log(2)+1));
            } else add(currentNode.right,nodeToAdd);
        }
    }

    public ArrayList<SkillTreeNode> unlockedUpgrades(SkillTreeNode currentNode) { //Listofupgrades will initially be empty
        if (currentNode == null || !currentNode.bought) return new ArrayList<>();
        ArrayList<SkillTreeNode> totalUpgrades = new ArrayList<>();
        totalUpgrades.add(currentNode);
        totalUpgrades.addAll(unlockedUpgrades(currentNode.left));
        totalUpgrades.addAll(unlockedUpgrades(currentNode.right));
        return totalUpgrades;
    }

    public ArrayList<SkillTreeNode> availableUpgrades(SkillTreeNode currentNode) {
        ArrayList<SkillTreeNode> upgrades = new ArrayList<>();
        if (currentNode == null) return upgrades;
        if (!currentNode.bought) {
            upgrades.add(currentNode);
            return upgrades;
        }
        upgrades.addAll(availableUpgrades(currentNode.left));
        upgrades.addAll(availableUpgrades(currentNode.right));
        return upgrades;
    }

    public ArrayList<SkillTreeNode> allNodes(SkillTreeNode currentNode) {
        ArrayList<SkillTreeNode> upgrades = new ArrayList<>();
        if (currentNode == null) return upgrades;
        upgrades.add(currentNode);
        upgrades.addAll(allNodes(currentNode.left));
        upgrades.addAll(allNodes(currentNode.right));
        return upgrades;
    }

    public SkillTreeNode find(SkillTreeNode currentNode, String nodeName) { //CurrentNode will always be head (first function)
        if (head.upgradeName.equals(nodeName)) return head;
        else if (currentNode.upgradeName.compareTo(nodeName) > 0) {
            if (currentNode.left == null || currentNode.left.upgradeName.equals(nodeName)) return currentNode.left;
            else return find(currentNode.left,nodeName);
        } else {
            if (currentNode.right == null || currentNode.right.upgradeName.equals(nodeName)) return currentNode.right;
            else return find(currentNode.right,nodeName);
        }
    }

    private double round(double inp, int place) {
        return Math.round(inp*Math.pow(10,place)) / Math.pow(10,place);
    }
}