import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;

/**
 * Created by sven on 1/31/16.
 */
public class MainView {

    public final double INIT_HEIGHT = 960;

    public final double INIT_WIDTH = 1280;

    public static volatile MainView instance;

    public Scene finalViewerScene;

    public BorderPane mainWindow;

    public MenuBar menuBar;

    public Menu menu;

    public MenuItem openFile;

    public static MainView getInstance(){
        if(instance == null){
            synchronized (MainView.class){
                if(instance == null) {
                    instance = new MainView();
                    instance.initView();
                }
            }
        }
        return instance;
    }

    private MainView(){
    }

    private void initView(){

        mainWindow = new BorderPane();

        menuBar = new MenuBar();

        menu = new Menu("File");

        openFile = new MenuItem("Open File");

        menu.getItems().addAll(openFile);

        menuBar.getMenus().addAll(menu);

        mainWindow.getChildren().addAll(menuBar);
    }




}
