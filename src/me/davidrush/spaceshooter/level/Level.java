package me.davidrush.spaceshooter.level;

import me.davidrush.spaceshooter.Game;
import me.davidrush.spaceshooter.entities.*;
import me.davidrush.spaceshooter.graphics.Assets;
import me.davidrush.spaceshooter.graphics.Toast;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Level {
    private Game game;
    private Player player;
    private float cameraY, distance;
    private int cameraOffset = 620, maxEnemies = 16;
    private double difficulty = 0;
    private ArrayList<Entity> entities, toRemove;
    private ArrayList<Actor> actors, actorsToAdd;
    private Entity[] stars = new Entity[100];
    private ArrayList<Toast> toasts = new ArrayList<>(), toastsToRemove = new ArrayList<>();

    public Level(Game game) {
        this.game = game;
        entities = new ArrayList<Entity>();
        actors = new ArrayList<Actor>();
        toRemove = new ArrayList<Entity>();
        actorsToAdd = new ArrayList<Actor>();
        player = new Player(game.width / 2, game.height, 1f, this, game); //Note, the player cannot be added to the Entity or Actor list or else they will get ticked more than once!

        for(int i = 0; i < stars.length; i++) {
            stars[i] = new Star((float)(Math.random() * game.width), (float)(cameraY + Assets.player.getHeight() +(Math.random() * game.height)), this, game);
        }
        toasts.add(new Toast(240, "Welcome to the game!"));
        toasts.add(new Toast(480, "Use WASD to move around!"));
        toasts.add(new Toast(720, "Press SPACE to fire!"));
        toasts.add(new Toast(960, "Power distribution is displayed in the bottom left."));
        toasts.add(new Toast(1200, "Increase power to a system by pressing 1, 2, or 3."));
        toasts.add(new Toast(1440, "Decrease power to a system by pressing SHIFT and 1, 2, or 3."));
    }

    public void tick() {
        double rand = Math.random();
        if(rand <= Math.sqrt(difficulty) / 1000 && actors.size() < maxEnemies) {//enemies will spawn more frequently over time.
            double enemyType = (Math.sqrt(difficulty) / 10000) - rand;
            if(enemyType > 0.05) {
                addActor(new EnemyCarrier((float)Math.random() * game.width, (cameraY) - Assets.enemyCruiser.getHeight(), 1f, this, game));
            } else if(enemyType > 0.04) {
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
        for(Actor actor : actorsToAdd) {
            actors.add(actor);
        }
        actorsToAdd.clear();
        toRemove.clear();
        difficulty++;
        for(Toast toast : toasts) {
            toast.setTime(toast.getTime() - 1);
            if(toast.getTime() <= 0) {
                toastsToRemove.add(toast);
            }
        }
        for(Toast toast : toastsToRemove) {
            toasts.remove(toast);
        }
        toastsToRemove.clear();
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
        for(int i = 0; i < toasts.size(); i++) {
            String message = toasts.get(i).getMessage();
            g.drawString(message, 5, i * 20 + 40);
        }
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
            return;
        }
        actors.add(actor);
    }

    public void addActorConcurrent(Actor actor) {
        if(actor instanceof Player) {
            System.out.println("Adding player to actor list??? That's not right! DONT DO THAT!!!");
            return;
        }
       actorsToAdd.add(actor);
    }

    public void removeEntity(Entity entity) {
        toRemove.add(entity);
    }

    public void addToast(Toast toast) {
        toasts.add(toast);
    }
}
