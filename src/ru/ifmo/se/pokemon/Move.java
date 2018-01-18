package ru.ifmo.se.pokemon;

/**
 *  ����������� ����� - ������ ��� ���� ����
 */
public abstract class Move {

    protected Type type;            // ��� �����
    protected double power = 0.0;   // ������� (���� ��� ������� ���� �����)
    protected double accuracy = 1.0;    // �������� ��������� (����������� ������������)
    protected int priority = 0;     // ��������� �����
    protected int hits = 1;         // ���������� ������ �� ���� ���

    /**
     *  ����������� ��� ���������� �� ���������
     */
    public Move() {
        this(Type.NONE, 0.0, 1.0, 0, 1);
    }

    /**
     *  �����������, ����������� ������ ���, ������� � �������� �����
     *  ����� �������������� � ����������� �������, ��� ��� ��������� ������ 0, � ���������� ������ - 1
     *
     * @param type  ��� �����
     * @param pow   �������
     * @param acc   ��������
     */
    public Move(Type type, double pow, double acc) {
        this(type, pow, acc, 0, 1);
    }

    /**
     *  �����������, �������� ��� ��������� �����
     *
     * @param type  ��� �����
     * @param pow   �������
     * @param acc   ��������
     * @param priority  ���������
     * @param hits      ���������� ������ �� ���� ���
     */
    public Move(Type type, double pow, double acc, int priority, int hits) {
        this.type = type;
        accuracy = acc;
        power = pow;
        this.priority = priority;
        this.hits = hits;
    }

    /**
     * ����������� �����, ����������� �������� �����
     *
     * @param att   ��������� �������
     * @param def   ������������� �������
     */
    protected abstract void attack(Pokemon att, Pokemon def);

    /**
     *  ����� ��� �����������, ���������� �� �����, ���� ������� �����������.
     *  ���������� �������������� ��� �������� �����,
     *  ���������� �� ��������� �������� ���������� �������� � ������������ ��������������.
     *
     *  ��� ������������ ���� ������� ��������������, ��������, ����� ����� ������ �������.
     *
     * @param att   ��������� �������
     * @param def   ������������� �������
     * @return      �����������, ��� ������� �� �����������
     */
    protected boolean checkAccuracy(Pokemon att, Pokemon def) {
        return (accuracy * att.getStat(Stat.ACCURACY) / def.getStat(Stat.EVASION)) > Math.random();
    }

    /**
     * ��������� �����
     * @return  ���������� ��������� �����
     */
    public final int getPriority() {
        return priority;
    }

    /**
     *  �������� �������� ����� �� ����� ���.
     *  ������ ���������� ������, ������� ������������� ����� ���������� ���������� ��������.
     *  ��������, �� ��������� ����� ���������� ������ "�������".
     *
     *  �� ����� ��� ��� ����� �������� ����� �������� ��������� "������� � �������"
     *
     * @return  �������� �������� �����
     */
    protected String describe() {
        return "�������";
    }

    /**
     * ����� ��� ������� �������������� ��������, ������� ������������� �� �������������� ��������.
     *
     * �� ��������� ������ �� ������. ������ ���� �������������, ���� ������������� ������� ��������
     * �����-���� �����������, ����� ����������� �����������.
     *
     * @param p     �������
     */
    protected void applyOppEffects(Pokemon p) { }

    /**
     * ����� ��� ������� �������������� ��������, ������� ������������� �� ���������� ��������.
     *
     * �� ��������� ������ �� ������. ������ ���� �������������, ���� ��������� ������� ��������
     * �����-���� �����������, ����� ����������� �����������.
     *
     * @param p     �������
     */
    protected void applySelfEffects(Pokemon p) { }

    /**
     * ����������� �����, ������������ ������ �����, ������� ������ �� ������
     * @return  ����� ���� NoMove
     */
    public static Move getNoMove() {
        return noMove;
    }

    /**
     *  ����������� �����, ������������ ����� ���� Struggle.
     *  Struggle - ��� ����������� �����, ������� ������� ��� ��������. ��� ������������ � ��� ������,
     *  ����� � �������� ��� ������ ����. ��� ����� �� ����� ����, �� ���� ��������� ��������� �� ���� ���������.
     *  ��� ���������� ��������� ������� �������� 1/4 ���������� �����������.
     * @return
     */
    public static Move getStruggleMove() {
        return struggleMove;
    }

    /**
     * ����������� �����, ����������� �����, ������� ��������� �� ���� ������� � ��������� �������������
     *
     * @return
     */
    public static Move getConfusionMove() {
        return confusionMove;
    }

    private final static Move noMove = new Move(Type.NONE, 0.0, 0.0, -100, 0) {
        @Override public final void attack(Pokemon att, Pokemon def) { }
        @Override public String describe() { return "�� ����� ���������"; }
    };
    private final static Move struggleMove = new PhysicalMove(Type.NONE, 50.0, 1.0) {
        @Override public final String describe() {
            return "������� � ����������";
        }
        @Override public final void applySelfDamage(Pokemon att, double damage) {
            att.setMod(Stat.HP, (int) Math.round(damage / 4.0));
        }
    };
    private final static Move confusionMove = new PhysicalMove(Type.NONE, 40.0, 1.0) {
        @Override public final String describe() {
            return "���������� ���������� ����";
        }
        @Override public final void applySelfDamage(Pokemon att, double damage) {
            att.setMod(Stat.HP, (int) damage);
        }
        @Override public double calcCriticalHit(Pokemon att, Pokemon def) { return 1.0; }
        @Override protected void applyOppDamage(Pokemon def, double damage) { }
    };
}
