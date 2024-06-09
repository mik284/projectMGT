package com.company.projectmgt.view.taskdetail;


import com.company.projectmgt.entity.Task;
import com.company.projectmgt.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "tasks/:id", layout = MainView.class)
@ViewController("Task_.detail")
@ViewDescriptor("task-detail-view.xml")
@EditedEntityContainer("taskDc")
public class TaskDetailView extends StandardDetailView <Task> {
}