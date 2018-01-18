package ru.ifmo.se.pokemon;

import java.util.*;

/**
 *  ������� ����� ��� ���������. ��� �������� ������ ������������� �� ����.
 *  ������� ����� ���, ������������ �������-��������.
 *  ������� ����� ��� (������), ������� �������� � ������������ �������� ������.
 *  ������� ����� �������, �� �������� ������� ��� ����������� ��������������.
 *  ������� ����� ����� ���� ��� ��� ����, ������� ����� ������ � ������� ������ setType()
 *  ������� ����� ����� ��������� �� ������� ���� ���� Move
 *  ������� ����� ����� �� ������� �������������, ������� ��������������� ������� setStats(). ��� �� ����������.
 *  ��� ������ ������� �������������� ������� ����� �����������, ������� ����������,
 *  �� ������� �������� � ������ ������ �������� ������ ��������������.
 *  ������� ����� ������� �������� ����� �������� (HP). �� ����� ��� ������� �������� �����������.
 *  ����� �������� ���������� ����������� ���������� ������ HP, ������� ������ �������� � �������� �� ���.
 */
public class Pokemon {
    protected String name;
    private Type[] types;
    private Move[] moves = {Move.getStruggleMove()};
    private Move preparedMove;
    private Effect stage = new Effect();
    private Effect condition = new Effect();
    private List<Effect> effects = new LinkedList<Effect>();
    private int confusion;

    protected int level = 60;
    //    double xp = 0;
//    double baseXP = 100;
    private double[] base = new double[Stat.values().length]; // ������� ��������������

    /**
     * ����������� ������ ��� ��������
     * @param name
     */
    public Pokemon(String name) {
        this.name = name;
    }

    /**
     * ������������� ������� �������� ������������� ��������
     *
     * @param hp    ���� �������� (HP - health points)
     * @param att   ����� (�������)
     * @param def   ������ (�������)
     * @param spAtt ����������� �����
     * @param spDef ����������� ������
     * @param speed ��������
     */
    public final void setStats(double hp, double att, double def, double spAtt, double spDef, double speed) {
        base[Stat.HP.ordinal()] = hp;
        base[Stat.ATTACK.ordinal()] = att;
        base[Stat.DEFENSE.ordinal()] = def;
        base[Stat.SPECIAL_ATTACK.ordinal()] = spAtt;
        base[Stat.SPECIAL_DEFENSE.ordinal()] = spDef;
        base[Stat.SPEED.ordinal()] = speed;
    }

    /**
     * ���������� ����������� ������ �������� �������������� �������� � ������ ��� ������
     * � �������� ����������� �� �������� ��������
     *
     * @param stat ������ �������������� - ���� �� �������� ������������ Stat
     * @return ����������� �������� ��������������
     * @see Stat
     */
    public final double getStat(Stat stat) {
        double iv = 15.0;   // individual value (nwo constant, can be changed in future release
        double ev = 0.0;    // effort value (now zero, an be changed in future release
        double value = base[stat.ordinal()];
        double mod = this.stage.stat(stat);
        mod += condition.success() ? condition.stat(stat) : 0;        // condition effect
        for (Effect e : effects) {          // other effects
            mod += e.success() ? e.stat(stat) : 0;
        }
        if (Math.abs(mod) > 6.0) {          // max mod is +/-6
            mod = mod > 0 ? 6 : -6;
        }
        double adder = stat.isHidden() ? 0.0 : (stat == Stat.HP ? level + 10.0 : 5.0);
        double div = stat.isHidden() ? 3.0 : 2.0;
        value *= stat == Stat.HP ? 1.0 : (mod > 0 ? (div + mod) / div : div / (div + mod));
        value = stat.isHidden() ? value : (value * 2.0 + iv + Math.sqrt(ev) / 4.0) * level / 100.0;
        value += adder;
        return value;
    }

    /**
     * ���������� ������� ������������� ���� � ������� ��������
     *
     * @param type ����������� ���
     * @return true, ���� � �������� ���� ������ ���; false, ���� ���
     */
    public final boolean hasType(Type type) {
        for (Type t : types) {
            if (t == type) {
                return true;
            }
        }
        return false;
    }

    /**
     * ��������� ������, �������� �� ��������.
     * ��� ��������� ��������� ������� ������������ ����� setCondition(Effect)
     * ��������� ����� ���� ������ ����, ��������� ������ ��������� �������� ������.
     * ������� ����� �������������.
     *
     * @param e ������
     */
    public final void addEffect(Effect e) {
        if (e.condition() != Status.NORMAL) {
            effects.add(e);
        } else {
            setCondition(e);
        }
    }

    /**
     * ������������� ��������� ��������.
     * ����� ���� ������ �� 6 �����: ����������, �������, ���������, �������, ����������, ���.
     * ����� ��������� �������� ����������.
     * @param e
     */
    public final void setCondition(Effect e) {
        condition = e;
        String message = "";
        switch (e.condition()) {
            case BURN:
                message = " ��������������";
                break;
            case FREEZE:
                message = " ���������";
                break;
            case PARALYZE:
                message = " �����������";
                break;
            case POISON:
                message = " ��������";
                break;
            case SLEEP:
                message = " ��������";
                break;
        }
        System.out.println(this + message);
    }

    /**
     *  ������������� ��������� ������������� ��������. ������ �� 1 �� 4 �����.
     *  � ���� ��������� ������� � ������������ 33% ������ ����� ��������� ������� ���� ���������� ����� �����.
     */
    public void confuse() {
        confusion = (int) (Math.random() * 4 + 1);
    }

    /**
     *  ��������������� �������� ��������� ��������. ��������� ��� �����������, ���������� ������������.
     *  �������� ������� � ���������. �������� � ������������ ������������ �� 100%
     */
    public final void restore() {
        base[Stat.ACCURACY.ordinal()] = 1.0;
        base[Stat.EVASION.ordinal()] = 1.0;
        condition.clear();
        stage.clear();
        effects.clear();
    }

    /**
     * ���������� ���������� ������� HP � ������ �����������.
     * @return
     */
    public double getHP() {
        return getStat(Stat.HP) - stage.stat(Stat.HP);
    }

    /**
     * ������������� ����������� ��������������.
     * ��� HP ����������� � �������� ����������� (������������� - ������� ������ HP, ������������� - ���������������)
     * ��� ��������� ������������� - ������ ���������� ��������, �� ������� �������� ��������������
     * @param stat
     * @param value
     */
    public final void setMod(Stat stat, int value) {
        int newValue = value + stage.stat(stat);
        if (stat == Stat.HP) {
            if (value > 0) {
                System.out.println(this + " ������ " + value + " HP.");
            }
            if (value < 0) {
                System.out.println(this + " ��������������� " + value + " HP.");
            }
        } else {
            if (Math.abs(newValue) > 6) {
                newValue = newValue > 0 ? 6 : -6;
            }
            if (value > 0) {
                System.out.println(this + " �������� �������������� " + stat);
            }
            if (value < 0) {
                System.out.println(this + " �������� �������������� " + stat);
            }
        }
        stage.stat(stat, newValue);
    }

    /**
     * ���������� ������ ����� ��������
     * @return
     */
    public final Type[] getTypes() {
        return types;
    }

    /**
     * ���������� ������� ��������
     * @return
     */
    public final int getLevel() {
        return level;
    }

    private double getAttackChance() {
        double chance = stage.attack();
        chance *= condition.attack();
        for (Effect e : effects) {
            chance *= e.attack();
        }
        return chance;
    }

    /**
     * �������������� ��� ���������� ���� �� ��������� ����
     */
    public final void prepareMove() {
        if (getAttackChance() > Math.random()) {
            if (moves.length == 0) {
                preparedMove = Move.getStruggleMove();
            } else {
                if (confusion > 0 && Math.random() < .33) {
                    preparedMove = Move.getConfusionMove();
                } else {
                    preparedMove = moves[(int) Math.floor((Math.random() * moves.length))];
                }
                confusion -= confusion == 0 ? 0 : 1;
            }
        } else {
            preparedMove = Move.getNoMove();
        }
    }

    /**
     * ���������� true, ���� ������� � ��������
     * @return
     */
    public final boolean isAlive() {
        return getStat(Stat.HP) > stage.stat(Stat.HP);
    }

    /**
     * ������� ��������-���������. ����� �����, ���� ���� �� ��������� ������ ��������, ������� �� ���� ���������
     * � ���������� true
     * @param foe
     * @return
     */
    public boolean attack(Pokemon foe) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        preparedMove.attack(this, foe);
        if (!foe.isAlive()) {
            if (isAlive()) {
                win(foe);
            } else {
                System.out.println(this + " ����� ����� ����� ������ ��������.");
                System.out.println(foe + " ����� ������ ��� ��������.");
            }
            return true;
        } else {
            if (!isAlive()) {
                foe.win(this);
                return true;
            }
        }
        return false;
    }

    /**
     * ������ ���� ������ � ����� ������� ����, � ���� ������ ����������� ��� �������
     */
    public final void turn() {
        setMod(Stat.HP, condition.stat(Stat.HP));
        if (condition.turn()) {
            condition.clear();
        }
        if (condition.condition() == Status.FREEZE && Math.random() < 0.2) {
            condition.clear();
            System.out.println(this + " ���������");
        }
        for (Effect e : effects) {
            setMod(Stat.HP, e.stat(Stat.HP));
            if (e.turn()) {
                e.clear();
            }
        }
    }

    /**
     * ������� ��������� � ������ ��������� ��������
     * @param foe
     */
    public void win(Pokemon foe) {
        System.out.println(foe + " ������ �������� � ������!");
//        System.out.println(this + " �������� " + foe.level * foe.baseXP + " �����.");
//        xp += foe.level * foe.baseXP;
    }

    /**
     * ������������� ���� ��� ��� ���� ��������
     * @param types
     */
    protected final void setType(Type... types) {
        this.types = types;
    }

    /**
     * ������������� ���� ��� ��������� ����
     * @param moves
     */
    protected final void setMove(Move... moves) {
        if (moves == null) {
            setMove(Move.getStruggleMove());
        } else {
            this.moves = moves;
        }
    }

    /**
     * ���������� �������������� �����
     * @return
     */
    protected final Move getPreparedMove() {
        return preparedMove;
    }

    /**
     * ���������� ��� � ��� ��������
     * @return
     */
    @Override
    public final String toString() {
        return (getClass().isAnonymousClass() ? "Pokemon" : getClass().getSimpleName()) + " " + name;
    }

}
