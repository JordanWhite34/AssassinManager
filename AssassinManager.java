// Jordan White
// 2/7/2021
// Take Home Assessment 3
//
// AssassinManager monitors who is in the Kill Ring and who is in the
// Graveyard and is responsible for knowing who killed who and updating the
// records based on who is assassinated.

import java.util.*;

public class AssassinManager {
    private AssassinNode frontKill;
    private AssassinNode frontGrave;

    // Constructs a list containing the given list of names, but if the list
    // of names is empty then this will throw IllegalArgumentException
    public AssassinManager(List<String> names) throws IllegalArgumentException {
        if (names.size() == 0) {
            throw new IllegalArgumentException();
        }
        frontKill = new AssassinNode(names.get(0));
        AssassinNode current = frontKill;
        for (int i = 1; i < names.size(); i ++) {
            current.next = new AssassinNode(names.get(i));
            current = current.next;
        }
    }

    // Takes name of who is to be killed and moves that person to the graveyard from the kill ring
    // while ignoring casing and also keeps track of who killed that person while throwing
    // IllegalStateException if the game is over, throwing IllegalArgumentException if the name is
    // not in the kill ring, and throws IllegalStateException if both are true.
    public void kill(String name) throws IllegalStateException, IllegalArgumentException {
        if (gameOver()) {
            throw new IllegalStateException();
        }
        if (!killRingContains(name)) {
            throw new IllegalArgumentException();
        }
        AssassinNode currentKill = frontKill;
        while (currentKill.next != null && !currentKill.next.name.equalsIgnoreCase(name)) {
            currentKill = currentKill.next;
        }
        AssassinNode temp = frontGrave;
        String killerName = currentKill.name;
        if (currentKill.next == null) {
            frontGrave = frontKill;
            frontKill = frontKill.next;
        } else {
            frontGrave = currentKill.next;
            currentKill.next = currentKill.next.next;
        }
        frontGrave.killer = currentKill.name;
        frontGrave.next = temp;
    }

    // Prints who is in the kill ring, reporting who is stalking who
    public void printKillRing() {
        AssassinNode current = frontKill;
        while (current != null) {
            System.out.print("    " + current.name + " is stalking ");
            if (current.next != null) {
                System.out.println(current.next.name);
            } else {
                System.out.println(frontKill.name);
            }
            current = current.next;
        }
    }

    // Prints who is in the graveyard, reporting who was killed by who
    public void printGraveyard() {
        AssassinNode current = frontGrave;
        while (current != null) {
            System.out.println("    " + current.name + " was killed by " + current.killer);
            current = current.next;
        }
    }

    // Takes name of person and checks while ignoring casing if that person is in the kill ring
    // or not, returning true or false depending if they are in it or not
    public boolean killRingContains(String name) {
        AssassinNode current = frontKill;
        return generalContains(name, current);
    }

    // Takes name of person and checks while ignoring casing if that person is in the graveyard
    // or not, returning true or false depending if they are in it or not
    public boolean graveyardContains(String name) {
        AssassinNode current = frontGrave;
        return generalContains(name, current);
    }

    // Helper method for killRingContains and graveyardContains, checking while ignoring casing if
    // the name is in the given list of names
    private boolean generalContains(String name, AssassinNode current) {
        while (current != null) {
            if (current.name.equalsIgnoreCase(name)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Checks to see if there is only one person left in the kill ring,
    // and if there is then this will return true but will otherwise return false
    public boolean gameOver() {
        return (frontKill.next == null);
    }

    // Checks if the game is over to give the name of the winner, but if the game
    // is not over this will give an empty value. If the game is not over, null (the empty value)
    // will be returned
    public String winner() {
        if (!gameOver()) {
            return null;
        } else {
            return frontKill.name;
        }
    }
}
