package ru.ifmo.se.pokemon;

import java.util.*;

/**
 * ����� ��� ���������� ��� ����� ����������.
 * ��� ���������� �������� � ������� ������������ ������ addAlly() � addFoe()
 * ��� ������ ��� ������� ����� go()
 */
public final class Battle {
    private final int SIZE = 1;
    private final Queue<Pokemon> allies = new LinkedList<Pokemon>();
    private final Queue<Pokemon> foes = new LinkedList<Pokemon>();

    public void addAlly(Pokemon p) {
        allies.add(p);
    }

    public void addFoe(Pokemon p) {
        foes.add(p);
    }

    private boolean checkFirst(Pokemon ally, Pokemon foe) {
        if (ally.getPreparedMove().getPriority() == foe.getPreparedMove().getPriority()) {
            if (ally.getStat(Stat.SPEED) == foe.getStat(Stat.SPEED)) {
                 return Math.random() >= 0.5;
            } else {
                return ally.getStat(Stat.SPEED) > foe.getStat(Stat.SPEED);
            }
        } else {
            return ally.getPreparedMove().getPriority() > foe.getPreparedMove().getPriority();
        }
    }

    public void go() {
        Pokemon ally = null;
        Pokemon foe = null;
        while (true) {
            if (ally == null || !ally.isAlive()) {
                ally = allies.poll();
                ally.restore();
                System.out.println("� ��� �� ��������� �������� " + ally);
            }
            if (foe == null || !foe.isAlive()) {
                foe = foes.poll();
                foe.restore();
                System.out.println("� ��� �� �������� �������� " + foe);
            }
            while (true) {
                ally.prepareMove();
                foe.prepareMove();
                if (checkFirst(ally, foe)) {
                    if (ally.attack(foe)) {
                        break;
                    }
                    if (foe.attack(ally)) {
                        break;
                    }
                } else {
                    if (foe.attack(ally)) {
                        break;
                    }
                    if (ally.attack(foe)) {
                        break;
                    }
                }
                ally.turn(); if (!ally.isAlive()) break;
                foe.turn(); if (!foe.isAlive()) break;
            }
            if (allies.isEmpty() && !ally.isAlive()) {
                if (foes.isEmpty() && !foe.isAlive()) {
                    System.out.println("� ����� �������� �� �������� ���������. �����!");
                    break;
                } else {
                    System.out.println("��������� ������������ ������� ��������. ������� ���������!");
                    break;
                }
            } else {
                if (foes.isEmpty() && !foe.isAlive()) {
                    System.out.println("� ������� �������� �� �������� ���������. �������� ����������!");
                    break;
                }
            }
        }
    }
}
