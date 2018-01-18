package ru.ifmo.se.pokemon;
/**
 *  ����� PhysicalMove - ���������� ��� ���� ���� ��������� "����������" (Physical moves).
 *  �������� ����������� ������ DamageMove (�����, ��������� �����������).
 *  ������������ ���������� ���� �������� ��, ��� ��� ������� ���������� �����������
 *  ������������ ��������� ������� ����� � ������� ������.
 */
public class PhysicalMove extends DamageMove {
    public PhysicalMove() {
        super();
    }
    public PhysicalMove(Type type, double pow, double acc) {
        super(type, pow, acc);
    }

    public PhysicalMove(Type type, double pow, double acc, int priority, int hits) {
        super (type, pow, acc, priority, hits);
    }
    /**
     * ��� ������� A/D ���������� ���� ������������ ������� ����� � ������
     * @param attacker  ��������� �������
     * @param defender  ������������ �������
     * @return  ��������� ������� ������� ����� �� ������� ������
     */
    @Override protected final double calcAttDefFactor(Pokemon attacker, Pokemon defender) {
        return attacker.getStat(Stat.ATTACK) / defender.getStat(Stat.DEFENSE);
    }
}
