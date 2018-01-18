package ru.ifmo.se.pokemon;

/**
 *  Класс, представляющий собой эффект, воздействующий на покемона.
 *  Содержит массив целых чисел, соответствующих основным характеристикам. В нм хранятся модификаторы характеристик
 *  покемона. Для всех характеристик, кроме очков здоровья (HP), модификатор может быть от -6 до +6.
 *  Он показывает, на сколько ступеней (stages) изменяется характеристика.
 *  Ноль соответствует обычному значению (не измененному), положительное число - увеличенному значению характеристики,
 *  отрицательное - пониженному.
 *
 *  Если на покемона действует одновременно несколько эффектов, то модификаторы складываются, но суммарное значение
 *  не должно выходить за диапазон от -6 до 6.
 *
 *  Кроме модификаторов, эффект также содержит:
 *  - продолжительность эффекта в ходах (0 - эффект действует немедленно и сразу пропадает, -1 - эффект действует постоянно)
 *  - шанс эффекта - вероятность того, что при очередном ходе эффект сработает. По умолчанию - 100%
 *  - шанс, что покемон будет атаковать во время своего хода (от 0 (не атакует) до 1 (обязательно атакует))
 *  - состояние покемона (из перечисления Status)
 */
public final class Effect {
    private int[] mods = new int[Stat.values().length];
    private int turns = 0;
    private double effectChance = 1.0;
    private double attackChance = 1.0;
    private Status condition = Status.NORMAL;

    /**
     * Конструктор
     *
     */
    public Effect() {
    }

    /**
     * устанавливает продолжительность эффекта в ходах
     * @param turns  количество ходов, в течение которых эффект будет действововать
     *               0 - эффект должен сработать сразу и уничтожиться
     *               < 0 - эффект будет действовать постоянно, пока не будет снят или заменен другим
     * @return  эффект (чтобы можно было пользоваться цепочками методов установки параметров
     */
    public final Effect turns(int turns) {
        this.turns = turns;
        return this;
    }

    /**
     * устанавливает вероятность срабатывания эффекта
     * @param chance  вероятность, есди 1 - то эффект будет всегда срабатывать.
     * @return  эффект (чтобы можно было пользоваться цепочками методов установки параметров
     */
    public final Effect chance(double chance) {
        effectChance = chance;
        return this;
    }

    /**
     * устанавливает вероятность срабатывания атаки покемона во время действия эффекта.
     * По умолчанию - 1.
     * Нужен в тех случаях, когда наложенный эффект полностью или с некоторой вероятностью не дает покемону атаковать.
     *
     * @param chance  вероятность, 0 - покемон не будет атаковать, пока действует эффект
     * @return  эффект (чтобы можно было пользоваться цепочками методов установки параметров
     */
    public final Effect attack(double chance) {
        this.attackChance = chance;
        return this;
    }

    /**
     * Возвращает вероятность атаки покемона во время действия эффекта
     * @return
     */
    public final double attack() {
        return attackChance;
    }

    /**
     * устанавливает состояние покемона
     *
     * @param condition состояние (перечисление Status)
     * @return  эффект (чтобы можно было пользоваться цепочками методов установки параметров
     */
    public final Effect condition(Status condition) {
        this.condition = condition;
        return this;
    }

    /**
     * очищает модификаторы эффекта. После этого эффект перестает действовать на покемона
     */
    public final void clear() {
        for (Stat s : Stat.values()) {
            mods[s.ordinal()] = 0;
        }
        condition = Status.NORMAL;
        turns = 0;
        effectChance = 1.0;
        attackChance = 1.0;
    }

    /**
     * возвращает состояние покемона
     * @return  состояние
     */
    public final Status condition() {
        return condition;
    }

    /**
     * возвращает модификатор заданной характеристики
     * @param stat  характеристика (перечисление Stat)
     * @return  значение характеристики
     */
    public final int stat(Stat stat) {
        return mods[stat.ordinal()];
    }

    /**
     * устанавливает значение характеристики
     * @param stat  характеристика (перечисление Stat)
     * @param value  значение характеристики
     * @return  эффект (чтобы можно было пользоваться цепочками методов установки параметров
     */
    public final Effect stat(Stat stat, int value) {
        if (stat != Stat.HP) {
            if (value >= 0 & value > 6) value = 6;
            if (value < 0 & value < -6) value = -6;
        }
        mods[stat.ordinal()] = value;
        return this;
    }

    /**
     * проверка на срабатывание эффекта
     * @return  true с вероятностью срабатывания эффекта
     */
    public final boolean success() {
        return effectChance > Math.random();
    }

    /**
     *  проверка на однократность
     * @return  true, если эффект должен быть применен сразу и однократно
     */
    public final boolean immediate() {
        return turns == 0;
    }

    /**
     *  вызывается в конце очередного хода
     * @return  true, если эффект закончил действие после этого хода
     */
    public final boolean turn() {
        return --turns == 0;
    }

    /**
     * статический метод наложения на покемона эффекта воспламенения. Не действует на огненных покемонов.
     * Атака уменьшается на 2 ступени, каждый ход покемон будет терять 1/16 HP
     * @param p     покемон, на которого будет наложен эффект
     */
    public static void burn(Pokemon p) {
        if (!p.hasType(Type.FIRE)) {
            Effect e = new Effect().condition(Status.BURN).turns(-1);
            e.stat(Stat.ATTACK, -2).stat(Stat.HP, (int) p.getStat(Stat.HP) / 16);
            p.setCondition(e);
        }
    }
    /**
     * статический метод наложения на покемона эффекта парализации. Не действует на электрических покемонов.
     * Скорость уменьшается на 2 ступени, с вероятностью 25% покемон не будет атаковать
     * @param p     покемон, на которого будет наложен эффект
     */
    public static void paralyze(Pokemon p) {
        if (!p.hasType(Type.ELECTRIC)) {
            Effect e = new Effect().condition(Status.PARALYZE).attack(0.75).turns(-1);
            e.stat(Stat.SPEED, -2);
            p.setCondition(e);
        }
    }
    /**
     * статический метод наложения на покемона эффекта заморозки. Не действует на ледяных покемонов.
     * Покемон перестает атаковать
     * @param p     покемон, на которого будет наложен эффект
     */
    public static void freeze(Pokemon p) {
        if (!p.hasType(Type.ICE)) {
            Effect e = new Effect().condition(Status.FREEZE).attack(0).turns(-1);
            p.setCondition(e);
        }
    }
    /**
     * статический метод наложения на покемона эффекта отравления. Не действует на ядовитых и стальных покемонов
     * Каждый ход покемон получается урон в 1/8 его HP
     * @param p     покемон, на которого будет наложен эффект
     */
    public static void poison(Pokemon p) {
        if (!p.hasType(Type.POISON) && !p.hasType(Type.STEEL)) {
            Effect e = new Effect().condition(Status.POISON).turns(-1);
            e.stat(Stat.HP, (int) p.getStat(Stat.HP) / 8);
            p.setCondition(e);
        }
    }
    /**
     * статический метод наложения на покемона эффекта сна. Действует от 1 до 3 ходов. Спящий покемон не атакует.
     * @param p     покемон, на которого будет наложен эффект
     */
    public static void sleep(Pokemon p) {
        Effect e = new Effect().condition(Status.SLEEP).attack(0).turns((int)(Math.random() * 3 + 1));
        p.setCondition(e);
    }

    /**
     * Накладывает эффект flinch. Один ход покемон не атакует.
     * @param p     покемон, на которого будет наложен эффект
     */
    public static void flinch(Pokemon p) {
        Effect e = new Effect().attack(0).turns((int)(Math.random() * 4 + 1));
        p.addEffect(e);
    }

    /**
     * Вызывает у покемона растерянность.
     * @param p
     */
    public static void confuse(Pokemon p) {
        p.confuse();
    }
}
