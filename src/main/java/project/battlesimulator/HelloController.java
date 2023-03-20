package project.battlesimulator;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class HelloController {
    private Hero player;
    private boolean nameTyped = false;
    private ArrayList<ArrayList<Enemy>> keyDefenders = new ArrayList<>();
    private Enemy currentEnemy;
    private Attack currentAttack;
    private boolean inBattle = false;
    boolean playedBefore = false;
    private int numKey; //Which stage of progression the player has gotten to
    private String[] stages = {"Bronze Key", "Silver Key", "Gold Key", "Final Boss", "End"};
    private String [] descriptions = {"Minions: Basic long distance enemies", "Melee Bots: Hit all their shots with high damage and have a lot" +
            " of health. They take extra damage though. Their attacks get stronger over time. Remote control is strong against them.", "", "", "End"};
    private boolean gameEnded = false;

    @FXML
    private TabPane gadgetsSelection, allTabs;
    @FXML
    private Label attackDescription, attackDamage, attackNumberOfTurns, attackCost, attackHitChance;
    @FXML
    private TextArea attackDescriptionBox;
    @FXML
    private TextArea showStory;
    @FXML
    private Label boughtUpgradesLabel, availableUpgradesLabel;
    @FXML
    private ListView boughtUpgrades, availableUpgrades;
    @FXML
    private Button buyAttackBtn;
    @FXML
    private ImageView attackImage;
    @FXML
    private Button confirmInfoBtn;
    @FXML
    private TextField characterNameText;
    @FXML
    private ListView pickedGadgets, unpickedGadgets;
    @FXML
    private Label threeGadgetsLabel, pickedGadgetsLabel, unpickedGadgetsLabel;
    @FXML
    private ListView enemiesList;
    @FXML
    private Button battleStartBtn;
    @FXML
    private Label enemyNameLabel, playerNameInBattle, healthLabel, defenseLabel, chromebitsLabel, resultOfAttack;
    @FXML
    private Label enemyClassLabel;
    @FXML
    private TextArea describeEnemiesBox;
    @FXML
    private Label availableAttacksLabel, playerHealthBarLabel, enemyHealthBarLabel;
    @FXML
    private Button endTurnBtn;
    @FXML
    private ListView availableAttacksListview;
    @FXML
    private ProgressBar playerHealthBar, enemyHealthBar;
    @FXML
    private Label playerStatsLabel, playerHealthLabel, playerDefenseLabel, playerBoltsLabel, playerLevelLabel;
    @FXML
    private Label youWinLabel, youWinLabel2;
    @FXML
    private Button restartGameBtn, restartGameBtn2;
    @FXML
    private Rectangle playerStatsRectangle, gadgetsBox;
    @FXML
    private Label battleResultLabel;
    @FXML
    private ProgressBar levelProgress;
    @FXML
    private Label enemyAttackUsedLabel, enemyAttackResultLabel;
    @FXML
    private Line dividingLine;
    @FXML
    private ImageView playerImage, enemyImage;

    public HelloController() {
        for (int i=0; i<20; i++) {
            System.out.println(1.719372841*i);
        }

        player = new Hero("GERIGNJ", 500, 10); //Default name that nobody ever chooses

        setAttacks();

        for (int i=0; i<5; i++) keyDefenders.add(new ArrayList<>());

        keyDefenders.get(0).add(new Enemy("Scrappy 1", rNum(400,600), rNum(30,70), rNum(5,15)));
        keyDefenders.get(0).get(0).attacks.add(new Attack("Laser",50, 1, 6, false, false, 0,0,0));

        keyDefenders.get(0).add(new Enemy("Sparky", rNum(300,700), rNum(40,80), rNum(6,16)));
        keyDefenders.get(0).get(1).attacks.add(new Attack("Laser",50, 1, 6, false, false, 0,0,0));
        keyDefenders.get(0).get(1).attacks.add(new Attack("Blast",25, 2, 10, false, false, 0,0,0));

        keyDefenders.get(0).add(new Enemy("Macro-9000", rNum(400,700), rNum(40,80), rNum(6,16)));
        keyDefenders.get(0).get(2).attacks.add(new Attack("Rocket",50, 1, 6, false, false, 0,0,0));
        keyDefenders.get(0).get(2).attacks.add(new Attack("Guided Missile",25, 2, 10, false, false, 0,0,0));
        keyDefenders.get(0).get(2).attacks.add(new Attack("Machine Guns", 10, 5, 8, false, false,0,0,0));

        keyDefenders.get(1).add(new Enemy("Guardian", rNum(400,800), rNum(50,90), rNum(7,17)));
        keyDefenders.get(1).get(0).attacks.add(new Attack("Katana",70, 1, 10, false, false, 0,0,0));
        keyDefenders.get(1).get(0).attacks.add(new Attack("Punches",30, 3, 10, false, false, 0,0,0));

        keyDefenders.get(1).add(new Enemy("Machinator", rNum(500,900), rNum(70,100), rNum(10,20)));
        keyDefenders.get(1).get(1).attacks.add(new Attack("Flurry Rush",15, 5, 10, false, false, 0,0,0));
        keyDefenders.get(1).get(1).attacks.add(new Attack("Punches",30, 3, 10, false, false, 0,0,0));
        keyDefenders.get(1).get(1).attacks.add(new Attack("Back Kick",60, 1, 10, false, false, 0,0,0));

        keyDefenders.get(1).add(new Enemy("Lankentron", rNum(600,900), rNum(90,100), rNum(15,25)));
        keyDefenders.get(1).get(2).attacks.add(new Attack("Flurry Rush",15, 5, 10, false, false, 0,0,0));
        keyDefenders.get(1).get(2).attacks.add(new Attack("Punches",30, 3, 10, false, false, 0,0,0));
        keyDefenders.get(1).get(2).attacks.add(new Attack("Side Lunge",100, 1, 10, false, false, 0,0,0));

        for (Enemy enemy : keyDefenders.get(1)) {
            enemy.stongerOverTime = true; // Didn't feel like adding a new parameter to all the objects
            enemy.damageMultiplier = 1.5;
        }

        keyDefenders.get(2).add(new Enemy("Emination", rNum(600,1000), rNum(100,130), rNum(20,27)));
        keyDefenders.get(2).get(0).attacks.add(new Attack("Spark Plug",50, 2, 10, false, false, 0,0,0));
        keyDefenders.get(2).get(0).attacks.add(new Attack("Mind's Eye",20, 3, 10, false, false, 0,0,0));
        keyDefenders.get(2).get(0).elusiveness = .7;

        keyDefenders.get(2).add(new Enemy("Tanker Bot", rNum(700,1000), rNum(110,150), rNum(25,30)));
        keyDefenders.get(2).get(1).attacks.add(new Attack("Cyberattack",rNum(10,90),1,10,false,false,0,0,0));
        keyDefenders.get(2).get(1).attacks.add(new Attack("Barrage",10,rNum(3,8),10,false,false,0,0,0));

        keyDefenders.get(2).add(new Enemy("Scrappy 2", rNum(800,1100), rNum(110,160), rNum(28,33)));
        keyDefenders.get(2).get(2).attacks.add(new Attack("Recoil Detenator",rNum(10,90),1,10,false,false,0,0,0));
        keyDefenders.get(2).get(2).attacks.add(new Attack("Seige",10,rNum(3,8),10,false,false,0,0,0));

        keyDefenders.get(3).add(new Enemy("MOTHER AI", 500, 1000,50));
        keyDefenders.get(3).get(0).attacks.add(getAttack("Virus",player));
        keyDefenders.get(3).get(0).attacks.add(getAttack("Nanobots",player));
        keyDefenders.get(3).get(0).lifeLeech = 0.2;

        currentAttack = getAttack("Nanobots",player); //So I don't get nullPointer errors
        currentEnemy = getEnemy("Scrappy 1", keyDefenders);

        load();
    }

    @FXML
    private void spellTabSelected() {
        String spellName = gadgetsSelection.getSelectionModel().getSelectedItem().getText();
        currentAttack = getAttack(spellName, player);
        updateUgradeScreen();
    }

    @FXML
    private void upgradeBought() {
        SkillTreeNode selectedUpgrade;
        if (availableUpgrades.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        selectedUpgrade = currentAttack.attackTree.find(currentAttack.attackTree.head, substringBeforeSpaces((String)availableUpgrades.getSelectionModel().getSelectedItem()));
        if (selectedUpgrade.cost <= player.bolts) {
            selectedUpgrade.bought = true;
            player.bolts -= selectedUpgrade.cost;
            currentAttack.damage += selectedUpgrade.attackUp;
            currentAttack.defenseUp += selectedUpgrade.defenseUp;
            currentAttack.chanceOfHitting += selectedUpgrade.hitChanceUp;
            currentAttack.cost -= selectedUpgrade.costDown;
        }
        updateUgradeScreen();
        save();
    }

    @FXML
    private void nameTyped() {
        unpickedGadgets.setVisible(true);
        pickedGadgets.setVisible(true);
        threeGadgetsLabel.setVisible(true);
        pickedGadgetsLabel.setVisible(true);
        unpickedGadgetsLabel.setVisible(true);
        if (!nameTyped) {
            for (Attack attack : player.attacks) {
                unpickedGadgets.getItems().add(attack.attackName);
            }
            nameTyped = true;
        }
        enableConfirmInfoBtn();
    }

    @FXML
    private void confirmCharacterInfo() {
        player.name = characterNameText.getText();
        allTabs.getTabs().remove(0);
        allTabs.getSelectionModel().select(2);
        showStory.setText("Dear " + player.name + ", \n A year ago the AI we created was a revolution. A conscious " +
                "being orders of magnitude better than the human mind. But the more we thought about it, the less good it sounded. The AI could be " +
                "used for violence or become destructive itself. After pondering this for a long time, we decided to shut the project down. But as " +
                "you know very well, things didn't end there. The AI replicated itself on another computer, now with a vicious thirst for vengeance. " +
                "It hacked all the world's computers and used them to build a legion of robots. They wiped out people mercilessly and now we're the last" +
                " ones left. We left 3 keys to destroy the mother AI, one bronze, one silver, and one gold. You must collect them and use them to destroy the mother AI" +
                ". However, the mother AI will not surrender and will try to stop you." +
                "\n Sincerely, \n The Humans");
        save();
    }

    @FXML
    private void skipIntro() {
        if (playedBefore) {
            allTabs.getTabs().remove(0);
            allTabs.getTabs().remove(2);
        }
    }

    @FXML
    private void finishStoryParagraph() {
        allTabs.getTabs().remove(2);
        allTabs.getSelectionModel().select(0);
    }

    @FXML
    private void gadgetDeselected() {
        String selected = (String) pickedGadgets.getSelectionModel().getSelectedItem();
        getAttack(selected, player).attackBought = false;
        getAttack(selected, player).initiallyBought = false;
        pickedGadgets.getItems().remove(selected);
        unpickedGadgets.getItems().add(selected);
        enableConfirmInfoBtn();
    }

    @FXML
    private void gadgetSelected() {
        String selected = (String) unpickedGadgets.getSelectionModel().getSelectedItem();
        if (pickedGadgets.getItems().size() < 3) {
            getAttack(selected, player).attackBought = true;
            getAttack(selected, player).initiallyBought = true;
            unpickedGadgets.getItems().remove(selected);
            pickedGadgets.getItems().add(selected);
        }
        enableConfirmInfoBtn();
    }

    @FXML
    private void startBattle() {
        player.chromebits += player.chromeBitsPerTurn;
        String enemyPicked = enemiesList.getSelectionModel().getSelectedItem().toString();
        player.health = player.maxHealth;
        player.defense = player.normalDefense;
        currentEnemy = getEnemy(enemyPicked,keyDefenders);
        inBattle = true;
        currentEnemy.whenBattleStarted = System.nanoTime();
        currentEnemy.hitpoints = currentEnemy.maxHitpoints;
        updateBattleInfo();
        changeBattleUI();
        enemyAttackUsedLabel.setText("");
        enemyAttackResultLabel.setText("");
        resultOfAttack.setText("");
        battleResultLabel.setText("");
        battleStartBtn.setVisible(!inBattle);
    }

    @FXML
    private void battleTabSelected() {
        enemiesList.getItems().clear();
        for (int i=0; i<keyDefenders.get(numKey).size(); i++) {
            if (!keyDefenders.get(numKey).get(i).defeated) enemiesList.getItems().add(keyDefenders.get(numKey).get(i).name);
        }
        if (!gameEnded) changeBattleUI();
        youWinLabel.setVisible(gameEnded);
        restartGameBtn.setVisible(gameEnded);
        youWinLabel2.setVisible(gameEnded);
        restartGameBtn2.setVisible(gameEnded);
    }

    @FXML
    private void attackUsed() {
        currentAttack = getAttack(availableAttacksListview.getSelectionModel().getSelectedItem().toString(),player);
        boolean attackHit = false;
        if (currentAttack.cost <= player.chromebits) {
            player.chromebits -= currentAttack.cost;
            int numAttacksHit = 0;
            for (int i=0; i<currentAttack.numTurns; i++) {
                if (rNum(1,10) <= currentAttack.chanceOfHitting*currentEnemy.elusiveness) {
                    currentEnemy.hitpoints -= (int) (currentAttack.damage * currentEnemy.damageMultiplier);
                    player.health += currentAttack.healing;
                    player.health = Math.min(player.maxHealth, player.health);
                    player.defense += currentAttack.defenseUp;
                    if (currentAttack.sleepEffect) currentEnemy.asleep = true;
                    if (currentAttack.remoteControl) currentEnemy.lengthOfControl ++;
                    currentAttack.hitThisTurn = true;
                    numAttacksHit ++;
                    attackHit = true;
                }
            }
            resultOfAttack.setText(currentAttack.attackName + " hit " + numAttacksHit + "/" + currentAttack.numTurns + " times!");
        } else {
            resultOfAttack.setText("Not Enough Chromebits");
        }
        endBattle();
        updateBattleInfo();
        if (getAttack("Remote Control", player).hitThisTurn) {
            if (getAttack("EMP",player).attackBought) { //Can't use EMP's and remote controls in the same turn
                for (int i=0; i<availableAttacksListview.getItems().toArray().length; i++) {
                    if (availableAttacksListview.getItems().toArray()[i].toString().equals("EMP")) availableAttacksListview.getItems().remove(i);
                }
            }
        }
        if (getAttack("EMP", player).hitThisTurn) {
            if (getAttack("Remote Control",player).attackBought && attackHit) {
                for (int i=0; i<availableAttacksListview.getItems().toArray().length; i++) {
                    if (availableAttacksListview.getItems().toArray()[i].toString().equals("Remote Control")) availableAttacksListview.getItems().remove(i);
                }
            }
            if (getAttack("EMP",player).attackBought && attackHit) {
                for (int i=0; i<availableAttacksListview.getItems().toArray().length; i++) {
                    if (availableAttacksListview.getItems().toArray()[i].toString().equals("EMP")) availableAttacksListview.getItems().remove(i);
                }
            }
        }
    }

    @FXML
    private void upgradeTabSelected() {
        if (!gameEnded) updateUgradeScreen();
    }

    @FXML
    private void endTurn() {
        for (Attack attack : player.attacks) {
            attack.hitThisTurn = false;
        }
        player.chromebits += player.chromeBitsPerTurn;
        currentAttack = currentEnemy.attacks.get(rNum(0,currentEnemy.attacks.size()-1));
        if (currentEnemy.asleep) {
            enemyAttackUsedLabel.setText(currentEnemy.name + " is asleep!");
            enemyAttackResultLabel.setText("");
            currentEnemy.asleep = false;
        } else {
            int numHits = 0;
            if (currentEnemy.lengthOfControl > 0) {
                for (int j=0; j<currentEnemy.lengthOfControl; j++) {
                    for (int k=0; k<currentAttack.numTurns; k++) {
                        if (rNum(1, 10) <= currentAttack.chanceOfHitting) {
                            numHits++;
                            currentEnemy.hitpoints -= finalAttackDamage(false,currentEnemy.stongerOverTime);
                        }
                    }
                }
            } else {
                for (int k=0; k<currentAttack.numTurns; k++) {
                    if (rNum(1, 10) <= currentAttack.chanceOfHitting) {
                        currentAttack = currentEnemy.attacks.get(rNum(0,currentEnemy.attacks.size()-1));
                        numHits++;
                        player.health -= finalAttackDamage(true,currentEnemy.stongerOverTime);
                        currentEnemy.hitpoints = (int) Math.min(currentEnemy.maxHitpoints, currentEnemy.hitpoints + finalAttackDamage(true,currentEnemy.stongerOverTime)*currentEnemy.lifeLeech);
                    }
                }
            }
            enemyAttackResultLabel.setText("It hit " + numHits + " out of " + currentAttack.numTurns + " times");
            if (currentEnemy.lengthOfControl>0) {
                enemyAttackUsedLabel.setText(currentEnemy.name + " used " + currentAttack.attackName + " on itself!");
                enemyAttackResultLabel.setText("It hit " + numHits*currentEnemy.lengthOfControl + " out of " + currentAttack.numTurns*currentEnemy.lengthOfControl + " times");
                currentEnemy.lengthOfControl = 0;
            }
            else enemyAttackUsedLabel.setText(currentEnemy.name + " used " + currentAttack.attackName + "!");

            endBattle();
        }
        updateBattleInfo();
    }

    private int finalAttackDamage(boolean defense, boolean strongerOverTime) {
        int damage = (int) currentAttack.damage;
        if (defense) {
            damage *= Math.max(0, (double) (100-player.defense)/100);
        } if (strongerOverTime) {
            damage = (int) (damage * Math.pow(1.03, (System.nanoTime()-currentEnemy.whenBattleStarted)/(double)1000000000));
        }
        return damage;
    }

    @FXML
    private void buyAttack() {
        if (player.bolts >= 25) {
            player.bolts -= 25;
            currentAttack.attackBought = true;
            updateUgradeScreen();
            buyAttackBtn.setVisible(false);
        }
        save();
    }

    @FXML
    private void restartGame() {
        gameEnded = false;
        resetGame();
        save();
        updateUgradeScreen();
        updateBattleInfo();
        endGame();
        battleStartBtn.setVisible(!gameEnded);
    }

    @FXML
    private void restartGame2() {
        gameEnded = false;
        resetGame();
        save();
        updateUgradeScreen();
        updateBattleInfo();
        endGame();
        battleStartBtn.setVisible(!gameEnded);
    }

    private void setAttacks() {
        Attack nanobots = getAttack("Nanobots",player);
        nanobots.description = "A basic attack that costs little and does little damage";
        nanobots.addToTree(new SkillTreeNode("+5 Damage", 5, 0, 0, 0));
        nanobots.addToTree(new SkillTreeNode("-1 Chromebits", 0, 0, 0, 1));
        nanobots.addToTree(new SkillTreeNode("Guaranteed Hit", 0, 0, 1, 0));

        Attack virus = getAttack("Virus",player);
        virus.description = "A powerful attack that uses a lot of chromebits but does heavy damage";
        virus.addToTree(new SkillTreeNode("+1 Hitchance",0,0,1,0));
        virus.addToTree(new SkillTreeNode("-2 Chromebits",0,0,0,2));
        virus.addToTree(new SkillTreeNode("+5 Attack",5,0,0,0));

        Attack firewall = getAttack("Firewall",player);
        firewall.description = "Improves your defense";
        firewall.addToTree(new SkillTreeNode("-1 Chromebits", 0,0,0,1));
        firewall.addToTree(new SkillTreeNode("+1 Hitchance", 0,0,1,0));
        firewall.addToTree(new SkillTreeNode("+2 Defense", 0,2,0,1));

        Attack emp = getAttack("EMP",player);
        emp.description = "Sends out an electrifying pulse that'll shut your enemies down";
        emp.addToTree(new SkillTreeNode("+5 Damage", 5,0,0,0));
        emp.addToTree(new SkillTreeNode("-1 Chromebits", 0,0,0,1));
        emp.addToTree(new SkillTreeNode("+1 Hitchance", 0,0,1,0));

        Attack remoteControl = getAttack("Remote Control",player);
        remoteControl.description = "Hack your enemies to make their attacks your own";
        remoteControl.addToTree(new SkillTreeNode("-1 Chromebits", 0,0,0,1));
        remoteControl.addToTree(new SkillTreeNode("-1 Chromebits.", 0,0,0,1));
        remoteControl.addToTree(new SkillTreeNode("+1 Hitchance", 0,0,1,0));

        Attack recharge = getAttack("Recharge",player);
        recharge.description = "Heals yourself for " + recharge.healing + " health";
        recharge.addToTree(new SkillTreeNode("+1 Hitchance", 0,0,1,0));
        recharge.addToTree(new SkillTreeNode("-1 Chromebits", 0,0,0,1));
        recharge.addToTree(new SkillTreeNode("-1 Chromebits.", 0,0,0,1));
    }

    private void resetGame() {
        player.health = 500;
        player.maxHealth = 500;
        player.defense = 10;
        player.normalDefense = 10;
        player.level = 1;
        player.currentXp = 0;
        player.xpForNextLevel = (int) (5 * Math.pow(2, player.level));
        player.chromebits = 0;
        numKey = 0;

        player.attacks.clear();
        player.attacks.add(new Attack("Nanobots", 25, 1, 9, false, false,3,0,0));
        player.attacks.add(new Attack("Virus", 50, 2, 8, false, false, 10,0,0));
        player.attacks.add(new Attack("Firewall", 0, 1, 10, false, false, 5,0,5));
        player.attacks.add(new Attack("EMP", 0, 1, 7, true, false, 7,0,0));
        player.attacks.add(new Attack("Remote Control", 0, 1, 6, false, true, 4,0,0));
        player.attacks.add(new Attack("Recharge", 0, 2, 10, false, false, 6,30,0));

        setAttacks();

        for (Attack attack : player.attacks) {
            if (attack.initiallyBought) attack.attackBought = true;
        }

        for (ArrayList<Enemy> list : keyDefenders) {
            for (Enemy enemy : list) {
                enemy.defeated = false;
            }
        }

        battleResultLabel.setText("");
    }

    private void save() {
        try {
            String outFile = "src/main/resources/peopleInfo.txt";
            PrintWriter out = new PrintWriter(outFile);
            out.println(player.name);
            playedBefore = !player.name.equals("GERIGNJ");
            out.println(player.maxHealth);
            out.println(player.normalDefense);
            out.println(player.chromebits);
            out.println(player.bolts);
            out.println(player.level);
            out.println(player.currentXp);
            out.println(player.xpForNextLevel);
            out.println(player.chromeBitsPerTurn);
            out.println(numKey);

            for (Attack attack : player.attacks) {
                out.println(attack.attackBought);
                ArrayList<SkillTreeNode> totalUpgradeList = attack.attackTree.allNodes(attack.attackTree.head);
                for (SkillTreeNode upgrade : totalUpgradeList) { //I could use the list of bought upgrades but I wanted to keep the number of lines consistent for the load function
                    if (upgrade.bought) out.println(upgrade.upgradeName);
                    else out.println("");
                }
            }

            for (ArrayList<Enemy> arrayList: keyDefenders) {
                for (Enemy enemy : arrayList) {
                    out.println(enemy.defeated);
                }
            }
            out.close();
        } catch (FileNotFoundException var) {
            System.out.println("Doesn't Work");
        }
    }

    private void load() {
        try {
            String outFile = "src/main/resources/peopleInfo.txt";
            FileReader reader = new FileReader(outFile);
            Scanner fileReader = new Scanner(reader);
            while (fileReader.hasNextLine()) {
                player.name = fileReader.nextLine();
                playedBefore = !player.name.equals("GERIGNJ");
                player.maxHealth = Integer.parseInt(fileReader.nextLine());
                player.health = player.maxHealth;
                player.normalDefense = Integer.parseInt(fileReader.nextLine());
                player.defense = player.normalDefense;
                player.chromebits = Integer.parseInt(fileReader.nextLine());
                player.chromebits = 0;
                player.bolts = Integer.parseInt(fileReader.nextLine());
                player.level = Integer.parseInt(fileReader.nextLine());
                player.currentXp = Integer.parseInt(fileReader.nextLine());
                player.xpForNextLevel = Integer.parseInt(fileReader.nextLine());
                player.chromeBitsPerTurn = Integer.parseInt(fileReader.nextLine());
                numKey = Integer.parseInt(fileReader.nextLine());
                if (numKey == 4) {
                    gameEnded = true;
                }

                for (Attack attack : player.attacks) {
                    attack.attackBought = fileReader.nextLine().equals("true");
                    for (int i = 0; i < attack.attackTree.allNodes(attack.attackTree.head).size(); i++) {
                        SkillTreeNode nodeFound = attack.attackTree.find(attack.attackTree.head,fileReader.nextLine());
                        if (nodeFound != null) {
                            nodeFound.bought = true;
                        }
                    }
                }

                for (ArrayList<Enemy> arrayList: keyDefenders) {
                    for (Enemy enemy : arrayList) {
                        if (fileReader.nextLine().equals("true")) enemy.defeated = true;
                    }
                }
            }
        } catch (FileNotFoundException var) {
            System.out.println("Didn't Work");
        }
    }

    private void endBattle() {
        if (player.health <= 1) {
            inBattle = false;
            player.currentXp += currentEnemy.winXp/3;
            player.bolts += currentEnemy.boltsDropped/3;
            battleResultLabel.setText("You Lose! You got " + currentEnemy.winXp/3 + " xp and " + currentEnemy.boltsDropped/3 + " bolts!");
        } else if (currentEnemy.hitpoints <= 1) {
            inBattle = false;
            player.currentXp += currentEnemy.winXp;
            player.bolts += currentEnemy.boltsDropped;
            battleResultLabel.setText("You Win! You got " + currentEnemy.winXp + " xp and " + currentEnemy.boltsDropped + " bolts!");
            currentEnemy.defeated = true;
            enemiesList.getItems().remove(currentEnemy.name);
            if (enemiesList.getItems().size() == 0) {
                numKey += 1;
                if (numKey == 4) gameEnded = true;
            }
            endGame();
        }

        if (!inBattle) {
            while (player.currentXp > player.xpForNextLevel) {
                player.currentXp -= player.xpForNextLevel;
                player.level ++;
                player.maxHealth = (int) (player.maxHealth * 1.1);
                player.normalDefense = (int) (player.normalDefense * 1.1);
                player.xpForNextLevel = (int) (5 * Math.pow(2, player.level));
            }
            player.health = player.maxHealth;
            player.defense = player.normalDefense;
            player.chromebits = 0;
        }

        save();
        changeBattleUI();
    }

    private void changeBattleUI() {
        availableAttacksLabel.setVisible(inBattle);
        availableAttacksListview.setVisible(inBattle);
        endTurnBtn.setVisible(inBattle);
        healthLabel.setVisible(inBattle);
        defenseLabel.setVisible(inBattle);
        chromebitsLabel.setVisible(inBattle);
        playerHealthBarLabel.setVisible(inBattle);
        playerHealthBar.setVisible(inBattle);
        playerNameInBattle.setVisible(inBattle);
        enemyHealthBarLabel.setVisible(inBattle);
        enemyHealthBar.setVisible(inBattle);
        enemyAttackUsedLabel.setVisible(inBattle);
        enemyAttackResultLabel.setVisible(inBattle);
        dividingLine.setVisible(inBattle);
        resultOfAttack.setVisible(inBattle);
        playerImage.setVisible(inBattle);
        enemyImage.setVisible(inBattle);
        enemyNameLabel.setVisible(inBattle);
        battleStartBtn.setVisible(!inBattle);
    }

    private void updateBattleInfo() {
        availableAttacksListview.getItems().clear();
        enemiesList.getItems().clear();
        for (Attack gadget : player.attacks) {
            if (gadget.attackBought) {
                availableAttacksListview.getItems().add(gadget.attackName);
            }
        }
        for (Enemy enemy : keyDefenders.get(numKey)) {
            if (!enemy.defeated) enemiesList.getItems().add(enemy.name);
        }

        healthLabel.setText("Health: " + player.health + "/" + player.maxHealth);
        defenseLabel.setText("Defense: " + " " + player.defense);
        chromebitsLabel.setText("Chromebits: " + " " + player.chromebits);
        playerHealthBar.setProgress((double) player.health / player.maxHealth);
        enemyHealthBar.setProgress((double) currentEnemy.hitpoints / currentEnemy.maxHitpoints);
        enemyNameLabel.setText(currentEnemy.name);
        enemyClassLabel.setText("Enemies: " + stages[numKey]);
        describeEnemiesBox.setText(descriptions[numKey]);
        playerNameInBattle.setText(player.name);

        setImage("src/main/resources/pics/playerImage.jpg",playerImage);
        setImage("src/main/resources/pics/enemyImage.jpg",enemyImage);

        battleStartBtn.setVisible(!gameEnded);
    }

    private void updateUgradeScreen() {
        ArrayList<SkillTreeNode> availableUpgradesList = currentAttack.attackTree.availableUpgrades(currentAttack.attackTree.head);
        ArrayList<SkillTreeNode> boughtUpgradesList = currentAttack.attackTree.unlockedUpgrades(currentAttack.attackTree.head);
        availableUpgrades.getItems().clear();
        boughtUpgrades.getItems().clear();
        availableUpgrades.setDisable(inBattle);
        for (SkillTreeNode upgrade : availableUpgradesList) {
            availableUpgrades.getItems().add(upgrade.upgradeName + "    Cost: " + upgrade.cost + " bolts");
        }
        for (SkillTreeNode upgrade : boughtUpgradesList) {
            boughtUpgrades.getItems().add(upgrade.upgradeName + "    Cost: " + upgrade.cost + " bolts");
        }
        attackDescriptionBox.setText(currentAttack.description);
        attackCost.setText("Cost: " + currentAttack.cost + " chromebits");
        attackDamage.setText("Damage: " + currentAttack.damage);
        attackHitChance.setText("Chance Of Hitting: " + (int) currentAttack.chanceOfHitting + "/10");
        attackNumberOfTurns.setText("Number Of Attacks: " + currentAttack.numTurns);

        if (currentAttack.attackName.equals("Nanobots")) setImage("src/main/resources/pics/Nanobots.jpg",attackImage);
        else if (currentAttack.attackName.equals("Virus")) setImage("src/main/resources/pics/Virus.jpg",attackImage);
        else if (currentAttack.attackName.equals("Firewall")) setImage("src/main/resources/pics/Firewall.jpg",attackImage);
        else if (currentAttack.attackName.equals("EMP")) setImage("src/main/resources/pics/EMP.jpg",attackImage);
        else if (currentAttack.attackName.equals("Remote Control")) setImage("src/main/resources/pics/RemoteControl.jpg",attackImage);
        else if (currentAttack.attackName.equals("Recharge")) setImage("src/main/resources/pics/Recharge.png",attackImage);

        availableUpgrades.setVisible(currentAttack.attackBought);
        boughtUpgrades.setVisible(currentAttack.attackBought);
        attackDescriptionBox.setVisible(currentAttack.attackBought);
        attackCost.setVisible(currentAttack.attackBought);
        attackDamage.setVisible(currentAttack.attackBought);
        attackHitChance.setVisible(currentAttack.attackBought);
        attackNumberOfTurns.setVisible(currentAttack.attackBought);
        attackDescription.setVisible(currentAttack.attackBought);
        boughtUpgradesLabel.setVisible(currentAttack.attackBought);
        availableUpgradesLabel.setVisible(currentAttack.attackBought);
        buyAttackBtn.setVisible(!currentAttack.attackBought);
        attackImage.setVisible(currentAttack.attackBought);

        playerStatsLabel.setText(player.name + "'s Stats");
        playerBoltsLabel.setText("Bolts: " + player.bolts);
        playerDefenseLabel.setText("Defense: " + player.defense);
        playerHealthLabel.setText("Health: " + player.maxHealth);
        playerLevelLabel.setText("Level: " + player.level);
        levelProgress.setProgress((double) player.currentXp / player.xpForNextLevel);

        youWinLabel2.setVisible(gameEnded);
        restartGameBtn2.setVisible(gameEnded);
    }

    private void enableConfirmInfoBtn() {
        if (characterNameText.getText().length() > 0 && pickedGadgets.getItems().size() == 3) {
            confirmInfoBtn.setDisable(false);
        } else {
            confirmInfoBtn.setDisable(true);
        }
    }

    private int rNum(int lower, int upper) {
        return (int) (Math.random()*(upper-lower+1)) + lower;
    }

    private Attack getAttack(String attackName, Hero person) {
        for (Attack attack : person.attacks) {
            if (attack.attackName.equals(attackName)) return attack;
        }
        return null;
    }

    private Enemy getEnemy(String enemyName, ArrayList<ArrayList<Enemy>> list) {
        for (Enemy enemy : list.get(numKey)) {
            if (enemy.name.equals(enemyName)) {
                return enemy;
            }
        }
        return null;
    }

    private void setImage(String filePath, ImageView img) {
        try {
            FileInputStream inp = new FileInputStream(filePath);
            img.setImage(new Image(inp));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void endGame() {
        availableUpgrades.setVisible(!gameEnded);
        boughtUpgrades.setVisible(!gameEnded);
        attackDescriptionBox.setVisible(!gameEnded);
        attackCost.setVisible(!gameEnded);
        attackDamage.setVisible(!gameEnded);
        attackHitChance.setVisible(!gameEnded);
        attackNumberOfTurns.setVisible(!gameEnded);
        attackDescription.setVisible(!gameEnded);
        boughtUpgradesLabel.setVisible(!gameEnded);
        availableUpgradesLabel.setVisible(!gameEnded);
        buyAttackBtn.setVisible(!gameEnded);
        attackImage.setVisible(!gameEnded);
        availableAttacksLabel.setVisible(!gameEnded);
        availableAttacksListview.setVisible(!gameEnded);
        endTurnBtn.setVisible(!gameEnded);
        healthLabel.setVisible(!gameEnded);
        defenseLabel.setVisible(!gameEnded);
        chromebitsLabel.setVisible(!gameEnded);
        playerHealthBarLabel.setVisible(!gameEnded);
        playerHealthBar.setVisible(!gameEnded);
        playerNameInBattle.setVisible(!gameEnded);
        enemyHealthBarLabel.setVisible(!gameEnded);
        enemyHealthBar.setVisible(!gameEnded);
        enemyAttackUsedLabel.setVisible(!gameEnded);
        enemyAttackResultLabel.setVisible(!gameEnded);
        dividingLine.setVisible(!gameEnded);
        resultOfAttack.setVisible(!gameEnded);
        playerImage.setVisible(!gameEnded);
        enemyImage.setVisible(!gameEnded);
        enemyNameLabel.setVisible(!gameEnded);
        enemyClassLabel.setVisible(!gameEnded);
        enemiesList.setVisible(!gameEnded);
        describeEnemiesBox.setVisible(!gameEnded);
        playerStatsLabel.setVisible(!gameEnded);
        playerHealthLabel.setVisible(!gameEnded);
        playerDefenseLabel.setVisible(!gameEnded);
        playerBoltsLabel.setVisible(!gameEnded);
        playerLevelLabel.setVisible(!gameEnded);
        levelProgress.setVisible(!gameEnded);
        gadgetsSelection.setVisible(!gameEnded);
        buyAttackBtn.setVisible(!gameEnded);
        playerStatsRectangle.setVisible(!gameEnded);
        battleResultLabel.setVisible(!gameEnded);
        gadgetsBox.setVisible(!gameEnded);
        battleStartBtn.setVisible(!gameEnded);

        youWinLabel.setVisible(gameEnded);
        youWinLabel2.setVisible(gameEnded);
        restartGameBtn.setVisible(gameEnded);
        restartGameBtn2.setVisible(gameEnded);
    }

    private String substringBeforeSpaces(String inp) {
        for (int i = 0; i < inp.length()-5; i++) {
            if (inp.substring(i,i+4).equals("Cost")) {
                return inp.substring(0, i-4);
            }
        }
        return null;
    }
}