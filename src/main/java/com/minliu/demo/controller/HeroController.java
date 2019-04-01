package com.minliu.demo.controller;

import com.minliu.demo.pojo.Hero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ClassName: HeroController <br>
 * date: 3:01 PM 22/03/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@RestController
@RequestMapping("/heroes")
public class HeroController {

    private static final Logger logger = LoggerFactory.getLogger(HeroController.class);

    private static List<Hero> heroes = new CopyOnWriteArrayList<>();

    static {
        heroes.add(new Hero(1, "Andy"));
        heroes.add(new Hero(2, "Chris"));
        heroes.add(new Hero(3, "John"));
        heroes.add(new Hero(4, "James"));
        heroes.add(new Hero(5, "Kris"));
        heroes.add(new Hero(6, "Bowen"));
        heroes.add(new Hero(7, "Kobe"));
        heroes.add(new Hero(8, "Marry"));
        heroes.add(new Hero(9, "Milly"));
        heroes.add(new Hero(10, "Mark"));
        heroes.add(new Hero(11, "Max"));
        heroes.add(new Hero(12, "Jason"));
    }

    @GetMapping
    public List<Hero> getHeroes() {
        logger.info("get heroes...");
        return heroes;
    }

    @GetMapping("/{id}")
    public Hero getHero(@PathVariable("id") Integer id){
        Optional<Hero> optionalHero = heroes.stream().filter(hero -> id.equals(hero.getId()))
                .findAny();
        return optionalHero.orElse(null);
    }

    @PutMapping
    @ResponseBody
    public String updateHero(@RequestBody Hero hero) {
        logger.info("update hero ...");
        Optional<Hero> optionalHero = heroes.stream()
                .filter(h -> hero.getId().equals(h.getId()))
                .findFirst();
        optionalHero.ifPresent(h -> {
            heroes.remove(h);
            heroes.add(hero);
        });
        return "Update Success!";
    }

    @DeleteMapping
    @ResponseBody
    public String deleteHero(Integer id) {
        Optional<Hero> optionalHero = heroes.stream().filter(hero -> id.equals(hero.getId()))
                .findAny();
        if (optionalHero.isPresent()){
            heroes.remove(optionalHero);
            return "Remove Success";
        }
        return "No Hero who's id equal " + id;
    }

    @PostMapping
    @ResponseBody
    public String addHero(@RequestBody Hero hero) {
        Optional<Integer> max = heroes.stream().map(Hero::getId)
                .max(Integer::compareTo);
        max.ifPresent(num -> {
            hero.setId(num+1);
            heroes.add(hero);
        });
        return "hero " + hero.getName() + " saved successfully";
    }
}
