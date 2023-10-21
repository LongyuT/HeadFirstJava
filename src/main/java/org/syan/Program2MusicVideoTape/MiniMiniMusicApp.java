package org.syan.Program2MusicVideoTape;

import com.sun.xml.internal.ws.message.source.PayloadSourceMessage;
import org.syan.Program1AttackGame.Message;

import javax.sound.midi.*;
import java.sql.Time;
import java.util.Scanner;

public class MiniMiniMusicApp {

    private static Sequencer player;
    public static void main(String[] args) throws InvalidMidiDataException, MidiUnavailableException, InterruptedException {
        MiniMiniMusicApp mini = new MiniMiniMusicApp();
        mini.init();
        mini.play();
    }

    public synchronized void play() throws InvalidMidiDataException, MidiUnavailableException, InterruptedException {
        player.open();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一共要播放几个音节: ");
        int n = sc.nextInt();
        Sequence seq = new Sequence(Sequence.PPQ, 4);
        Track track = seq.createTrack();
        int times = 0;
        for (; times < n; times++) {
            System.out.println("请输入音符0-127: ");
            int m = sc.nextInt();
            track.add(new MidiEvent(new ShortMessage(144, 1, m, 100), times * 4));
        }
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 1, 44, 100), times * 4));
        player.setSequence(seq);
        player.start();
        Thread.sleep(times * 1000);
        player.close();

    }

    public void init() {
        try {
            player = MidiSystem.getSequencer();
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }

    }


}
