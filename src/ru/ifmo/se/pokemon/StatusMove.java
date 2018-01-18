package ru.ifmo.se.pokemon;

/**
 *  Класс, представляющий атаки, изменяющие статус. Они не наносят повреждения, но могут менять состояние покемонов,
 *  их характеристики
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
     *  Метод, реализующий атаку. Сначала идет проверка на то, что атака удалась.
     *  Затем рассчитывается эффект от типа атаки и типов покемонов.
     *  При этом, так как повреждения нет, то проверяется только иммунитет покемона к атакам определенного типа.
     *  В конце применяются эффекты от атаки на атакующего и обороняющегося покемонов.
     *
     * @param att   атакующий покемон
     * @param def   обороняющийся покемон
     */
    public final void attack(Pokemon att, Pokemon def) {
        for (int i = 0; i < hits; i++) {
            if (checkAccuracy(att, def)) {
                System.out.println(att + " " + describe() + ". ");
                if (type.getEffect(def.getTypes()) > 0.0) {
                    applyOppEffects(def);
                } else {
                    System.out.println(def + " не замечает воздействие типа " + type);
                }
                if (type.getEffect(att.getTypes()) > 0.0) {
                    applySelfEffects(att);
                }
            } else {
                System.out.println(att + " промахивается.");
            }
        }
    }

    /**
     *  Метод для применения эффектов на атакующего покемона (обычно положительных).
     *  При необходимости должен быть переопределен, так как по умолчанию ничего не делает.
     *
     * @param p     покемон
     */
    protected void applySelfEffects(Pokemon p) { }
    /**
     *  Метод для применения эффектов на обороняющегося покемона (обычно отрицательных).
     *  При необходимости должен быть переопределен, так как по умолчанию ничего не делает.
     *
     * @param p     покемон
     */
    protected void applyOppEffects(Pokemon p) { }
}
