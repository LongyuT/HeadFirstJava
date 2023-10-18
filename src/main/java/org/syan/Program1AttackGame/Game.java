package org.syan.Program1AttackGame;

import java.util.ArrayList;

public class Game {
    static ArrayList<DotCom> dotComs;

    static int times = 0;
    static Helper helper;
    public static void main(String[] args) {
        initGame();
        GameStart();
    }

    public static void initGame() {
        dotComs = new ArrayList<>();
        helper = new Helper();
        System.out.println("正在生成第1艘战舰");
        String[] names = helper.generateName();
        dotComs.add(new DotCom(helper.generatePos(), names[0]));
        System.out.println("正在生成第2艘战舰");
        dotComs.add(new DotCom(helper.generatePos(), names[1]));
        System.out.println("正在生成第3艘战舰");
        dotComs.add(new DotCom(helper.generatePos(), names[2]));
//        System.out.println(dotComs.get(0).getDotComName() + ":" + dotComs.get(0).getPosition().get(0) + dotComs.get(0).getPosition().get(1) + dotComs.get(0).getPosition().get(2));
//        System.out.println(dotComs.get(1).getDotComName() + ":" + dotComs.get(1).getPosition().get(0) + dotComs.get(1).getPosition().get(1) + dotComs.get(1).getPosition().get(2));
//        System.out.println(dotComs.get(2).getDotComName() + ":" + dotComs.get(2).getPosition().get(0) + dotComs.get(2).getPosition().get(1) + dotComs.get(2).getPosition().get(2));
    }

    public static void GameStart() {
        System.out.println("游戏正式开始");
        helper.GameStartOutput();
        while (!dotComs.isEmpty()) {
            String pos = helper.round();
            times++;
            boolean flag = false;
            for (int i = 0; i < dotComs.size(); i++) {
                DotCom dotCom = dotComs.get(i);
                Message message = dotCom.checkSelf(pos);
                if (message.isHit()) {
                    flag = true;
                    System.out.println("Hit");
                }
                if (message.getStatus() == DotCom.Status.Killed) {
                    System.out.println("You killed " + dotCom.getDotComName());
                    dotComs.remove(dotCom);
                    break;
                }
            }
            if (!flag)
                System.out.println("Miss");
        }
        System.out.println("游戏结束, 一共发射了 " + times + " 次");
    }

}
