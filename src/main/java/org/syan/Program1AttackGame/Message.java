package org.syan.Program1AttackGame;

public class Message {

    private DotCom.Status status = DotCom.Status.Alive;

    private boolean isHit;

    public DotCom.Status getStatus() {
        return status;
    }

    public void setStatus(DotCom.Status status) {
        this.status = status;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }
}
