package me.davidrush.spaceshooter.level;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.entities.*;
import me.davidrush.spaceshooter.graphics.Assets;

import java.awt.*;
import java.util.ArrayList;

public class Level {
    private Game game;
    private Player player;
    private float cameraY, distance;
    private int cameraOffset = 620, maxEnemies = 16;
    private double difficulty = 0;
    private ArrayList<Entity> entities, toRemove;
    private ArrayList<Actor> actors;
    private Entity[] stars = new Entity[100];

    public Level(Game game) {
        this.game = game;
        entities = new ArrayList<Entity>();
        actors = new ArrayList<Actor>();
        toRemove = new ArrayList<Entity>();
        player = new Player(game.width / 2, game.height, 1f, this, game); //Note, the player cannot be added to the Entity or Actor list or else they will get ticked more than once!

        for(int i = 0; i < stars.length; i++) {
            stars[i] = new Star((float)(Math.random() * game.width), (float)(cameraY + Assets.player.getHeight() +(Math.random() * game.height)), this, game);
        }
    }

    public void tick() {
        double rand = Math.random();
        if(rand <= Math.sqrt(difficulty) / 10000 && actors.size() < maxEnemies) {//enemies will spawn more frequently over time.
            double enemyType = (Math.sqrt(difficulty) / 10000) - rand;
            if(enemyType > 0.04) {
                addActor(new EnemyCruiser((float)Math.random() * game.width, (cameraY) - Assets.enemyCruiser.getHeight(), 1f, this, game));
            } else if(enemyType > 0.03) {
                addActor(new EnemyFighter((float)Math.random() * game.width, (cameraY) - Assets.enemyFighter.getHeight(), 1f,  this, game));
            } else if(enemyType > 0.01) {
                addActor(new EnemyBomber((float)Math.random() * game.width, (cameraY) - Assets.enemyBomber.getHeight(), 1f,  this, game));
            } else if(enemyType > 0.001) {
                addActor(new EnemyScout((float)Math.random() * game.width, (cameraY) - Assets.enemyScout.getHeight(), 1f,  this, game));
            }
        }

        float startCameraY = cameraY;
        player.tick();
        distance = startCameraY - cameraY;
        for(Entity star : stars) {
            star.tick();
        }
        for(Entity entity : entities) {
            entity.tick();
        }
        for(Actor actor : actors) {
            actor.tick();
        }
        for(Entity entity : toRemove) {
            if(entities.contains(entity)) {
                entities.remove(entity);
            }
            if(actors.contains(entity)) {
                actors.remove(entity);
            }
        }
        toRemove.clear();
        difficulty++;
    }

    public void render(Graphics g) {
        for(Entity star : stars) {
            star.render(g);
        }
        for(Entity entity : entities) {
            entity.render(g);
        }
        for(Actor actor : actors) {
            actor.render(g);
        }
        player.render(g);
        g.drawString("Score: " + game.score, 5, 20);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Player getPlayer() {
        return player;
    }

    public float getCameraY(){
        return cameraY;
    }

    public void setCameraY(float cameraY) {
        this.cameraY = cameraY;
    }

    public int getCameraOffset() {
        return cameraOffset;
    }

    public float getDistance() {
        return distance;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public void addEntity(Entity entity) {
        if(entity instanceof Player) {
            System.out.println("Adding player to entity list??? That's not right! DONT DO THAT!!!");
        }
        entities.add(entity);
    }

    public void addActor(Actor actor) {
        if(actor instanceof Player) {
            System.out.println("Adding player to actor list??? That's not right! DONT DO THAT!!!");
        }
        actors.add(actor);
    }

    public void removeEntity(Entity entity) {
        toRemove.add(entity);
    }
}
