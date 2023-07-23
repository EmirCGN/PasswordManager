package de.emir.passwordmanager.ui;

public class BackgroundThread extends Thread {
    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(5000); // Warte 5 Sekunden zwischen den Hintergrund-Aufgaben
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}