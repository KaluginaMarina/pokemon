package ru.ifmo.se.pokemon;

import ru.ifmo.se.pokemon.pocemon.Sigilyph;

public class StartBattle {


    public static void main(String[] args) {
        Battle battle = new Battle();

        battle.addAlly(new Sigilyph("Pavel"));
        battle.addAlly(new Sigilyph("Vladimir"));
        battle.addAlly(new Sigilyph("Sonya"));

        battle.addFoe(new Sigilyph("Nikolay"));
        battle.addFoe(new Sigilyph("Sveta"));
        battle.addFoe(new Sigilyph("Oleg"));

        battle.go();
    }




}
