package com.company.projectmgt.view.task;

import com.company.projectmgt.entity.Task;

import com.company.projectmgt.view.main.MainView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "tasks", layout = MainView.class)
@ViewController("Task_.list")
@ViewDescriptor("task-list-view.xml")
@LookupComponent("tasksDataGrid")
@DialogMode(width = "64em")
public class TaskListView extends StandardListView<Task> {
    @ViewComponent
    private DataGrid<Task> tasksDataGrid;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Downloader downloader;

    @Subscribe
    public void onInit(final InitEvent event) {
        tasksDataGrid.addComponentColumn(attachment ->{
            Button downloadBtn = uiComponents.create(Button.class);
            downloadBtn.setText("Attachment");
            downloadBtn.addThemeName("tertiary-inline");

            if(attachment.getAttachment() == null) {
                downloadBtn.setEnabled(false);
            }
            downloadBtn.addClickListener(buttonClickEvent -> {
                downloader.download(attachment.getAttachment());
            });
            return downloadBtn;
        });
    }
}