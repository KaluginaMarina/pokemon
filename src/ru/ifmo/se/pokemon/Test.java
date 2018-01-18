package ru.ifmo.se.pokemon;

public class Test {
public static void main(String[] args) {
    Battle b = new Battle();
    b.addAlly(new Nidoking("Пётр"));
    b.addFoe(new Slowpoke("Кузьмич"));
    b.go();
}
}

class Nidoking extends Pokemon {
    public Nidoking(String name) {
        super(name);
    }
    {
        setStats(81,102,77,85,75,85);
        setType(Type.POISON, Type.GROUND);
        setMove(new Megahorn());
    }
}
class Megahorn extends PhysicalMove {
    public Megahorn () { super (Type.BUG, 120, 0.85); }
}
class Slowpoke extends Pokemon {
    public Slowpoke(String name) {
        super(name);
    }
    {
        level = 90;
        setStats(90,65,65,40,40,15);
        setType(Type.WATER, Type.PSYCHIC);
        setMove(new WaterGun());
    }
}
class WaterGun extends SpecialMove {
    public WaterGun () { super (Type.WATER, 40, 1.0); }

}
