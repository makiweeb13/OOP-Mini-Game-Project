package JavaGame;

import java.util.LinkedList;
import java.awt.Graphics;
public class Render {
    LinkedList<GameObject> object = new LinkedList<GameObject>();

    public void tick() {
        for (GameObject temp : object) {
            temp.tick();
        }
    }

    public void render(Graphics g) {
        for (GameObject temp : object) {
            temp.render(g);
        }
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }


}
