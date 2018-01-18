package ru.ifmo.se.pokemon;

/**
 *  Класс SpecialMove - суперкласс для всех атак категории "Специальные" (Special moves).
 *  Является наследником класса DamageMove (атаки, наносящие повреждение).
 *  Особенностью специальных атак является то, что для расчета наносимого повреждения
 *  используется отношение специальной атаки и специальной защиты.
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
     * Для расчета A/D специальных атак используются специальные атака и защита
     * @param attacker  атакующий покемон
     * @param defender  обороняюийся покемон
     * @return  результат деления специальной атаки на специальную защиту
     */
    @Override public final double calcAttDefFactor(Pokemon attacker, Pokemon defender) {
        return attacker.getStat(Stat.SPECIAL_ATTACK) / defender.getStat(Stat.SPECIAL_DEFENSE);
    }
}
