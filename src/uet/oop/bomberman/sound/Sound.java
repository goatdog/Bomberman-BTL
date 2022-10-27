package uet.oop.bomberman.sound;

import uet.oop.bomberman.BombermanGame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.ArrayList;
import java.util.List;


public class Sound {
        private static List<Clip> clipList = new ArrayList<Clip>();
        public static void play(String sound) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Clip clip = AudioSystem.getClip();
                        if (BombermanGame.hasSound == true) {
                            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                                    Main.class.getResourceAsStream("/sound/" + sound + ".wav"));
                            clip.open(inputStream);
                            clip.start();
                            clipList.add(clip);
                        } else {
                            clip.stop();
                        }
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
            }).start();
        }

        public static void stop(String sound) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Clip c = AudioSystem.getClip();
                        for (int i = 0; i < clipList.size(); i++) {
                            c = clipList.get(i);
                            c.stop();
                        }
                        clipList.clear();
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
            }).start();
        }
    }
