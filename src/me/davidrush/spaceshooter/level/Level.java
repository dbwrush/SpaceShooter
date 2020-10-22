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
    private int cameraOffset = 620, enemySpawnDelay = 240;
    private double difficulty = 0;
    private ArrayList<Entity> entities, toRemove;
    private ArrayList<Actor> actors;
    private Entity[] stars = new Entity[100];

    public Level(Game game) {
        this.game = game;
        entities = new ArrayList<Entity>();
        actors = new ArrayList<Actor>();
        toRemove = new ArrayList<Entity>();
        player = new Player(game.width / 2, game.height, 1f, this, game);
        for(int i = 0; i < stars.length; i++) {
            stars[i] = new Star((float)(Math.random() * game.width), (float)(cameraY + Assets.player.getHeight() +(Math.random() * game.height)), this, game);
        }
    }

    public void tick() {
        if(Math.random() <= difficulty) {//enemies will spawn more frequently over time.
            addActor(new EnemyBomber((float)Math.random() * game.width, (cameraY) - Assets.enemyBomber.getHeight(), 1f,  this, game));
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
        enemySpawnDelay--;
        if(enemySpawnDelay >= -60 && enemySpawnDelay <= 0) {
            difficulty = 0.005;
        } else if(enemySpawnDelay < -60) {
            difficulty += 0.0000001;
        }
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
        entities.add(entity);
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public void removeEntity(Entity entity) {
        toRemove.add(entity);
    }
}
