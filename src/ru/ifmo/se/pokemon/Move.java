package ru.ifmo.se.pokemon;

/**
 *  Абстрактный класс - предок для всех атак
 */
public abstract class Move {

    protected Type type;            // тип атаки
    protected double power = 0.0;   // энергия (своя для каждого вида атаки)
    protected double accuracy = 1.0;    // точность попадания (вероятность срабатывания)
    protected int priority = 0;     // приоритет атаки
    protected int hits = 1;         // количество ударов за один ход

    /**
     *  Конструктор без параметров по умолчанию
     */
    public Move() {
        this(Type.NONE, 0.0, 1.0, 0, 1);
    }

    /**
     *  Конструктор, позволяющий задать тип, энергию и точность атаки
     *  Может использоваться в большинстве случаев, так как приоритет обычно 0, а количество ударов - 1
     *
     * @param type  тип атаки
     * @param pow   энергия
     * @param acc   точность
     */
    public Move(Type type, double pow, double acc) {
        this(type, pow, acc, 0, 1);
    }

    /**
     *  Конструктор, задающий все параметры атаки
     *
     * @param type  тип атаки
     * @param pow   энергия
     * @param acc   точность
     * @param priority  приоритет
     * @param hits      количество ударов за один ход
     */
    public Move(Type type, double pow, double acc, int priority, int hits) {
        this.type = type;
        accuracy = acc;
        power = pow;
        this.priority = priority;
        this.hits = hits;
    }

    /**
     * абстрактный метод, реализующий механизм атаки
     *
     * @param att   атакующий покемон
     * @param def   обороняющийся покемон
     */
    protected abstract void attack(Pokemon att, Pokemon def);

    /**
     *  Метод для определения, произойдет ли атака, либо покемон промахнулся.
     *  Стандартно рассчитывается как точность атаки,
     *  умноженная на отношения точности атакующего покемона к уклоняемости обороняющегося.
     *
     *  Для определенных атак требует перопределения, например, когда атака всегда успешна.
     *
     * @param att   атакующий покемон
     * @param def   обороняющийся покемон
     * @return      вероятность, что покемон не промахнулся
     */
    protected boolean checkAccuracy(Pokemon att, Pokemon def) {
        return (accuracy * att.getStat(Stat.ACCURACY) / def.getStat(Stat.EVASION)) > Math.random();
    }

    /**
     * Приоритет атаки
     * @return  возвращает приоритет атаки
     */
    public final int getPriority() {
        return priority;
    }

    /**
     *  Тектовое описание атаки во время боя.
     *  Должен возвращать глагол, который подставляется после упоминания атакующего покемона.
     *  Например, по умолчанию метод возвращает строку "атакует".
     *
     *  Во время боя при атаке покемона будет выведено сообщение "Покемон Х атакует"
     *
     * @return  описание действия атаки
     */
    protected String describe() {
        return "атакует";
    }

    /**
     * Метод для задания дополнительных эффектов, которые накладываются на обороняющегося покемона.
     *
     * По умолчанию ничего не делает. Должен быть переопределен, если обороняющийся покемон получает
     * какое-либо воздействие, кроме физического повреждения.
     *
     * @param p     покемон
     */
    protected void applyOppEffects(Pokemon p) { }

    /**
     * Метод для задания дополнительных эффектов, которые накладываются на атакующего покемона.
     *
     * По умолчанию ничего не делает. Должен быть переопределен, если атакующий покемон получает
     * какое-либо воздействие, кроме физического повреждения.
     *
     * @param p     покемон
     */
    protected void applySelfEffects(Pokemon p) { }

    /**
     * Статический метод, возвращающий пустую атаку, которая ничего не делает
     * @return  атаку типа NoMove
     */
    public static Move getNoMove() {
        return noMove;
    }

    /**
     *  Статический метод, возврящающий атаку типа Struggle.
     *  Struggle - это стандартная атака, которой владеют все покемоны. Она используется в том случае,
     *  когда у покемона нет других атак. Эта атака не имеет типа, то есть действует одинаково на всех покемонов.
     *  При применении атакующий покемон получает 1/4 наносимого повреждения.
     * @return
     */
    public static Move getStruggleMove() {
        return struggleMove;
    }

    /**
     * Статический метод, возращающий атаку, которую напраляет на себя покемон в состоянии растерянности
     *
     * @return
     */
    public static Move getConfusionMove() {
        return confusionMove;
    }

    private final static Move noMove = new Move(Type.NONE, 0.0, 0.0, -100, 0) {
        @Override public final void attack(Pokemon att, Pokemon def) { }
        @Override public String describe() { return "не может атаковать"; }
    };
    private final static Move struggleMove = new PhysicalMove(Type.NONE, 50.0, 1.0) {
        @Override public final String describe() {
            return "борется с соперником";
        }
        @Override public final void applySelfDamage(Pokemon att, double damage) {
            att.setMod(Stat.HP, (int) Math.round(damage / 4.0));
        }
    };
    private final static Move confusionMove = new PhysicalMove(Type.NONE, 40.0, 1.0) {
        @Override public final String describe() {
            return "растерянно повреждает себя";
        }
        @Override public final void applySelfDamage(Pokemon att, double damage) {
            att.setMod(Stat.HP, (int) damage);
        }
        @Override public double calcCriticalHit(Pokemon att, Pokemon def) { return 1.0; }
        @Override protected void applyOppDamage(Pokemon def, double damage) { }
    };
}
