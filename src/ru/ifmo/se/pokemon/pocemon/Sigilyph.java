package ru.ifmo.se.pokemon.pocemon;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.attack.BellyDrum;
import ru.ifmo.se.pokemon.attack.Bounce;
import ru.ifmo.se.pokemon.attack.LeafBlade;
import ru.ifmo.se.pokemon.attack.Thunder;

public class Sigilyph extends Pokemon{
    public Sigilyph(String name){
        super(name);
        level=73;
        final String s;
        {
           s = "2";
        }
        setStats(72,58,80, 103, 80,93 );
        setType(Type.PSYCHIC);
        setMove(new Thunder(), new BellyDrum(), new Bounce(), new LeafBlade());
    }
}
