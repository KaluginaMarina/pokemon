package ru.ifmo.se.pokemon;

/**
 *  Перечисление содержит типы покемонов и атак. Атака имеет один тип, покемон - один или два.
 *  Можно использовать метод getEffect для расчета эффективности атаки против покемона
 */
public enum Type {

    NORMAL, FIRE, WATER, ELECTRIC, GRASS, ICE, FIGHTING, POISON, GROUND,
    FLYING, PSYCHIC, BUG, ROCK, GHOST, DRAGON, DARK, STEEL, FAIRY, NONE;

    /*
     *  effects array holds type-against-type effect modifiers
     *  value 0 means normal effect
     *  value 1 means half effect
     *  value 2 means no effect
     *  value -2 means double effect
     */
    private static final int[][] effects;

    /*
     *  method effect(x) returns real multiplier based on values stored in effects array
     *  0 converts to 1.0
     *  1 converts to 0.5
     *  2 converts to 0.0
     *  -2 converts to 2.0
     *
     */
    private double effect(Type x) {
        return 1.0 - effects[this.ordinal()][x.ordinal()] / 2.0;
    }

    /*
     *  helper methods to fill effects array
     */
    private Type zero(Type... types) {
        for (Type x : types) {
            effects[this.ordinal()][x.ordinal()] = 2;
        }
        return this;
    }
    private Type half(Type... types) {
        for (Type x : types) {
            effects[this.ordinal()][x.ordinal()] = 1;
        }
        return this;
    }
    private Type doub(Type... types) {
        for (Type x : types) {
            effects[this.ordinal()][x.ordinal()] = -2;
        }
        return this;
    }

    /*
     *  array initialization, default is 0, that means normal effect
     */
    static {
        effects = new int[values().length][values().length];
        NORMAL  .half(ROCK, STEEL)
                .zero(GHOST);
        FIRE    .half(FIRE, WATER, ROCK, DRAGON)
                .doub(GRASS, ICE, BUG, STEEL);
        WATER   .doub(FIRE, GROUND, ROCK)
                .half(WATER, GRASS, DRAGON);
        ELECTRIC.doub(WATER, FLYING)
                .half(ELECTRIC, GRASS, DRAGON)
                .zero(GROUND);
        GRASS   .half(FIRE, GRASS, POISON, FLYING, BUG, DRAGON, STEEL)
                .doub(WATER, GROUND, ROCK);
        ICE     .half(FIRE, WATER, ICE, STEEL)
                .doub(GRASS, GROUND, FLYING, DRAGON);
        FIGHTING.doub(NORMAL, ICE, ROCK, DARK, STEEL)
                .half(POISON, FLYING, PSYCHIC, BUG, FAIRY)
                .zero(GHOST);
        POISON  .doub(GRASS, FAIRY)
                .half(POISON, GROUND, ROCK, GHOST)
                .zero(STEEL);
        GROUND  .doub(FIRE, ELECTRIC, POISON, ROCK, STEEL)
                .half(GRASS, BUG)
                .zero(FLYING);
        FLYING  .half(ELECTRIC, ROCK, STEEL)
                .doub(GRASS, FIGHTING, BUG);
        PSYCHIC .doub(FIGHTING, POISON)
                .half(PSYCHIC, STEEL)
                .zero(DARK);
        BUG     .half(FIRE, FIGHTING, POISON, FLYING, GHOST, STEEL, FAIRY)
                .doub(GRASS, PSYCHIC, DARK);
        ROCK    .doub(FIRE, ICE, FLYING, BUG)
                .half(FIGHTING, GROUND, STEEL);
        GHOST   .zero(NORMAL)
                .doub(PSYCHIC, GHOST)
                .half(DARK);
        DRAGON  .doub(DRAGON)
                .half(STEEL)
                .zero(FAIRY);
        DARK    .half(FIGHTING, DARK, FAIRY)
                .doub(PSYCHIC, GHOST);
        STEEL   .half(FIRE, WATER, ELECTRIC, STEEL, FAIRY)
                .doub(ICE, ROCK);
        FAIRY   .half(FIRE, POISON, STEEL)
                .doub(FIGHTING, DRAGON, DARK);
    }

    /**
     * Рассчитывает эффект атаки против покемона одного или двух заданных типов
     * @param types один или несколько типов покемона
     * @return суммарный эффект атаки
     */
    public double getEffect(Type... types) {
        double eff = 1.0;
        for (Type x : types) {
            eff *= effect(x);
        }
        return eff;
    }
}
