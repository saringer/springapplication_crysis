package de.agdb.views.categories.manage_categories;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.SingleSelectionModel;
import com.vaadin.ui.themes.ValoTheme;
import de.agdb.AppUI;
import de.agdb.backend.entities.Categories;
import de.agdb.backend.entities.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.alump.materialicons.MaterialIcons;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@UIScope
@SpringView(name = ManageCategoriesView.VIEW_NAME)
public class ManageCategoriesView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "ManageCategoriesView";
    private Grid<Categories> grid = new Grid<>(Categories.class);
    private TextField categoryTitle;
    private TextField categoryShortcut;
    private ColorPicker shortcutColor;
    private TextField categoryTags;
    private TextArea categoryDescription;



    @Autowired
    UsersRepository repository;





    @PostConstruct
    void init() {
        addStyleNames("general-background-color-grey");
        setSizeFull();
        VerticalLayout formWrapper = new VerticalLayout();
        formWrapper.setWidth(1200, Unit.PIXELS);
        formWrapper.setHeight(800, Unit.PIXELS);
        addComponent(formWrapper);
        setComponentAlignment(formWrapper, Alignment.MIDDLE_CENTER);


        HorizontalLayout content = buildContent();
        content.setWidth("80%");
        content.setHeight("80%");

        formWrapper.addStyleName("solid-border");
        formWrapper.addStyleName("general-background-color-white");
        formWrapper.setSpacing(false);
        formWrapper.setMargin(false);
        formWrapper.addComponent(content);
        formWrapper.setComponentAlignment(content, Alignment.MIDDLE_CENTER);
        formWrapper.setExpandRatio(content, 1);
    }

    private HorizontalLayout buildContent() {
        HorizontalLayout wrapperLayout = new HorizontalLayout();
        wrapperLayout.setSpacing(false);
        wrapperLayout.setSizeFull();

        VerticalLayout categoryListLayout = buildCategoriesList();
        VerticalLayout categoryDetailsLayout = buildCategoryDetails();

        wrapperLayout.addComponent(categoryListLayout);
        wrapperLayout.addComponent(categoryDetailsLayout);

        return wrapperLayout;
    }

    private VerticalLayout buildCategoriesList() {
        VerticalLayout wrapperLayout = new VerticalLayout();
        wrapperLayout.setSizeFull();
        wrapperLayout.setSpacing(false);
        wrapperLayout.setMargin(false);

        CssLayout header = new CssLayout();
        header.setWidth("100%");
        header.setHeight(50, Unit.PIXELS);
        header.addStyleNames("managecontacts-header");
        header.addStyleNames("solid-border");
        Label label = new Label("Categories");
        //label.setWidth("100%");
        label.addStyleNames("headerLabel");
        //label.addStyleNames(ValoTheme.LABEL_H3);
        //label.addStyleNames(ValoTheme.LABEL_COLORED);
        header.addComponent(label);

        TextField searchField = new TextField();
        searchField.setDescription("search...");
        searchField.setValue("search...");
        searchField.setIcon(VaadinIcons.SEARCH);
        searchField.addStyleNames(ValoTheme.TEXTFIELD_INLINE_ICON);
        searchField.setWidth("100%");

        Button addCategoryButton = new Button("Add category");
        addCategoryButton.addClickListener((Button.ClickListener) clickEvent -> {
                    UI.getCurrent().getNavigator().navigateTo("AddCategoryView");
        });
        addCategoryButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        addCategoryButton.setIcon(MaterialIcons.PLUS_ONE);
        addCategoryButton.setWidth("100%");

        // Create a grid bound to the list


        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.setColumns("title");
        grid.removeHeaderRow(0);

        //wrapperLayout.addStyleName("disable-grid-cell-select");
        wrapperLayout.addComponent(header);
        wrapperLayout.addComponent(searchField);
        wrapperLayout.addComponent(addCategoryButton);
        wrapperLayout.addComponent(grid);

        wrapperLayout.setExpandRatio(grid, 1f);


        return wrapperLayout;
    }

    public void initCategoriesList() {
        AppUI app = (AppUI) UI.getCurrent();
        String userName = app.getAccessControl().getUsername();
        grid.deselectAll();

        if (!repository.findByUsername(userName).isEmpty()) {
            grid.setItems(repository.findByUsername(userName).get(0).getCategories());
            grid.addSelectionListener(event -> {


               /*if (event.getFirstSelectedItem().get() != null) {
                    Categories category = event.getFirstSelectedItem().get();
                    //Categories category  = selected.iterator().next();
                    categoryTitle.setValue(category.getTitle());
                    categoryShortcut.setValue(category.getShortCut());
                    categoryDescription.setValue(category.getDescription());
                }*/
            });


        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        initCategoriesList();
    }

    private VerticalLayout buildCategoryDetails() {
        VerticalLayout wrapperLayout = new VerticalLayout();
        wrapperLayout.setSizeFull();
        wrapperLayout.setSpacing(false);
        wrapperLayout.setMargin(false);

        CssLayout header = new CssLayout();
        header.setWidth("100%");
        header.setHeight(50, Unit.PIXELS);
        header.addStyleNames("managecontacts-header");
        header.addStyleNames("solid-border");
        Label label = new Label("SAS DETAIL");
        //label.setWidth("100%");
        label.addStyleNames("headerLabel");
        //label.addStyleNames(ValoTheme.LABEL_H3);
        //label.addStyleNames(ValoTheme.LABEL_COLORED);
        header.addComponent(label);

        FormLayout detailsForm = new FormLayout();
        detailsForm.setMargin(true);
        detailsForm.setSizeFull();
        detailsForm.addStyleNames("solid-border");

        categoryTitle = new TextField();
        categoryTitle.setWidth("100%");
        categoryTitle.setCaption("Category title");
        categoryShortcut = new TextField();
        categoryShortcut.setWidth("100%");
        categoryShortcut.setCaption("Category shortcut");
        shortcutColor = new ColorPicker();
        shortcutColor.setCaption("Shortcut-color");
        categoryTags = new TextField();
        categoryTags.setCaption("Category tags ");
        categoryDescription = new TextArea();
        categoryDescription.setRows(3);
        categoryDescription.setWidth("100%");
        categoryDescription.setCaption("Category description");
        detailsForm.addComponent(categoryTitle);
        detailsForm.addComponent(categoryShortcut);
        detailsForm.addComponent(shortcutColor);
        detailsForm.addComponent(categoryTags);

        detailsForm.addComponent(categoryDescription);

        wrapperLayout.addComponent(header);
        wrapperLayout.addComponent(detailsForm);
        wrapperLayout.setExpandRatio(detailsForm, 1);



        return wrapperLayout;


    }

}