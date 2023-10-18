package org.syan.Program1AttackGame;

import java.util.ArrayList;

public class DotCom {
    private ArrayList<String> position;

    private String DotComName;
    public enum Status {
        Alive,
        Killed;
    }

    public String getDotComName() {
        return DotComName;
    }

    public void setDotComName(String dotComName) {
        DotComName = dotComName;
    }

    private Status status = Status.Alive;

    public ArrayList<String> getPosition() {
        return position;
    }

    public void setPosition(ArrayList<String> position) {
        this.position = position;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Message checkSelf(String pos) {
        Message message = new Message();
        if (position.contains(pos)) {
            position.remove(pos);
            message.setHit(true);
        }
        else {
            message.setHit(false);
        }
        if (position.size() == 0) {
            status = Status.Killed;
            message.setStatus(Status.Killed);
        }
        return message;
    }

    public DotCom(ArrayList<String> position, String dotComName) {
        this.position = new ArrayList<>(position);
        DotComName = dotComName;
    }

    public DotCom() {
    }
}
