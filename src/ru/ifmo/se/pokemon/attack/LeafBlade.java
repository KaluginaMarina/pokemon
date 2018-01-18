package ru.ifmo.se.pokemon.attack;

import ru.ifmo.se.pokemon.*;

public class LeafBlade extends PhysicalMove {
    public LeafBlade(){
        super(Type.GRASS, 90, 1.0);
    }
    @Override
    protected void applySelfEffects(Pokemon p) {
        Effect e = new Effect().stat(Stat.ATTACK, 125);
    }
}
