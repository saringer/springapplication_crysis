package de.agdb.views.schedules.create_schedule;

import com.vaadin.data.HasValue;
import com.vaadin.event.LayoutEvents;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.UserError;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.agdb.AppUI;
import de.agdb.custom_components.CustomButton;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.ZoneOffset;

@UIScope
@SpringView(name = EventDescriptionView.VIEW_NAME)
public class EventDescriptionView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "GeneralView";
    private TextField scheduleTitle;
    private TextArea scheduleDescription;
    private DateField dateField;
    private CheckBox activateRecurrency;
    private ComboBox<String> dropDownMenu;
    @Autowired
    private GeneralViewPresenter generalViewPresenter;

    @PostConstruct
    void init() {

        setSizeFull();
        setSpacing(false);
        setMargin(false);



        VerticalLayout formWrapper = new VerticalLayout();
        formWrapper.setWidth(1150, Unit.PIXELS);
        formWrapper.setHeight(650, Unit.PIXELS);
        addComponent(formWrapper);
        setComponentAlignment(formWrapper, Alignment.MIDDLE_CENTER);

        setExpandRatio(formWrapper, 1);


        FormLayout content = buildContent();
        content.addStyleName("general-view-formlayout");
        content.setSpacing(true);
        content.setMargin(true);
        content.setHeight("80%");
        content.setWidth("70%");


        HorizontalLayout topNavBar = createTopNavBar();

        HorizontalLayout bottomNavBar = createBottomNav();



        formWrapper.addComponent(topNavBar);
        formWrapper.addStyleName("solid-border");
        //formWrapper.addStyleName("overflow-auto");
        formWrapper.setSpacing(false);
        formWrapper.setMargin(false);
        formWrapper.addComponent(content);
        formWrapper.addComponent(bottomNavBar);
        formWrapper.setComponentAlignment(content, Alignment.TOP_CENTER);
        formWrapper.setExpandRatio(content, 1);

        initPresenter(generalViewPresenter);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        AppUI app = (AppUI) UI.getCurrent();
        app.getGlobalScheduleWrapper();

        scheduleTitle.setComponentError(null);
        scheduleDescription.setComponentError(null);

        if (app.getGlobalScheduleWrapper().getTitle() == null) {
            scheduleTitle.setValue("");
        }
        if (app.getGlobalScheduleWrapper().getDescription() == null) {
            scheduleDescription.setValue("");
        }
        if (app.getGlobalScheduleWrapper().getTitle() != null) {
            scheduleTitle.setValue(app.getGlobalScheduleWrapper().getTitle());
        }
        if (app.getGlobalScheduleWrapper().getDescription() != null) {
            scheduleDescription.setValue(app.getGlobalScheduleWrapper().getDescription());
        }
        if (app.getGlobalScheduleWrapper().isRecurrentEvent() == false) {
            dateField.setEnabled(false);
            dropDownMenu.setEnabled(false);
            activateRecurrency.setValue(false);
        }


    }

    @Autowired
    public void initPresenter(GeneralViewPresenter generalViewPresenter) {
        generalViewPresenter.setEventDescriptionView(this);

    }

    private HorizontalLayout createTopNavBar() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");
        horizontalLayout.setHeight(30, Unit.PIXELS);

        CssLayout generalBar = new CssLayout();
        generalBar.setWidth("100%");
        generalBar.setHeight(30, Unit.PIXELS);
        Label generalHeader = new Label("Step 1: General");
        generalHeader.setSizeUndefined();
        generalBar.addComponent(generalHeader);
        generalBar.setStyleName("nav-top-active");

        CssLayout dateBar = new CssLayout();
        dateBar.setWidth("100%");
        dateBar.setHeight(30, Unit.PIXELS);
        Label dateHeader = new Label("Step 2: Set date(s)");
        dateHeader.setSizeUndefined();
        dateBar.addComponent(dateHeader);
        dateBar.setStyleName("nav-top-inactive");

        CssLayout timeLocationBar = new CssLayout();
        timeLocationBar.setWidth("100%");
        timeLocationBar.setHeight(30, Unit.PIXELS);
        Label timeLocationHeader = new Label("Step 3: Set time/location");
        timeLocationHeader.setSizeUndefined();
        timeLocationBar.addComponent(timeLocationHeader);
        timeLocationBar.setStyleName("nav-top-inactive");

        CssLayout categoriesBar = new CssLayout();
        categoriesBar.setWidth("100%");
        categoriesBar.setHeight(30, Unit.PIXELS);
        Label categoriesHeader = new Label("Step 4: Set categories");
        categoriesHeader.setSizeUndefined();
        categoriesBar.addComponent(categoriesHeader);
        categoriesBar.setStyleName("nav-top-inactive");

        horizontalLayout.addComponent(generalBar);
        horizontalLayout.addComponent(dateBar);
        horizontalLayout.addComponent(timeLocationBar);
        horizontalLayout.addComponent(categoriesBar);
        horizontalLayout.setSpacing(false);


        //horizontalLayout.setExpandRatio(generalBar, 1);
        return horizontalLayout;
    }

    private FormLayout buildContent() {

        Label section;

        FormLayout form = new FormLayout();

        section = new Label("Event title");
        section.addStyleName("h3");
        section.addStyleName("colored");
        scheduleTitle = new TextField();
        scheduleTitle.setWidth("100%");
        form.addComponent(section);
        form.addComponent(scheduleTitle);

        section = new Label("Recurrency");
        section.addStyleName("h3");
        section.addStyleName("colored");
        HorizontalLayout recurrencyOptions = new HorizontalLayout();

        dropDownMenu = new ComboBox<>();
        dropDownMenu.setTextInputAllowed(false);
        dropDownMenu.setEmptySelectionAllowed(false);
        dropDownMenu.setItems("weekly", "monthly");
        dropDownMenu.setSelectedItem("monthly");
        dropDownMenu.setEnabled(false);

     /*   NativeSelect<String> dropDownMenu = new NativeSelect();
        dropDownMenu.setEmptySelectionAllowed(false);
        dropDownMenu.setItems("weekly", "monthly");
        dropDownMenu.setSelectedItem("monthly");*/
        //dropDownMenu.setEnabled(false);
        recurrencyOptions.addComponent(dropDownMenu);
        recurrencyOptions.addComponent(new Label("until"));
        dateField = new DateField();
        dateField.setEnabled(false);
        recurrencyOptions.addComponent(dateField);
        activateRecurrency = new CheckBox("Make this a recurrying event", false);
        activateRecurrency.addValueChangeListener((HasValue.ValueChangeListener<Boolean>) event -> {
            if (activateRecurrency.getValue()) {
                dropDownMenu.setEnabled(true);
                dateField.setEnabled(true);
            } else {
                dropDownMenu.setEnabled(false);
                dateField.setEnabled(false);
            }
        });
        form.addComponent(section);
        form.addComponent(recurrencyOptions);
        form.addComponent(activateRecurrency);


        section = new Label("Description");
        section.addStyleName("h3");
        section.addStyleName("colored");
        scheduleDescription = new TextArea();
        scheduleDescription.setWidth("100%");
        form.addComponent(section);
        form.addComponent(scheduleDescription);


        return form;

    }

    public HorizontalLayout createBottomNav() {
        HorizontalLayout nav = new HorizontalLayout();
        nav.setWidth("100%");
        nav.setSpacing(false);
        nav.setMargin(false);

        LayoutEvents.LayoutClickListener listener = new LayoutEvents.LayoutClickListener() {
            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent layoutClickEvent) {
                if (scheduleTitle.getValue().length() <= 1) {
                    scheduleTitle.setComponentError(new UserError("Please enter a title foryour schedule"));
                }

                if (scheduleDescription.getValue().length() <= 1) {
                    scheduleDescription.setComponentError(new UserError("Please enter a description for your schedule"));
                }
                if (activateRecurrency.getValue() && dateField.getComponentError() != null) {
                    // dateField handles validation automatically

                } else {
                    AppUI app = (AppUI) UI.getCurrent();
                    app.getGlobalScheduleWrapper().setTitle(scheduleTitle.getValue());
                    app.getGlobalScheduleWrapper().setDescription(scheduleDescription.getValue());
                    if (activateRecurrency.getValue() == true) {
                        app.getGlobalScheduleWrapper().setRecurrentEvent(true);
                        System.out.println(dropDownMenu.getValue());
                        app.getGlobalScheduleWrapper().setRecurrentMode(dropDownMenu.getValue());
                        app.getGlobalScheduleWrapper().setFinalDate(dateField.getValue().atStartOfDay(ZoneOffset.UTC));
                        System.out.println(dateField.getValue().atStartOfDay(ZoneOffset.UTC).toString());
                    }
                    else {
                        app.getGlobalScheduleWrapper().setRecurrentEvent(false);

                    }

                    app.getNavigator().navigateTo("DateView");
                }
            }
        };

        CustomButton button = new CustomButton(VaadinIcons.ARROW_CIRCLE_RIGHT_O.getHtml() + " " + "NEXT", listener);
        button.setWidth("15%");
        button.setHeight(40, Unit.PIXELS);
        button.setWidth(167, Unit.PIXELS);
        button.addStyleName("next-button");

        nav.addComponent(button);
        nav.setComponentAlignment(button, Alignment.MIDDLE_RIGHT);

        return nav;

    }

    private CssLayout createBreadCrumbs() {
        CssLayout wrapperLayout = new CssLayout();
        Button topNavButton =  new Button("Schedule");
        topNavButton.addStyleName(ValoTheme.BUTTON_LINK);
        topNavButton.addClickListener((Button.ClickListener) event -> {
            UI.getCurrent().getNavigator().navigateTo("");
        });
        wrapperLayout.addComponent(topNavButton);
        Button placeHolder = new Button("> Add event");
        placeHolder.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        wrapperLayout.addComponent(placeHolder);
        wrapperLayout.addStyleName("breadcrumbs");
        return wrapperLayout;
    }


}
