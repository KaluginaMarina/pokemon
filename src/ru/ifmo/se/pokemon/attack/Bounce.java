package ru.ifmo.se.pokemon.attack;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Bounce extends PhysicalMove {
    public Bounce(){
        super(Type.FLYING, 85, 0.85);
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() < 0.3) {
            Effect.paralyze(p);
        }
    }
}
