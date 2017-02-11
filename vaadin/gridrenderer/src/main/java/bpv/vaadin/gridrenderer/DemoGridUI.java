package bpv.vaadin.gridrenderer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.vaadin.grid.cellrenderers.editoraware.CheckboxRenderer;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import bpv.vaadin.gridrenderer.beans.Person;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("DemoGridTheme")
@SuppressWarnings("serial")
public class DemoGridUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        Label title = new Label("Grid Renderer Demo !");
        
        layout.addComponent(title);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        final Grid grid = createGrid();
        layout.addComponent(grid);
        
        Button save = new Button("Save");
        save.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				List<Person> nonDuplicates = new ArrayList<>();
				List<Person> primarys = new ArrayList<>();
				for(Object itemId : grid.getContainerDataSource().getItemIds()){
					Person p = (Person) itemId;
					if(!p.isDuplicate()){
						nonDuplicates.add(p);
					}
					if(p.isPrimary()){
						primarys.add(p);
					}
				}
				if(primarys.isEmpty()){
					Notification.show("Please select a primary person", Type.ERROR_MESSAGE);
				}else if(nonDuplicates.isEmpty()){
					Notification.show("Please deselect atleast one person", Type.ERROR_MESSAGE);
				}else if(primarys.size() > 1){
					Notification.show("Please select only one primary person", Type.ERROR_MESSAGE);
				}else if(nonDuplicates.contains(primarys.get(0))){
					Notification.show("Primary person must be selected from duplicate persons", Type.ERROR_MESSAGE);
				}else{
					Notification.show("Below persons will be removed from duplicate list\n\n" + nonDuplicates);
				}
			}
		});
        
        layout.addComponent(save);
        
        setContent(layout);
    }
    
    private Grid createGrid(){
    	BeanItemContainer<Person> container = new BeanItemContainer<>(Person.class);
    	int max = 5;
    	for(int id=1;id<=max;id++){
    		container.addBean(Person.createNew(id));
    	}
    	
    	Grid grid = new Grid(container);
    	
    	grid.setColumns("name","duplicate","primary");
    	grid.setSizeFull();
		grid.setEditorEnabled(true);
		grid.setEditorBuffered(false);
		grid.getColumn("name").setEditable(false);
		grid.getColumn("duplicate").setRenderer(new CheckboxRenderer());
		grid.getColumn("primary").setRenderer(new CheckboxRenderer());
    	
    	return grid;
    }

    @WebServlet(urlPatterns = "/*", name = "DemoGridUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = DemoGridUI.class, productionMode = false, widgetset = "bpv.vaadin.gridrenderer.demo.DemoWidgetSet")
    public static class DemoGridUIServlet extends VaadinServlet {
    }
}
