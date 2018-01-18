package ru.ifmo.se.pokemon;

/**
 *  ����������� ����� ��� ���� ����, ������� ������� �����������.
 *  �������� �������� ������ Move.
 *  �������� ������� ������� PhysicalMove � SpecialMove
 */
public abstract class DamageMove extends Move {
    public DamageMove() {
        super();
    }
    public DamageMove(Type type, double pow, double acc) {
        super(type, pow, acc);
    }

    public DamageMove(Type type, double pow, double acc, int priority, int hits) {
        super (type, pow, acc, priority, hits);
    }

    /**
     *  ����� ��� ������� �������� �����������.
     *  ����������� ������� ����������� ������� �� ������ ���������� �������� (level)
     *  � ������� ����� (power).

     *  � ����������� ������� ����� �� ������� ���������������.
     *
     * @param att   ��������� �������
     * @param def   ������������� �������
     * @return  ������� �����������
     */
    protected double calcBaseDamage(Pokemon att, Pokemon def) {
        return (0.4 * att.getLevel() + 2.0) * power / 100.0;
    }

    /**
     *  ����������� ����� ��� ������� �������, ������������ ����������� ����� � ������.
     *  ���������������� �� ������� ��� ���������� � ����������� ����
     *
     * @param att   ��������� �������
     * @param def   ������������� �������
     * @return      ��������� ����� � ������ (������� �� ���� �����)
     */
    protected abstract double calcAttDefFactor(Pokemon att, Pokemon def);

    /**
     *  ����� ��� ������� ������� ���� ����� � ����� ��������.
     *  �� ������� ���������������.
     *  � ������, ���� ����������� �� ����� 1, ��������� ��������������� ���������.
     *
     * @param att   ��������� �������
     * @param def   ������������� �������
     * @return      0, ���� ������� �������� � ������� ���� ����� (� ���� ������ ����� ������� ����� 1 HP)
     *              0.25, ���� ��� ���� �������� ���������� � ������� ���� �����
     *              0.5, ���� ���� �� ����� �������� ���������� � ������� ���� �����
     *              1, ���� ��� ���� �������� ����������, ���� ���� ��� ����������, ������ - ������
     *              2, ���� ���� �� ����� �������� ������ � ������� ���� �����
     *              4, ���� ��� ���� �������� ������� � ������� ���� �����
     */
    protected double calcTypeEffect(Pokemon att, Pokemon def) {
        String message = "";
        double typeEffect = type.getEffect(def.getTypes());
        if (typeEffect <= 0) {
            message = " �� ��������� �����";
        } else {
            message = " ��������� ";
            if (typeEffect < 0.4) message += "����� ";
            if (typeEffect < 0.75) message += "������ �����������";
            if (typeEffect > 2.5) message += "����� ";
            if (typeEffect > 1.5) message += "������� �����������";
        }
        if (typeEffect != 1.0) {
            System.out.println("����� ���� " + type + message + ".");
        }
        return typeEffect;
    }

    /**
     *  �����, ����������� ���������� � ���� ������������ �����.
     *  ����������� ����������� - ����������� �������� �������� / 512.
     *  ����������� ���� - � 2 ���� ������ �������.
     *
     *  ����� ���� �������������, ���� ��� ���������� ����� ����������� ���� �������� ��-�������.
     *
     * @param att   ��������� �������
     * @param def   ������������� �������
     * @return      ���������, �� ������� ���������� �����������
     *              2 � ������������ ���. �������� / 512;
     *              1 � ��������� �������
     */
    protected double calcCriticalHit(Pokemon att, Pokemon def) {
        if (att.getStat(Stat.SPEED) / 512.0 > Math.random()) {
            System.out.println("����������� ����!");
            return 2.0;
        } else {
            return 1.0;
        }
    }

    /**
     *  ����� ��� ������� ������, ���� ��� ���������� �������� ��������� � ����� �����
     *  ���������� ��������� ����� � 1.5 ����.
     *
     *  ������ �� ������� ���������������.
     *
     * @param att   ��������� �������
     * @param def   ������������� �������
     * @return      1.5, ���� ���� ����� � ���������� �������� ���������
     *              1, ���� �� ���������
     */
    protected double calcSameTypeAttackBonus(Pokemon att, Pokemon def) {
        double effect = 1.0;
        if (type != Type.NONE) {
            for (Type t : att.getTypes()) {
                if (t == type) {
                    effect *= 1.5;
                }
            }
        }
        return effect;
    }

    /**
     *  ����� ��� ������� ��������� ����� �����������
     *  ���������� ���������� ����� �� 0,85 �� 1,0
     *
     * @param att   ��������� �������
     * @param def   ������������� �������
     * @return      ��������� ����� � ��������� [ 0,85 ; 1.0 )
     */
    protected double calcRandomDamage(Pokemon att, Pokemon def) {
        return Math.random() + 0.15 + 0.85;
    }

    /**
     *  �����, ����������� �����.
     *  ������� ���������, �� ����������� �� ��������� �������.
     *  ����� ������� �����������, ��� ������������ ��������� �������
     *  ����� ���������� ���������� ����������� �������� ������:
     *  applyOppDamage(def, damage)
     *  applySelfDamage(att, damage)
     *  applyOppEffects(def)
     *  applySelfEffects(att)
     *
     *  ��� ���������� ����������� � ������ �������� � �������������� � ���������� ���������.
     *
     * @param att   ��������� �������
     * @param def   ������������� �������
     */
    public final void attack(Pokemon att, Pokemon def) {
        for (int i = 0; i < hits; i++) {
            if (checkAccuracy(att, def)) {
                System.out.println(att + " " + describe() + ". ");
                double damage = calcBaseDamage(att, def) * calcAttDefFactor(att, def) + 2.0;
                damage *= calcCriticalHit(att, def);
                damage *= calcSameTypeAttackBonus(att, def);
                damage *= calcRandomDamage(att, def);
                damage *= calcTypeEffect(att, def);
                if (damage == 0.0) { damage = 1.0; }
                damage = Math.round(damage);
                applyOppDamage(def, damage);
                applySelfDamage(att, damage);
                if (type.getEffect(def.getTypes()) > 0.0) {
                    applyOppEffects(def);
                } else {
                    System.out.println(def + " �� �������� ����������� ���� " + type);
                }
                if (type.getEffect(att.getTypes()) > 0.0) {
                    applySelfEffects(att);
                }

            } else {
                System.out.println(att + " �������������.");
            }
        }
    }

    /**
     *  �����, ����������� ����������� ����������� � �������������� ��������.
     *  ��������� ��� ��������� ����������� � ������ �������.
     *
     *  ��� ������������ ���� ������ ���� �������������.
     *
     * @param def       ������������� �������
     * @param damage    ��������� �����������
     */
    protected void applyOppDamage(Pokemon def, double damage) {
        def.setMod(Stat.HP, (int) Math.round(damage));
    }
    /**
     *  �����, ����������� ����������� ����������� � ���������� ��������.
     *  ��������� - ������ �� ������.
     *
     *  ��� ����, ��� ��������� ������� ���� �������� ���� - ������ ���� �������������.
     *
     * @param att       ��������� �������
     * @param damage    ��������� ����������� �������������� ��������
     */

    protected void applySelfDamage(Pokemon att, double damage) {
    }
}
