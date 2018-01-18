package ru.ifmo.se.pokemon;

/**
 *  ����� SpecialMove - ���������� ��� ���� ���� ��������� "�����������" (Special moves).
 *  �������� ����������� ������ DamageMove (�����, ��������� �����������).
 *  ������������ ����������� ���� �������� ��, ��� ��� ������� ���������� �����������
 *  ������������ ��������� ����������� ����� � ����������� ������.
 */
public class SpecialMove extends DamageMove {
    public SpecialMove() {
        super();
    }
    public SpecialMove(Type type, double pow, double acc) {
        super(type, pow, acc);
    }

    public SpecialMove(Type type, double pow, double acc, int priority, int hits) {
        super (type, pow, acc, priority, hits);
    }

    /**
     * ��� ������� A/D ����������� ���� ������������ ����������� ����� � ������
     * @param attacker  ��������� �������
     * @param defender  ������������ �������
     * @return  ��������� ������� ����������� ����� �� ����������� ������
     */
    @Override public final double calcAttDefFactor(Pokemon attacker, Pokemon defender) {
        return attacker.getStat(Stat.SPECIAL_ATTACK) / defender.getStat(Stat.SPECIAL_DEFENSE);
    }
}
