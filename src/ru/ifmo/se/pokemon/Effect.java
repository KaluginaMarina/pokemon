package ru.ifmo.se.pokemon;

/**
 *  �����, �������������� ����� ������, �������������� �� ��������.
 *  �������� ������ ����� �����, ��������������� �������� ���������������. � �� �������� ������������ �������������
 *  ��������. ��� ���� �������������, ����� ����� �������� (HP), ����������� ����� ���� �� -6 �� +6.
 *  �� ����������, �� ������� �������� (stages) ���������� ��������������.
 *  ���� ������������� �������� �������� (�� �����������), ������������� ����� - ������������ �������� ��������������,
 *  ������������� - �����������.
 *
 *  ���� �� �������� ��������� ������������ ��������� ��������, �� ������������ ������������, �� ��������� ��������
 *  �� ������ �������� �� �������� �� -6 �� 6.
 *
 *  ����� �������������, ������ ����� ��������:
 *  - ����������������� ������� � ����� (0 - ������ ��������� ���������� � ����� ���������, -1 - ������ ��������� ���������)
 *  - ���� ������� - ����������� ����, ��� ��� ��������� ���� ������ ���������. �� ��������� - 100%
 *  - ����, ��� ������� ����� ��������� �� ����� ������ ���� (�� 0 (�� �������) �� 1 (����������� �������))
 *  - ��������� �������� (�� ������������ Status)
 */
public final class Effect {
    private int[] mods = new int[Stat.values().length];
    private int turns = 0;
    private double effectChance = 1.0;
    private double attackChance = 1.0;
    private Status condition = Status.NORMAL;

    /**
     * �����������
     *
     */
    public Effect() {
    }

    /**
     * ������������� ����������������� ������� � �����
     * @param turns  ���������� �����, � ������� ������� ������ ����� �������������
     *               0 - ������ ������ ��������� ����� � ������������
     *               < 0 - ������ ����� ����������� ���������, ���� �� ����� ���� ��� ������� ������
     * @return  ������ (����� ����� ���� ������������ ��������� ������� ��������� ����������
     */
    public final Effect turns(int turns) {
        this.turns = turns;
        return this;
    }

    /**
     * ������������� ����������� ������������ �������
     * @param chance  �����������, ���� 1 - �� ������ ����� ������ �����������.
     * @return  ������ (����� ����� ���� ������������ ��������� ������� ��������� ����������
     */
    public final Effect chance(double chance) {
        effectChance = chance;
        return this;
    }

    /**
     * ������������� ����������� ������������ ����� �������� �� ����� �������� �������.
     * �� ��������� - 1.
     * ����� � ��� �������, ����� ���������� ������ ��������� ��� � ��������� ������������ �� ���� �������� ���������.
     *
     * @param chance  �����������, 0 - ������� �� ����� ���������, ���� ��������� ������
     * @return  ������ (����� ����� ���� ������������ ��������� ������� ��������� ����������
     */
    public final Effect attack(double chance) {
        this.attackChance = chance;
        return this;
    }

    /**
     * ���������� ����������� ����� �������� �� ����� �������� �������
     * @return
     */
    public final double attack() {
        return attackChance;
    }

    /**
     * ������������� ��������� ��������
     *
     * @param condition ��������� (������������ Status)
     * @return  ������ (����� ����� ���� ������������ ��������� ������� ��������� ����������
     */
    public final Effect condition(Status condition) {
        this.condition = condition;
        return this;
    }

    /**
     * ������� ������������ �������. ����� ����� ������ ��������� ����������� �� ��������
     */
    public final void clear() {
        for (Stat s : Stat.values()) {
            mods[s.ordinal()] = 0;
        }
        condition = Status.NORMAL;
        turns = 0;
        effectChance = 1.0;
        attackChance = 1.0;
    }

    /**
     * ���������� ��������� ��������
     * @return  ���������
     */
    public final Status condition() {
        return condition;
    }

    /**
     * ���������� ����������� �������� ��������������
     * @param stat  �������������� (������������ Stat)
     * @return  �������� ��������������
     */
    public final int stat(Stat stat) {
        return mods[stat.ordinal()];
    }

    /**
     * ������������� �������� ��������������
     * @param stat  �������������� (������������ Stat)
     * @param value  �������� ��������������
     * @return  ������ (����� ����� ���� ������������ ��������� ������� ��������� ����������
     */
    public final Effect stat(Stat stat, int value) {
        if (stat != Stat.HP) {
            if (value >= 0 & value > 6) value = 6;
            if (value < 0 & value < -6) value = -6;
        }
        mods[stat.ordinal()] = value;
        return this;
    }

    /**
     * �������� �� ������������ �������
     * @return  true � ������������ ������������ �������
     */
    public final boolean success() {
        return effectChance > Math.random();
    }

    /**
     *  �������� �� �������������
     * @return  true, ���� ������ ������ ���� �������� ����� � ����������
     */
    public final boolean immediate() {
        return turns == 0;
    }

    /**
     *  ���������� � ����� ���������� ����
     * @return  true, ���� ������ �������� �������� ����� ����� ����
     */
    public final boolean turn() {
        return --turns == 0;
    }

    /**
     * ����������� ����� ��������� �� �������� ������� �������������. �� ��������� �� �������� ���������.
     * ����� ����������� �� 2 �������, ������ ��� ������� ����� ������ 1/16 HP
     * @param p     �������, �� �������� ����� ������� ������
     */
    public static void burn(Pokemon p) {
        if (!p.hasType(Type.FIRE)) {
            Effect e = new Effect().condition(Status.BURN).turns(-1);
            e.stat(Stat.ATTACK, -2).stat(Stat.HP, (int) p.getStat(Stat.HP) / 16);
            p.setCondition(e);
        }
    }
    /**
     * ����������� ����� ��������� �� �������� ������� �����������. �� ��������� �� ������������� ���������.
     * �������� ����������� �� 2 �������, � ������������ 25% ������� �� ����� ���������
     * @param p     �������, �� �������� ����� ������� ������
     */
    public static void paralyze(Pokemon p) {
        if (!p.hasType(Type.ELECTRIC)) {
            Effect e = new Effect().condition(Status.PARALYZE).attack(0.75).turns(-1);
            e.stat(Stat.SPEED, -2);
            p.setCondition(e);
        }
    }
    /**
     * ����������� ����� ��������� �� �������� ������� ���������. �� ��������� �� ������� ���������.
     * ������� ��������� ���������
     * @param p     �������, �� �������� ����� ������� ������
     */
    public static void freeze(Pokemon p) {
        if (!p.hasType(Type.ICE)) {
            Effect e = new Effect().condition(Status.FREEZE).attack(0).turns(-1);
            p.setCondition(e);
        }
    }
    /**
     * ����������� ����� ��������� �� �������� ������� ����������. �� ��������� �� �������� � �������� ���������
     * ������ ��� ������� ���������� ���� � 1/8 ��� HP
     * @param p     �������, �� �������� ����� ������� ������
     */
    public static void poison(Pokemon p) {
        if (!p.hasType(Type.POISON) && !p.hasType(Type.STEEL)) {
            Effect e = new Effect().condition(Status.POISON).turns(-1);
            e.stat(Stat.HP, (int) p.getStat(Stat.HP) / 8);
            p.setCondition(e);
        }
    }
    /**
     * ����������� ����� ��������� �� �������� ������� ���. ��������� �� 1 �� 3 �����. ������ ������� �� �������.
     * @param p     �������, �� �������� ����� ������� ������
     */
    public static void sleep(Pokemon p) {
        Effect e = new Effect().condition(Status.SLEEP).attack(0).turns((int)(Math.random() * 3 + 1));
        p.setCondition(e);
    }

    /**
     * ����������� ������ flinch. ���� ��� ������� �� �������.
     * @param p     �������, �� �������� ����� ������� ������
     */
    public static void flinch(Pokemon p) {
        Effect e = new Effect().attack(0).turns((int)(Math.random() * 4 + 1));
        p.addEffect(e);
    }

    /**
     * �������� � �������� �������������.
     * @param p
     */
    public static void confuse(Pokemon p) {
        p.confuse();
    }
}
