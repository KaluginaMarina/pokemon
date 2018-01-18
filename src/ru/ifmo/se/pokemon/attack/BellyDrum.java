package ru.ifmo.se.pokemon.attack;

import ru.ifmo.se.pokemon.*;

public class BellyDrum extends StatusMove {
    public BellyDrum (){
        super(Type.NORMAL , 0.0 , 1.0);
    }
    @Override
    protected void applySelfEffects(Pokemon p){
        Effect e = new Effect().stat(Stat.ATTACK, 40);
        Effect e1 = new Effect().stat(Stat.ATTACK, 30);
    }
}
