package org.syan.Program1AttackGame;

import java.util.*;

public class Helper {

    int length = 7;
    boolean[][] grid = new boolean[length][length];
    public String[] generateName() {
        Random random = new Random();
        List<String> nameList = new ArrayList<>(Arrays.asList("John", "Michael", "Emily", "Emma", "Sophia", "Olivia"));
        String[] names = new String[3];
        names[0] = nameList.get(random.nextInt(nameList.size()));
        nameList.remove(names[0]);
        names[1] = nameList.get(random.nextInt(nameList.size()));
        nameList.remove(names[1]);
        names[2] = nameList.get(random.nextInt(nameList.size()));
        nameList.remove(names[2]);
        return names;
    }

    public ArrayList<String> generatePos() {
        boolean flag = false;
        ArrayList<String> pos = new ArrayList<>();
        while (!flag) {
            Random random = new Random();
            int x = random.nextInt(5), y = random.nextInt(5);
            if (grid[x][y] == true)
                continue;
            if (grid[x + 1][y] == false && grid[x + 2][y] == false) {
                grid[x][y] = true;
                pos.add(x++ + " " + y);
                grid[x][y] = true;
                pos.add(x++ + " " + y);
                grid[x][y] = true;
                pos.add(x + " " + y);
                flag = true;
            }
            else if (grid[x][y + 1] == false && grid[x][y + 2] == false) {
                grid[x][y] = true;
                pos.add(x + " " + y++);
                grid[x][y] = true;
                pos.add(x + " " + y++);
                grid[x][y] = true;
                pos.add(x + " " + y);
                flag = true;
            }
        }
        return pos;
    }

    public void GameStartOutput() {

    }

    public String round() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入攻击方位横坐标x: ");
        int x = sc.nextInt();
        System.out.println("请输入攻击方位纵坐标y: ");
        int y = sc.nextInt();
        String position = x + " " + y;
        return position;
    }
}
