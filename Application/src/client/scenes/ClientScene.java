package client.scenes;

import client.ClientModel;
import javafx.scene.Scene;

public abstract class ClientScene {

    protected ClientModel model;
    protected Scene scene;

    public ClientScene (ClientModel model) {
        this.model = model;
    }

    public Scene getScene() {
        if (scene == null) {
            throw new IllegalStateException("Scene is not set");
        }
        return scene;
    }

    public abstract String getName();
    protected abstract void buildScene();
    public abstract void onEnter();
    public abstract void onLeave();
}
