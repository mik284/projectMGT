package com.company.projectmgt.view.timeentry;

import com.company.projectmgt.entity.TimeEntry;

import com.company.projectmgt.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Route(value = "timeEntries/:id", layout = MainView.class)
@ViewController("TimeEntry.detail")
@ViewDescriptor("time-entry-detail-view.xml")
@EditedEntityContainer("timeEntryDc")
@DialogMode(width = "AUTO", height = "AUTO")
public class TimeEntryDetailView extends StandardDetailView<TimeEntry> {
    @Subscribe
    public void onInitEntity(final InitEntityEvent<TimeEntry> event) {
        event.getEntity().setEntryDate(LocalDateTime.now());
    }
}