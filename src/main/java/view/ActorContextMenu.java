package view;

import annotations.Invisible;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import model.Actor;
import model.Territory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActorContextMenu extends ContextMenu {
    Method[] standardMethods = Territory.class.getDeclaredMethods();
    private TerritoryPanel territoryPanel;

    /**
     * helper Method to sort out all Visible Methods declared in Territory.class
     * @return
     */
    private List<Method> getVisibleStandardMethods(){
        List<Method> visibleStandardMethods = new ArrayList<>();
        for(Method m : standardMethods){
            if(m.getAnnotation(Invisible.class) == null){
                visibleStandardMethods.add(m);
            }
        }
        return visibleStandardMethods;
    }

    /**
     * Helper Method to Merge the visible standardmethods with the new ones from the inheriting class.
     * @param standardMethods
     * @param addedMethods
     * @return
     */
    private List<Method> mergeLists(List<Method> standardMethods, List<Method> addedMethods){
        ObservableList<Method> toBeReturned = FXCollections.observableArrayList();
        toBeReturned.addAll(addedMethods);
        standardMethods.forEach(m -> {
            if(!hasMethod(toBeReturned, m.getName())) {
                toBeReturned.add(m);
            }
        });
        return toBeReturned;
    }

    /**
     * Constructor for the ActorContextMenu
     * @param territoryPanel
     */
    public ActorContextMenu(TerritoryPanel territoryPanel) {
        this.territoryPanel = territoryPanel;
        Class<? extends Territory> territorryClass = territoryPanel.getTerritory().getClass();

        List<Method> addedMethods = Arrays.asList(territorryClass.getDeclaredMethods());

        List<Method> standardMethodsList = getVisibleStandardMethods();

        List<Method> gatheredMethods = mergeLists(standardMethodsList, addedMethods);

        for (Method method : gatheredMethods) {
            boolean visible = true;
            if (method.getAnnotation(Invisible.class) != null) {
                visible = false;
            }
            if (visible) {
                MenuItem menuItem = new MenuItem(method.getName()+"()");
                menuItem.setOnAction(event -> {
                    try {
                        method.invoke(territoryPanel.getTerritory());
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
