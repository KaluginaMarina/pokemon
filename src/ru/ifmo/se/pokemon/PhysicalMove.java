package ru.ifmo.se.pokemon;
/**
 *  Класс PhysicalMove - суперкласс для всех атак категории "Физические" (Physical moves).
 *  Является наследником класса DamageMove (атаки, наносящие повреждение).
 *  Особенностью физических атак является то, что для расчета наносимого повреждения
 *  используется отношение обычной атаки и обычной защиты.
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
     * Для расчета A/D физических атак используются обычные атака и защита
     * @param attacker  атакующий покемон
     * @param defender  обороняюийся покемон
     * @return  результат деления обычной атаки на обычную защиту
     */
    @Override protected final double calcAttDefFactor(Pokemon attacker, Pokemon defender) {
        return attacker.getStat(Stat.ATTACK) / defender.getStat(Stat.DEFENSE);
    }
}
