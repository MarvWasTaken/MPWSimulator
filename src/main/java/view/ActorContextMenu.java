package view;

import annotations.Invisible;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import model.Actor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ActorContextMenu extends ContextMenu {
    Method[] standardMethods = Actor.class.getDeclaredMethods();
    private TerritoryPanel territoryPanel;

    public ActorContextMenu(Class<? extends Actor> actorClass, TerritoryPanel territoryPanel) {
        this.territoryPanel = territoryPanel;
        List<Method> addedMethods = Arrays.asList(actorClass.getDeclaredMethods());
        List<Method> standardMethodsList = Arrays.asList(standardMethods);

        standardMethodsList.forEach(m -> {
            if (!hasMethod(addedMethods, m.getName())) {
                addedMethods.add(m);
            }
        });
        for (Method method : addedMethods) {
            boolean visible = true;
            if (method.getAnnotation(Invisible.class) != null) {
                visible = false;
            }
            if (visible) {
                MenuItem menuItem = new MenuItem(method.getName());
                menuItem.setOnAction(event -> {
                    try {
                        method.invoke(territoryPanel.getTerritory().getActor());
                        System.out.println("invoked " + method.getName());
                        territoryPanel.draw();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                this.getItems().add(menuItem);
            }
        }
    }

    public boolean hasMethod(List<Method> list, String methodName) {
        for (Method m : list) {
            if (m.getName().equals(methodName)) {
                return true;
            }
        }
        return false;
    }
}
