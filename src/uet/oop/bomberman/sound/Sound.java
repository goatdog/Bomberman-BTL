package uet.oop.bomberman.sound;

import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.ArrayList;
import java.util.List;


public class Sound {
        private static List<Pair<Clip, String>> clipList = new ArrayList<Pair<Clip, String>>();
        public static void play(String sound) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Clip clip = AudioSystem.getClip();
                        if (BombermanGame.hasSound == true) {
                            boolean tmp = false;
                            for (int i = 0; i < clipList.size(); i++) {
                                if (clipList.get(i).getValue().equals(sound)) {
                                    clipList.get(i).getKey().start();
                                    tmp = true;
                                    break;
                                }
                            }
                            if (tmp == false) {
                                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                                        Main.class.getResourceAsStream("/sound/" + sound + ".wav"));
                                clip.open(inputStream);
                                clip.start();
                                if (sound.equals("Sound")) clipList.add(new Pair<>(clip, sound));
                            }
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
                            if (clipList.get(i).getValue().equals(sound)) {
                                System.out.println(clipList.get(i).getValue() + " " + sound);
                                clipList.get(i).getKey().stop();
                            }
                        }
                        //clipList.clear();
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
            }).start();
        }
    }
