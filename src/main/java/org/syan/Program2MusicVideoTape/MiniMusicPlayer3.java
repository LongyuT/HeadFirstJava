package org.syan.Program2MusicVideoTape;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class MiniMusicPlayer3 {

    JFrame frame;

    MyDrawPanel myDrawPanel;

    public static void main(String[] args) {
        MiniMusicPlayer3 player3 = new MiniMusicPlayer3();
        player3.init();
        player3.go();
    }

    public void init () {

    }

    public void go() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myDrawPanel = new MyDrawPanel();
        frame.setContentPane(myDrawPanel);
        frame.setBounds(30, 30, 300, 300);
        frame.setVisible(true);
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addControllerEventListener(myDrawPanel, new int[]{127});
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            for (int i = 5; i < 60; i += 4) {
                int r = (int) (Math.random() * 50 + 1);
                track.add(makeEvent(144, 1, r, 100, i));
                track.add(makeEvent(176, 1, 127, 0, i));
                track.add(makeEvent(128, 1, r, 100, i + 2));
            }
            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(120);
            sequencer.start();

        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
    }

    public  MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        }
        catch (Exception e) {}
        return event;
    }
}
