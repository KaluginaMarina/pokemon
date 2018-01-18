package ru.ifmo.se.pokemon;

/**
 *  �����, �������������� �����, ���������� ������. ��� �� ������� �����������, �� ����� ������ ��������� ���������,
 *  �� ��������������
 *
 */
public class StatusMove extends Move {
    public StatusMove() {
        super();
    }
    public StatusMove(Type type, double pow, double acc) {
        super(type, pow, acc);
    }

    public StatusMove(Type type, double pow, double acc, int priority, int hits) {
        super (type, pow, acc, priority, hits);
    }

    /**
     *  �����, ����������� �����. ������� ���� �������� �� ��, ��� ����� �������.
     *  ����� �������������� ������ �� ���� ����� � ����� ���������.
     *  ��� ����, ��� ��� ����������� ���, �� ����������� ������ ��������� �������� � ������ ������������� ����.
     *  � ����� ����������� ������� �� ����� �� ���������� � �������������� ���������.
     *
     * @param att   ��������� �������
     * @param def   ������������� �������
     */
    public final void attack(Pokemon att, Pokemon def) {
        for (int i = 0; i < hits; i++) {
            if (checkAccuracy(att, def)) {
                System.out.println(att + " " + describe() + ". ");
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
     *  ����� ��� ���������� �������� �� ���������� �������� (������ �������������).
     *  ��� ������������� ������ ���� �������������, ��� ��� �� ��������� ������ �� ������.
     *
     * @param p     �������
     */
    protected void applySelfEffects(Pokemon p) { }
    /**
     *  ����� ��� ���������� �������� �� �������������� �������� (������ �������������).
     *  ��� ������������� ������ ���� �������������, ��� ��� �� ��������� ������ �� ������.
     *
     * @param p     �������
     */
    protected void applyOppEffects(Pokemon p) { }
}
