package com.backend_casting.repository;

import com.backend_casting.entity.Reminder;

import java.util.List;

public interface ReminderService {
    List<Reminder> getAllReminders();
    void saveReminder(Reminder reminder);
}